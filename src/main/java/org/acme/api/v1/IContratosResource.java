package org.acme.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.api.v1.request.MyRequestBody;
import org.acme.share.ServicoEnum;
import org.acme.share.annotations.LogOperacao;


@Path("/contratos")
public interface IContratosResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @LogOperacao(operacao = ServicoEnum.BUSCA_CONTRATOS)
    public String buscar();
    
    @GET
    @Path("{contrato}")
    @Produces(MediaType.TEXT_PLAIN)
    @LogOperacao(operacao = ServicoEnum.DETALHA_CONTRATO)
    public String detalhar(@PathParam("contrato") String contrato);

    @POST
    @LogOperacao(operacao = ServicoEnum.SALVA)
    public void salvar(MyRequestBody body);
    
}
