package org.acme.share;

public enum ServicoEnum {

    NOT_FOUND(1, "not found"),
    BUSCA_CONTRATOS(2, "busca-contratos-service"),
    DETALHA_CONTRATO(3, "detalha-contrato-service"),
    SALVA(4, "salvar-service");

    private Integer code;
    private String value;

    ServicoEnum(Integer code, String value){
        this.code  = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
    
}
