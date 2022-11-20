package dev.sorokin.hacksovcom.api.controller.admin

import dev.sorokin.hacksovcom.api.controller.account.CurrencyAccountDto
import dev.sorokin.hacksovcom.api.controller.account.toDto
import dev.sorokin.hacksovcom.api.controller.user.dto.UserDto
import dev.sorokin.hacksovcom.api.controller.user.dto.toDto
import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.account.CurrencyAccountService
import dev.sorokin.hacksovcom.service.user.UserService
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRegistrationRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/users")
class AdminController(
    val userService: UserService,
    val sessionUserService: SessionUserService,
    val accountService: CurrencyAccountService
) {


    @Operation(
        summary = "Подтверждение регистрации пользователя. id - id регистрации." +
                "isAccepted - принимать или нет запрос - true/false", description = "реализовано"
    )
    @GetMapping("/registration/accept/{id}/{isAccepted}")
    fun acceptRequest(
        @PathVariable @Schema(description = "GGGGGGGGGGDLMFDnnj") id: Long,
        @PathVariable isAccepted: Boolean
    ) {
        return userService.acceptRegistration(id, isAccepted);
    }

    @Operation(
        summary = "Получение списка всех запросов на регистрацию +++",
        description = "реализовано"
    )
    @GetMapping("/registration")
    fun getAllRequests(): List<UserRegistrationRequest> {
        return userService.getAllRegistrationRequests()
    }


    @Operation(
        summary = "Получение списка всех пользователей +++",
        description = "реализовано"
    )
    @GetMapping("")
    fun getAll(): List<UserDto> {
        return userService.getAllUsers(false).map { it.toDto() }
    }

    @Operation(
        summary = "Заблокировать пользователя с id. isBlock - true или false",
        description = "реализовано"
    )
    @PutMapping("/block/{id}/{isBlock}")
    fun blockUser(@PathVariable id: Long, @PathVariable isBlock: Boolean) {
        return userService.setBlockedToUser(id, isBlock)
    }

    @Operation(
        summary = "Получить все счета пользователя по его ID",
        description = "реализовано"
    )
    @GetMapping("/accounts/{id}")
    fun getUserAccounts(@PathVariable id: Long): List<CurrencyAccountDto> {
        return accountService.getUserAccounts(id).map { it.toDto() }
    }


}