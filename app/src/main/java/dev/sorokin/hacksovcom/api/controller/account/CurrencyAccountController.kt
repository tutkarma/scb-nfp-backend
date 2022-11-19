package dev.sorokin.hacksovcom.api.controller.account

import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.account.CurrencyAccountService
import dev.sorokin.hacksovcom.service.account.CurrencyAccount
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/account")
class CurrencyAccountController(
    val currencyAccountService: CurrencyAccountService,
    val sessionUserService: SessionUserService,
) {

    @Operation(
        summary = "Создать аккаунт текущему пользователю в валюте currencyName",
        description = "реализовано"
    )
    @PostMapping
    fun createAccount(@RequestBody currencyName: String): CurrencyAccountDto {
        val user = sessionUserService.getSessionUser()
        return currencyAccountService.createAccount(user, currencyName).toDto()
    }

    @Operation(
        summary = "Выдать все аккаунты пользователя",
        description = "реализовано"
    )
    @GetMapping("/my")
    fun getAllMyAccounts(): List<CurrencyAccountDto> {
        val user = sessionUserService.getSessionUser()
        return currencyAccountService.getUserAccounts(user).map { it.toDto() }
    }

    @Operation(
        summary = "Информация об аккаунте по его ID",
        description = "реализовано"
    )
    @GetMapping("{id}")
    fun getAccountInfo(@PathVariable(name = "id") accountId: Long): CurrencyAccountDto {
        return currencyAccountService.getAccount(accountId).toDto()
    }

}

@Schema(description = "")
data class CurrencyAccountDto(
    @Schema(description = "id счета") val id: Long,
    @Schema(description = "id владельца счета") val ownerId: Long,
    @Schema(description = "валюта счета") val currencyName: String,
    @Schema(description = "баланс счета") val accountAmount: Double,
    @Schema(description = "номер счета") val accountNumber: String,
)

fun CurrencyAccount.toDto() = CurrencyAccountDto(
    id!!,
    ownerId,
    currencyName,
    accountAmount,
    accountNumber
)
