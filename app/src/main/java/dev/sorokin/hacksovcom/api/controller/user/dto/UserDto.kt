package dev.sorokin.hacksovcom.api.controller.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Data
import java.sql.Timestamp

@Data
class UserDto(
    var id: Long,
    var name: String,
    var phone: String,
    var email: String,
    @Schema(description = "дата рождения timestamp в секундах") var birthDate: Long,
    @Schema(description = "Роль ADMIN или USER") var role: String,
    var isBlocked: Boolean
)