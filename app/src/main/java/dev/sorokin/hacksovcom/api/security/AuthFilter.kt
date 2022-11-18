package dev.sorokin.hacksovcom.api.security

import dev.sorokin.hacksovcom.user.UserService
import dev.sorokin.hacksovcom.user.domain.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class AuthFilter(private val userService: UserService, private val encoder: PasswordEncoder) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        val req = request as HttpServletRequest
        if (req.getHeader("Authorization") == null) {
            chain.doFilter(request, response)
            return
        }

        val basicToken = req.getHeader("Authorization").substring("Basic ".length)
        val decoded = String(Base64.getDecoder().decode(basicToken))

        val login = decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

        val password = decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val dbUser = userService.findByLogin(login) ?: throw RuntimeException("Пользователь не найден")

//        var isMatches = encoder.matches(password, dbUser.getPassword());
        val isMatches = dbUser.password == password

        if (!isMatches) throw RuntimeException("Неверный пароль")

        generateAuth(dbUser)
        chain.doFilter(request, response)
    }

    private fun generateAuth(user: User) {
        val auth = UsernamePasswordAuthenticationToken(
            user.login,
            null, emptyList()
        )
        auth.details = user
        SecurityContextHolder.getContext().authentication = auth
    }

    private fun generateFailAuth() {
        val auth = UsernamePasswordAuthenticationToken(
            null,
            null,
            emptyList()
        )
        auth.isAuthenticated = false
        SecurityContextHolder.getContext().authentication = auth
    }

}