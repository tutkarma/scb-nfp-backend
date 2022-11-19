package dev.sorokin.hacksovcom.service.currency

import dev.sorokin.hacksovcom.api.controller.currency.CurrencyDescriptionDto
import dev.sorokin.hacksovcom.api.controller.currency.CurrencyPriceDto
import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CurrencyService {

    @Required(UserRole.USER)
    fun getAllCurrencies(): List<CurrencyDescriptionDto> {
        TODO("Not yet implemented")
    }

    @Required(UserRole.USER)
    fun getCurrencyExchange(source: String, target: String): CurrencyPriceDto {
        TODO("Not yet implemented")
    }

    @Required(UserRole.USER)
    fun getCurrencyFrame(
        source: String,
        target: String,
        startDate: Instant?,
        endDate: Instant?
    ): Map<String, CurrencyPriceDto> {
        TODO("Not yet implemented")
    }


}