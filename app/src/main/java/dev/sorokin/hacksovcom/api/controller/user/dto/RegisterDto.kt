package dev.sorokin.hacksovcom.api.controller.user.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.SchemaProperty
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@Schema(description = "Данные для регистрации пользователя")
class RegisterDto(
//    @Schema(description = "ФИО", example = "ВАся")
    val name: String,
    val phone: String,
    val email: String,
    val passport: String,
    var password: String,
    val birthDate: String = "2022-10-07 10:10:10",
)