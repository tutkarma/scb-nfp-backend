package dev.sorokin.hacksovcom.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import dev.sorokin.hacksovcom.api.excpetion.ErrorDto
import dev.sorokin.hacksovcom.repo.UserJpaRepo
import dev.sorokin.hacksovcom.service.user.UserService
import dev.sorokin.hacksovcom.service.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.filter.GenericFilterBean
import java.lang.Exception
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthFilter(
    private val userService: UserService,
    private val encoder: PasswordEncoder,
    private val userRepo: UserJpaRepo,
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

//        val req = request as HttpServletRequest
//        if (req.getHeader("Authorization") == null) {
//            chain.doFilter(request, response)
//            return
//        }
//
//        val basicToken = req.getHeader("Authorization").substring("Basic ".length)
//        val decoded = String(Base64.getDecoder().decode(basicToken))
//
//        val login = decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
//
//        val password = decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
//
//        val dbUser = userRepo.findUserByPhone(login) ?: throw AuthenticationServiceException("Пользователь не найден")
//
//
//        val isMatches = encoder.matches(password, dbUser.password);
////        val isMatches = dbUser.password == password
//
//        if (!isMatches) throw AuthenticationServiceException("Неверный пароль")
//        checkIsUserBlocked(dbUser)
//        generateAuth(dbUser)
//        chain.doFilter(request, response)

        try {
            doMethod(request, response)
            chain.doFilter(request, response)
        } catch(e: Exception) {
            response as HttpServletResponse
            response.status = 401;
            val objectMapper = ObjectMapper()
            val errorDto = ErrorDto(e.message ?: "Ошибка входа")
            response.outputStream.write(objectMapper.writeValueAsBytes(errorDto))
        }
    }

    fun doMethod(request: ServletRequest, response: ServletResponse) {
        val req = request as HttpServletRequest
        if (req.getHeader("Authorization") == null) {
//            chain.doFilter(request, response)
            return
        }

        val basicToken = req.getHeader("Authorization").substring("Basic ".length)
        val decoded = String(Base64.getDecoder().decode(basicToken))

        val login = decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

        val password = decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val dbUser = userRepo.findUserByPhone(login) ?: throw RuntimeException("Пользователь не найден")


        val isMatches = encoder.matches(password, dbUser.password);
//        val isMatches = dbUser.password == password

        if (!isMatches) throw RuntimeException("Неверный пароль")
        checkIsUserBlocked(dbUser)
        generateAuth(dbUser)
//        chain.doFilter(request, response)
    }

    private fun checkIsUserBlocked(dbUser: User) {
        if(!dbUser.isBlocked) return
        throw AuthenticationServiceException("Ваш акканут заблокирован")
    }

    private fun generateAuth(user: User) {
        val auth = UsernamePasswordAuthenticationToken(
            user.phone,
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
