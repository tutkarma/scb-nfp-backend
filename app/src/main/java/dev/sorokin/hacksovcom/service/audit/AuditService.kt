package dev.sorokin.hacksovcom.service.audit

import dev.sorokin.hacksovcom.api.controller.audit.UserAudit
import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service

@Service
class AuditService {

    @Required(UserRole.USER)
    fun getUserAudit(user: User): UserAudit {
        return TODO()
    }

}