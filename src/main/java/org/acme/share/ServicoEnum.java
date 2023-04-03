package org.acme.share;

public enum ServicoEnum {

    BUSCA_CONTRATOS(1, "busca-contratos-service","/contratos"),
    DETALHA_CONTRATO(2, "detalha-contrato-service","/contratos"),
    SALVA(3, "salvar-service","/contratos");

    private Integer code;
    private String value;
    private String url;

    ServicoEnum(Integer code, String value, String url){
        this.code  = code;
        this.value = value;
        this.url   = url;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getUrl() {
        return url;
    }

    public static ServicoEnum getByUrl(String url){
        for (ServicoEnum s : ServicoEnum.values()) { 
            if(s.getUrl().equals(url)){
                return s;
            }
        }

        return null;
    }
    
}
