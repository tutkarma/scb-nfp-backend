package dev.sorokin.hacksovcom.api.controller.currency

import dev.sorokin.hacksovcom.service.currency.CurrencyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.jpa.repository.Query
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.Date

@RestController
@RequestMapping("/api/currency")
class CurrencyController(
    val currencyService: CurrencyService
) {

    @Operation(
        summary = "Выдать все доступные валюты",
        description = "реализовано"
    )
    @GetMapping
    fun getAllCurrencies(): List<CurrencyDescriptionDto> {
        return currencyService.getAllCurrencies()
    }


    @Operation(
        summary = "Выдать цену покупки валюты Х (target) за валюту Y (source)",
        description = "реализовано"
    )
    @GetMapping("/exchange")
    fun getCurrencyExchangePrice(
        @RequestParam(name = "target") target: String,
        @RequestParam(name = "source") source: String,
    ): CurrencyPriceDto {
        return currencyService.getCurrencyExchange(source, target);
    }

    @Operation(
        summary = "Вернуть данные для графика за последний год валюты Х" +
                "\n target - то что покупают (по оси Y), source - за что покупают (ось X)" +
                "\nВозвращает map: дата --> цена",
        description = "реализовано"
    )
    @GetMapping("/frame/year")
    fun getLastYearFrame(
        @RequestParam(name = "target") target: String,
        @RequestParam(name = "source") source: String,
    ): Map<Long, CurrencyPriceDto> {
        val startDate = Instant.now().minus(364, ChronoUnit.DAYS)
        val endDate = Instant.now()
        return currencyService.getCurrencyFrame(source, target, startDate, endDate)
    }

    @Operation(
        summary = "Вернуть данные для графика за последние 3 месяца валюты Х" +
                "\n target - то что покупают (по оси Y), source - за что покупают (ось X)" +
                "\nВозвращает map: дата --> цена",
        description = "реализовано"
    )
    @GetMapping("/frame/3month")
    fun getLast3MonthFrame(
        @RequestParam(name = "target") target: String,
        @RequestParam(name = "source") source: String,
    ): Map<Long, CurrencyPriceDto> {
        val startDate = Instant.now().minus(90, ChronoUnit.DAYS)
        val endDate = Instant.now()
        return currencyService.getCurrencyFrame(source, target, startDate, endDate)
    }

    @Operation(
        summary = "Вернуть данные для графика за последнюю неделю валюты" +
                "\n target - то что покупают (по оси Y), source - за что покупают (ось X)" +
                "\n Возвращает map: дата --> цена",
        description = "реализовано"
    )
    @GetMapping("/frame/week")
    fun getLastWeekFrame(
        @RequestParam(name = "target") target: String,
        @RequestParam(name = "source") source: String,
    ): Map<Long, CurrencyPriceDto> {
        val startDate = Instant.now().minus(7, ChronoUnit.DAYS)
        val endDate = Instant.now()
        return currencyService.getCurrencyFrame(source, target, startDate, endDate)
    }

}

@Schema(description = "Класс описывающий валюту")
data class CurrencyDescriptionDto(
    @Schema(description = "имя валюты", example = "RUB") val name: String,
    @Schema(description = "описание валюты") val description: String,
)

@Schema(description = "имя валюты", example = "RUB")
data class CurrencyPriceDto(
    @Schema(
        description = "имя валюты за которую покупается вторая валюта",
        example = "RUB"
    ) val currencySourceName: String,
    @Schema(description = "имя валюты, которую покупают", example = "USD") val currencyTargetName: String,
    @Schema(
        description = "количество первой валюты необходимое, чтобы купить 1шт второй",
        example = "60.12"
    ) val price: Double,
)