package dev.sorokin.hacksovcom.service.user.domain

import lombok.Data
import org.springframework.data.annotation.Id

@Data
class User(
    @Id
    var id: String?,
    val login: String,
    val password: String,
)