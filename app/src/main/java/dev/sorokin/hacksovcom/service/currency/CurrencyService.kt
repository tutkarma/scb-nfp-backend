package dev.sorokin.hacksovcom.service.currency

import dev.sorokin.hacksovcom.api.controller.currency.CurrencyDescriptionDto
import dev.sorokin.hacksovcom.api.controller.currency.CurrencyPriceDto
import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.service.api.CurrencyApi
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


@Service
class CurrencyService(
    val currencyApi: CurrencyApi,
) {

//    @Required(UserRole.USER)
    fun getAllCurrencies(): List<CurrencyDescriptionDto> {
        return currencyApi.getAllCurrencies().currencies!!
            .map { CurrencyDescriptionDto(it.key, it.value) }
            .toList()
    }

//    @Required(UserRole.USER)
    fun getCurrencyExchange(source: String, target: String): CurrencyPriceDto {
        return currencyApi.getCurrencyPrice(source, target).let {
            CurrencyPriceDto(
                source,
                target,
                it.quotes!!.entries.first().value
            )
        }
    }

//    @Required(UserRole.USER)
    fun getCurrencyFrame(
        source: String,
        target: String,
        startDate: Instant,
        endDate: Instant
    ): Map<Long, CurrencyPriceDto> {
        val resp = currencyApi.getFrame(source, target, startDate, endDate)
        val result = HashMap<Long, CurrencyPriceDto>()
        resp.quotes!!.map { it ->
            val stringDate = it.key
            val price = it.value.values.first()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(stringDate, formatter).toEpochSecond(LocalTime.MIN, ZoneOffset.MIN)
            result.put(date, CurrencyPriceDto(source, target, price))
        }
        return result;
    }


}