package org.acme.api.v1;

import org.acme.api.v1.request.MyRequestBody;

public class ContratosResource implements IContratosResource{

    public String buscar() {
        return "Contratos";
    }

    public String detalhar(String contrato) {
        //throw new RuntimeException("Erro");
        return "Contrato '"+contrato+"' detalhado";
    }

    public void salvar(MyRequestBody body) {

    }    
    
}
