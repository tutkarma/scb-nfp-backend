package dev.sorokin.hacksovcom.api.security

import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Order(0)
@Component
class ServiceLayerInterceptor(private val sessionUserService: SessionUserService) :
    ApplicationListener<ContextRefreshedEvent?>
{
    private var isApplicationInitializingWorks = true

    @Pointcut("@annotation(Required)")
    private fun protectedMethod() {
    }

    @Before("protectedMethod()")
    fun handleProtectedMethod(jp: JoinPoint) {
        if (isApplicationInitializingWorks || !sessionUserService.isUserExists()) return
        val user = sessionUserService.getSessionUser()
        val required: UserRole = (jp.signature as MethodSignature)
            .method
            .getAnnotation(Required::class.java)
            .value
        if (user.roleId == required.id) return
        throw RuntimeException("Пользователь не имеет прав на своершение операции")
    }

//    @Override
//    fun onApplicationEvent(event: ContextRefreshedEvent?) {
//        isApplicationInitializingWorks = false
//    }

    companion object {
        private val logger = LoggerFactory.getLogger(ServiceLayerInterceptor::class.java)
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        isApplicationInitializingWorks = false
    }
}