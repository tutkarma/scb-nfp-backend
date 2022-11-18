package dev.sorokin.hacksovcom.api.controller.user.dto

import dev.sorokin.hacksovcom.user.domain.User

fun User.toDto(): UserDto = UserDto(id!!, login, password)