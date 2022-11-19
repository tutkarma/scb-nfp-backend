package dev.sorokin.hacksovcom.api.controller.account

import org.springframework.web.bind.annotation.RestController
import java.util.StringJoiner
import javax.management.monitor.StringMonitor

@RestController
class CurrencyAccountController {


    fun createAccount(): CurrencyAccountDto {
        return TODO()
    }

    fun getAllMyAccounts(): List<CurrencyAccountDto> {
        return TODO()
    }

    fun getAccountInfo(accountId: Long): CurrencyAccountDto {
        return TODO()
    }

}

data class CurrencyAccountDto(
    val id: Long,
    val currencyName: String,
    val accountAmount: Double,
    val accountNumber: String,
)