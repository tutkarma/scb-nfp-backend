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
    @Schema(description = "ФИО") val name: String,
    @Schema(description = "Номер телефона") val phone: String,
    @Schema(description = "Почта") val email: String,
    @Schema(description = "ДАнные пасспорта") val passport: String,
    @Schema(description = "Пароль") var password: String,
    @Schema(description = "Дата рождения, timestanp в секундах") val birthDate: Long
)