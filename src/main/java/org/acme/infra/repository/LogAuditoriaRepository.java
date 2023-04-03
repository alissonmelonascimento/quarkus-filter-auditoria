package org.acme.infra.repository;

import org.acme.infra.entity.LogAuditoria;

public interface LogAuditoriaRepository {

    void salvar(LogAuditoria logs);
    LogAuditoria buscaPeloId(Long id);
    
}
