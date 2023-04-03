package org.acme.share;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.acme.infra.entity.LogAuditoria;
import org.acme.infra.repository.LogAuditoriaRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class AuditoriaFilter implements ContainerRequestFilter, ContainerResponseFilter{

    static final String LOG_AUDITORIA_REQUEST_PROPRETY = "LogAuditoriaRequestProperty";
    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    LogAuditoriaRepository logAuditoriaRepository;

    @Override
    @Transactional
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String pathRequest = requestContext.getUriInfo().getPath();

        ServicoEnum servicoEnum = ServicoEnum.getByUrl(pathRequest);

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
        logRequest.operacao = servicoEnum.getCode();
        logRequest.servico  = servicoEnum.getValue();
        logRequest.url      = pathRequest;

        logAuditoriaRepository.salvar(logRequest);

        requestContext.setProperty(LOG_AUDITORIA_REQUEST_PROPRETY, logRequest);
    }

    @Override
    @Transactional
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        LogAuditoria logAuditoriaRequest = (LogAuditoria) requestContext.getProperty(LOG_AUDITORIA_REQUEST_PROPRETY);
        System.out.println("ID: "+logAuditoriaRequest.id);

    }
    
}
