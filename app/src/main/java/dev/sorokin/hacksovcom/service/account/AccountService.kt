package dev.sorokin.hacksovcom.service.account

import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service

@Service
class AccountService {

    @Required(UserRole.USER)
    fun getUserAccounts(user: User): List<CurrencyAccount> {
        TODO("Not yet implemented")
    }

    @Required(UserRole.USER)
    fun getAccount(accountId: Long): CurrencyAccount {
        TODO("Not yet implemented")
    }

    @Required(UserRole.USER)
    fun createAccount(user: User, currencyName: String): CurrencyAccount {
        TODO("Not yet implemented")
    }

}