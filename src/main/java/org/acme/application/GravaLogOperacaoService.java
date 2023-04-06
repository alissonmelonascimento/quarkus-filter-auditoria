package org.acme.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.acme.domain.uc.GravaLogOperacaoUC;
import org.acme.infra.entity.LogAuditoria;
import org.acme.infra.repository.LogAuditoriaRepository;

@ApplicationScoped
public class GravaLogOperacaoService implements GravaLogOperacaoUC{

    @Inject
    LogAuditoriaRepository repository;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void execute(LogAuditoria entity) {
        repository.salvar(entity);
    }
    
}
