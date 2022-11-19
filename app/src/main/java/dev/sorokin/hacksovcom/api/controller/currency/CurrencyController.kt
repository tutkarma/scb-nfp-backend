package dev.sorokin.hacksovcom.api.controller.currency

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/currency")
class CurrencyController {

    fun getAllCurrencies(): List<CurrencyDescriptionDto> {
        return TODO()
    }

    fun getCurrencyExchangePrice(): CurrencyCurrentPriceDto {
        return TODO()
    }

}


data class CurrencyDescriptionDto(
    val name: String,
    val description: String,
)

data class CurrencyCurrentPriceDto(
    val currencySourceName: String,
    val currencyTargetName: String,
    val price: Double,
)