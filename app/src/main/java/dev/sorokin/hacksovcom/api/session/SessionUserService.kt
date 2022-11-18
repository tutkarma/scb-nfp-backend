package dev.sorokin.hacksovcom.api.session

import dev.sorokin.hacksovcom.user.domain.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SessionUserService {

    fun getSessionUser(): User {
        return SecurityContextHolder.getContext().authentication.details as User
    }

}