package dev.sorokin.hacksovcom.api.security

import dev.sorokin.hacksovcom.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    var userService: UserService? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .addFilterBefore(AuthFilter(userService!!, encoder!!), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
            .authorizeRequests()
            .antMatchers("/api").authenticated()
            .antMatchers("/api/users/register").permitAll()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .disable()
    }
}