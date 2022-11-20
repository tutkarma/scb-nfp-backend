package dev.sorokin.hacksovcom.api.controller.user

import dev.sorokin.hacksovcom.api.controller.user.dto.RegisterDto
import dev.sorokin.hacksovcom.api.controller.user.dto.UserDto
import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.user.UserService
import lombok.AllArgsConstructor
import dev.sorokin.hacksovcom.api.controller.user.dto.toDto
import dev.sorokin.hacksovcom.service.api.CurrencyApi
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin
class UserController(
    val userService: UserService,
    val sessionUserService: SessionUserService,
    val currencyApi: CurrencyApi
) {

    @Operation(
        summary = "Создание нового пользователя",
        description = "реализовано"
    )
    @PostMapping("/register")
    fun registerUser(@RequestBody dto: RegisterDto) {
        userService.registerUser(dto)
    }

    @Operation(
        summary = "Вход в систему (нужен токен в хедерах)",
        description = "реализовано"
    )
    @GetMapping("/login")
    fun getUser(): UserDto {
        return sessionUserService.getSessionUser().toDto()
    }

    @Operation(description = "Для того чтобы поменять токен для доступа к апи")
    @PostMapping("/new-token")
    fun resetApiToken(@RequestBody newToken: String) {
        currencyApi.resetToken(newToken)
    }

}