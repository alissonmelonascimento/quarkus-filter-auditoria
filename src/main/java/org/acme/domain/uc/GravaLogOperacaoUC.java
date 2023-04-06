package org.acme.domain.uc;

import org.acme.infra.entity.LogAuditoria;

public interface GravaLogOperacaoUC {

    void execute(LogAuditoria entity);
    
}
