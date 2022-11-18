package dev.sorokin.hacksovcom.api.controller.user.dto

import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
class RegisterDto(
    val login: String,
    val password: String,
)