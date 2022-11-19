package dev.sorokin.hacksovcom.api.controller.user.dto

import lombok.Data
import java.sql.Timestamp

@Data
class UserDto(
    var id: Long,
    var name: String,
    var phone: String,
    var email: String,
    var birthDate: String,
    var role: String,
)