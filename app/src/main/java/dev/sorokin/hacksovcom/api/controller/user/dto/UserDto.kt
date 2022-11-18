package dev.sorokin.hacksovcom.api.controller.user.dto

import lombok.Data

@Data
class UserDto(
    val id: String,
    val login: String,
    val password: String,
)