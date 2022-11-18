package dev.sorokin.hacksovcom.api.controller.user

import dev.sorokin.hacksovcom.api.controller.user.dto.RegisterDto
import dev.sorokin.hacksovcom.api.controller.user.dto.UserDto
import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.user.UserService
import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import dev.sorokin.hacksovcom.api.controller.user.dto.toDto
import io.swagger.v3.oas.annotations.Operation

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
class UserController(
    val userService: UserService,
    val sessionUserService: SessionUserService,
) {

    @Operation(description = "Создание нового пользователя")
    @PostMapping("/register")
    fun registerUser(@RequestBody dto: RegisterDto) {
        userService.registerUser(dto.login, dto.password)
    }

    @Operation(description = "Вход в систему (нужен токен в хедерах)")
    @GetMapping("/login")
    fun getUser(): UserDto {
        return sessionUserService.getSessionUser().toDto();
    }

}