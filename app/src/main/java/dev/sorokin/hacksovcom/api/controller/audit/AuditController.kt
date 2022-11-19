package dev.sorokin.hacksovcom.api.controller.audit

import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.service.audit.AuditService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/audit")
class AuditController(
    val auditService: AuditService,
    val sessionUserService: SessionUserService
) {

    @Operation(summary = "Получить данные пользователя о его действиях")
    @GetMapping
    fun getUserAudit(): UserAudit {
        val user = sessionUserService.getSessionUser()
        return auditService.getUserAudit(user)
    }

}

@Schema(description = "все операции пользователя с деньгами")
data class UserAudit(
    @Schema(description = "операции снятия и зачисления денег") val accountActions: List<UserAccountAction>,
    @Schema(description = "операции покупки валюты") val currencyExchanges: List<UserCurrencyExchange>
)

@Schema(description = "операция пополнения или снятия со счета")
data class UserAccountAction(
    @Schema(description = "количество пополенных денег (положительное или отрицательное число)") val amount: Double,
    @Schema(description = "валюта счета", example = "RUB") val currency: String,
)

@Schema(description = "операция покупки валюты")
data class UserCurrencyExchange(
    @Schema(description = "валюта которая покупается") val targetCurrency: String,
    @Schema(description = "валюта за которую покупается target валюта") val sourceCurrency: String,
    @Schema(description = "количество купленной валюты") val amount: Double,
    @Schema(description = "Курс в момент покупки") val price: Double,
    @Schema(description = "Дата: timestamp в секундах") val date: Long,
)