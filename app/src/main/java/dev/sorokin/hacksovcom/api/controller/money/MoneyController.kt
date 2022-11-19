package dev.sorokin.hacksovcom.api.controller.money

import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.money.MoneyService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/money")
class MoneyController(
    val moneyService: MoneyService,
    val sessionUserService: SessionUserService
) {

    @Operation(summary = "Пополнить счет по ID счета")
    @PostMapping("/deposit/{accountId}")    //todo logging it
    fun depositToAccount(
        @PathVariable accountId: Long,
        @RequestParam(name = "amount") amount: Double
    ) {
        val user = sessionUserService.getSessionUser()
        moneyService.deposit(user, accountId, amount)
    }

    @Operation(summary = "снять со счета по его ID")
    @PostMapping("/withdraw/{accountId}")
    fun withdrawFromAccount(
        @PathVariable accountId: Long,
        @RequestParam(name = "amount") amount: Double
    ) {
        val user = sessionUserService.getSessionUser()
        moneyService.withdraw(user, accountId, amount)
    }

    @Operation(summary = "Купить валюту за другую валюту")
    @PostMapping("/buy")
    fun buyCurrency(
        @RequestParam(name = "target") target: String,
        @RequestParam(name = "source") source: String,
        @RequestParam(name = "target") amount: Double
    ) {
        val user = sessionUserService.getSessionUser()
        moneyService.buyCurrency(source, target, amount, user)
    }

}