package dev.sorokin.hacksovcom.api.controller.user.dto

import dev.sorokin.hacksovcom.api.toSeconds
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRole

fun User.toDto(): UserDto = UserDto(
    id!!,
    name,
    phone,
    email,
    birthDate.toSeconds(),
    UserRole.findById(roleId).toString(),
    isBlocked,
    passport
)