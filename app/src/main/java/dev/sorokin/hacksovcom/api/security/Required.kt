package dev.sorokin.hacksovcom.api.security

import dev.sorokin.hacksovcom.service.user.domain.UserRole
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Required(val value: UserRole)