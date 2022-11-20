package dev.sorokin.hacksovcom.service.api

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.MapDeserializer
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.*


@Service
class CurrencyApi(
    @Value("\${currency-api.token}")
    var apiToken: String,
) {

    val objectMapper = ObjectMapper()
    val httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(30))
        .build()

    val logger = LoggerFactory.getLogger(CurrencyApi::class.java)

    fun getAllCurrencies(): AllCurrenciesResponse {

        val url = "https://api.apilayer.com/currency_data/list"

        var request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .setHeader("apikey", apiToken)
            .build()

        try {

            val resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(resp.body(), AllCurrenciesResponse::class.java)

        } catch (e: Exception) {
            logger.error("Ошибка во время получения всех валют", e)
            throw RuntimeException("Ошибка во время получения всех валют")
        }

    }


    fun getCurrencyPrice(source: String, target: String): CurrencyPriceResponse {
        val url = "https://api.apilayer.com/currency_data/live?source=${target}&currencies=${source}"

        var request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .setHeader("apikey", apiToken)
            .build()

        try {

//            val resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            return objectMapper.readValue(resp.body(), CurrencyPriceResponse::class.java)

            return CurrencyPriceResponse(
                mapOf("USDRUB" to 60.0),
                "sd",
                false,
                null,
            )

        } catch (e: Exception) {
            logger.error("Ошибка во время получения всех валют")
            throw RuntimeException("Ошибка во время получения всех валют")
        }

    }

    fun getFrame(
        source: String,
        target: String,
        startDate: Instant,
        endDate: Instant
    ): CurrencyFrameResponse {
        val formatter = SimpleDateFormat("yyyy-MM-dd")

        val startFormatted: String = Date.from(startDate).let { formatter.format(it) }
        val endFormatted: String = Date.from(endDate).let { formatter.format(it) }

        val url = "https://api.apilayer.com/currency_data/timeframe?start_date=${startFormatted}&end_date=${endFormatted}&source=$target&currencies=$source"

        var request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .setHeader("apikey", apiToken)
            .build()

        try {

            val resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(resp.body(), CurrencyFrameResponse::class.java)

        } catch (e: Exception) {
            logger.error("Ошибка во время получения всех валют", e)
            throw RuntimeException("Ошибка во время получения всех валют")
        }
    }

    fun resetToken(newToken: String) {
        this.apiToken = newToken;
    }


}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class AllCurrenciesResponse(
    var success: Boolean? = null,
    @JsonDeserialize(using = MapDeserializer::class)
    var currencies: Map<String, String>? = null
) {
    constructor() : this(null, null)
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class CurrencyPriceResponse(
    var quotes: Map<String, Double>?,
    var source: String?,
    var success: Boolean?,
    var timestamp: Long?,
) {
    constructor() : this(null, null, null, null)
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class CurrencyFrameResponse(
    var success: Boolean?,
    var timeframe: Boolean?,
    var source: String?,
    var quotes: Map<String, Map<String, Double>>?
    ) {
    constructor() : this(null, null, null, null)
}