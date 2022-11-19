package dev.sorokin.hacksovcom.api.security;

import dev.sorokin.hacksovcom.api.session.SessionUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
public class ServiceLayerInterceptor implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLayerInterceptor.class);
    private boolean isApplicationInitializingWorks = true;
    private SessionUserService sessionUserService;

    @Autowired
    public ServiceLayerInterceptor(SessionUserService sessionUserService) {
        this.sessionUserService = sessionUserService;
    }

    @Pointcut("@annotation(Required)")
    private void protectedMethod() {}


    @Before("protectedMethod()")
    public void handleProtectedMethod(JoinPoint jp) {
        if(isApplicationInitializingWorks || !sessionUserService.isUserExists())
            return;

        var user = sessionUserService.getSessionUser();
        var required = ((MethodSignature) jp.getSignature())
                .getMethod()
                .getAnnotation(Required.class)
                .value();

        if(user.getRoleId() == required.getId())
            return;
        throw new RuntimeException("Пользователь не имеет прав на своершение операции");

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        isApplicationInitializingWorks = false;
    }
}