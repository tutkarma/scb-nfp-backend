package dev.sorokin.hacksovcom.api.session

import dev.sorokin.hacksovcom.service.user.domain.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import javax.swing.text.StyledEditorKit.BoldAction

@Service
class SessionUserService {

    fun getSessionUser(): User {
        if(!isUserExists())
            throw RuntimeException("Вход не выполнен")
        return SecurityContextHolder.getContext().authentication.details as User
    }

    fun isUserExists(): Boolean {
        if(SecurityContextHolder.getContext().authentication == null)
            return false
        if(SecurityContextHolder.getContext().authentication.details == null)
            return false
        return SecurityContextHolder.getContext().authentication.details is User
    }

}