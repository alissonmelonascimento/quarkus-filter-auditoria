package org.acme.api.v1;

import org.acme.api.v1.request.MyRequestBody;
import org.acme.share.ServicoEnum;
import org.acme.share.annotations.LogOperacao;

public class ContratosResource implements IContratosResource{
    
    public String buscar() {
        return "Contratos";
    }
    
    public String detalhar(String contrato) {
        //throw new RuntimeException("Erro");
        return "Contrato '"+contrato+"' detalhado";
    }

    @LogOperacao(operacao = ServicoEnum.SALVA)
    public void salvar(MyRequestBody body) {

    }    
    
}
