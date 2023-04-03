package org.acme.infra.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LOG_AUDITORIA", schema = "public")
public class LogAuditoria {

    @Id
    @SequenceGenerator(
        name = "SQ_LOGS",
        sequenceName = "SQ_LOGS",
        allocationSize = 1,
        initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOGS")
    public Long id;

    public String servico;
    public LocalDateTime data;
    public Integer operacao;
    public String url;
    public String conteudo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pai", insertable = true, updatable = true, nullable = true)
    public LogAuditoria pai;
    
}
