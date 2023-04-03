package org.acme.infra.repository.impl;

import javax.enterprise.context.ApplicationScoped;

import org.acme.infra.entity.LogAuditoria;
import org.acme.infra.repository.LogAuditoriaRepository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class LogAuditoriaRepositoryImpl implements LogAuditoriaRepository, PanacheRepository<LogAuditoria>{

    public void salvar(final LogAuditoria logAuditoria){
        persist(logAuditoria);
    }

    @Override
    public LogAuditoria buscaPeloId(final Long id) {
        return findById(id);
    }
    
}
