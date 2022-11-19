package dev.sorokin.hacksovcom.service.money

import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service

@Service
class MoneyService {

    @Required(UserRole.USER)
    fun deposit(user: User, accountId: Long, amount: Double) {
        TODO("Not yet implemented")
    }

    @Required(UserRole.USER)
    fun withdraw(user: User, accountId: Long, amount: Double) {
        TODO("Not yet implemented")
    }

    @Required(UserRole.USER)
    fun buyCurrency(source: String, target: String, amount: Double, user: User) {
        TODO("Not yet implemented")
    }

}