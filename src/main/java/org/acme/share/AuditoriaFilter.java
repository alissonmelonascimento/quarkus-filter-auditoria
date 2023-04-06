package org.acme.share;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.acme.domain.uc.GravaLogOperacaoUC;
import org.acme.infra.entity.LogAuditoria;
import org.acme.share.annotations.LogOperacao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class AuditoriaFilter implements ContainerRequestFilter, ContainerResponseFilter{

    static final String LOG_AUDITORIA_REQUEST_PROPRETY = "LogAuditoriaRequestProperty";
    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    GravaLogOperacaoUC gravaLogOperacaoUC;

    @Context
    ResourceInfo resourceInfo;    

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String pathRequest = requestContext.getUriInfo().getPath();

        LogOperacao logOperacao = this.getLogOperacao(this.resourceInfo.getResourceMethod());

        //buscando body do request
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        requestContext.getEntityStream().transferTo(baos);        
        InputStream entityStream = new ByteArrayInputStream(baos.toByteArray());
        InputStream entityStream2 = new ByteArrayInputStream(baos.toByteArray());

        JsonNode tree = mapper.readTree(entityStream);
        String bodyRequest = tree.toString();
        requestContext.setEntityStream(entityStream2);//tem que setar de volta no request, se nao fica nulo        

        LogAuditoria logRequest = new LogAuditoria();
        logRequest.data      = LocalDateTime.now();
        logRequest.conteudo = bodyRequest;
        logRequest.operacao = logOperacao.operacao().getCode();
        logRequest.servico  = logOperacao.operacao().getValue();
        logRequest.url      = pathRequest;

        this.gravaLogOperacaoUC.execute(logRequest);

        requestContext.setProperty(LOG_AUDITORIA_REQUEST_PROPRETY, logRequest);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        LogAuditoria logAuditoriaRequest = (LogAuditoria) requestContext.getProperty(LOG_AUDITORIA_REQUEST_PROPRETY);

        LogAuditoria logAuditoriaResponse = new LogAuditoria();
        logAuditoriaResponse.pai      = logAuditoriaRequest;
        logAuditoriaResponse.data     = LocalDateTime.now();
        logAuditoriaResponse.operacao = logAuditoriaRequest.operacao;
        logAuditoriaResponse.servico  = logAuditoriaRequest.servico;

        if(responseContext.getMediaType() != null){
            if(responseContext.getMediaType() == MediaType.APPLICATION_JSON_TYPE){
                logAuditoriaResponse.conteudo = mapper.writeValueAsString(responseContext.getEntity());
            }else{
                logAuditoriaResponse.conteudo = responseContext.getEntity().toString();
            }
        }
        
        this.gravaLogOperacaoUC.execute(logAuditoriaResponse);        

    }

    LogOperacao getLogOperacao(Method m){
        LogOperacao[] logs = m.getAnnotationsByType(LogOperacao.class);

        if(logs != null){
            return logs[0];
        }

        return null;
    }
    
}
