package dev.sorokin.hacksovcom.service.account

import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.api.session.SessionUserService
import dev.sorokin.hacksovcom.repo.account.CurrencyAccountJpaRepo
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service
import java.util.*

@Service
class CurrencyAccountService(
    val repo: CurrencyAccountJpaRepo,
    val sessionUserService: SessionUserService,
) {

    @Required(UserRole.USER)
    fun getCurrentUserAccounts(): List<CurrencyAccount> {
        val user = sessionUserService.getSessionUser()
        return repo.findAllByOwnerId(user.id!!)
    }

    @Required(UserRole.ADMIN)
    fun getUserAccounts(userId: Long): List<CurrencyAccount> {
        return repo.findAllByOwnerId(userId)
    }

    @Required(UserRole.USER)
    fun getAccount(accountId: Long): CurrencyAccount {
        return repo.findById(accountId).orElse(null) ?:
            throw RuntimeException("Счет с Id: $accountId не найден")
    }

    @Required(UserRole.USER)
    fun createAccount(user: User, currencyName: String): CurrencyAccount {
        if(repo.findByOwnerIdAndCurrencyName(user.id!!, currencyName) != null)
            throw RuntimeException("Пользователь с id: ${user.id} уже имеет счет в валюте $currencyName")
        var accountNumber = "$currencyName${user.id}"
        accountNumber += genRandString(20L - accountNumber.length)
        return repo.save(CurrencyAccount(
            null,
            user.id!!,
            currencyName,
            0.0,
            accountNumber
        ))
    }

    fun createDefaultAccount(user: User): CurrencyAccount {
        return createAccount(user, "RUB")
    }

}

private fun genRandString(len: Long): String {
    val ran = Random()
    var randomChar = ' '
    var result = ""

    for (i in 0..len) {
        randomChar = '0'.plus(ran.nextInt(9))
        result = randomChar.toString() + result
    }
    return result;
}