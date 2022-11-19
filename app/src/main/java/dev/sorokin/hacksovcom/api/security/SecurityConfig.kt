package dev.sorokin.hacksovcom.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import dev.sorokin.hacksovcom.api.security.excpetion.ErrorDto
import dev.sorokin.hacksovcom.repo.user.UserJpaRepo
import dev.sorokin.hacksovcom.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    var userService: UserService? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var userJpaRepo: UserJpaRepo? = null;

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .addFilterBefore(AuthFilter(userService!!, encoder!!, userJpaRepo!!), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
            .authorizeRequests()
            .antMatchers("/api").authenticated()
            .antMatchers("/api/users/register").permitAll()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .failureHandler(CustomAuthenticationFailureHandler())
            .disable()
    }
}

class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {
    @Throws(IOException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        response.sendRedirect("/customError")
        val objectMapper = ObjectMapper()
        val errorDto = ErrorDto(exception?.message ?: "Ошибка входа")
        response.sendError(401, objectMapper.writeValueAsString(errorDto))
    }
}