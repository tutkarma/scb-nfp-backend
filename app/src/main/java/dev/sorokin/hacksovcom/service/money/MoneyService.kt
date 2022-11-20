package dev.sorokin.hacksovcom.service.money

import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.repo.account.CurrencyAccountJpaRepo
import dev.sorokin.hacksovcom.service.account.CurrencyAccountService
import dev.sorokin.hacksovcom.service.api.CurrencyApi
import dev.sorokin.hacksovcom.service.currency.CurrencyService
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class MoneyService(
    val accountJpaRepo: CurrencyAccountJpaRepo,
    val accountService: CurrencyAccountService,
    val currencyApi: CurrencyApi,
    val currencyService: CurrencyService
) {

    //    @Required(UserRole.USER)
    fun deposit(user: User, accountId: Long, amount: Double) {
        val account = accountJpaRepo.findById(accountId).orElse(null)
            ?: throw RuntimeException("Счет не найден с id: $accountId")

        account.accountAmount += amount; // TODO запись в журнал истории
        accountJpaRepo.save(account)
    }

    //    @Required(UserRole.USER)
    fun withdraw(user: User, accountId: Long, amount: Double) {
        deposit(user, accountId, amount * -1)
    }

    //    @Required(UserRole.USER)
    fun buyCurrency(source: String, target: String, amount: Double, user: User) { // TODO запись в журнал истории
        val sourceAccount = accountJpaRepo.findByOwnerIdAndCurrencyName(user.id!!, source)
            ?: throw RuntimeException("Счет не найден с валютой: $source")
        val targetAccount = accountJpaRepo.findByOwnerIdAndCurrencyName(user.id!!, target)
            ?: throw RuntimeException("Счет не найден с валютой: $target")


        val price = currencyService.getCurrencyExchange(source, target)

        val neededMoney = amount * price.price;

        if(sourceAccount.accountAmount < neededMoney)
            throw RuntimeException("Недостаточно денег на счете: $source для проведения операции")

        sourceAccount.accountAmount -= neededMoney;
        targetAccount.accountAmount += amount;

        accountJpaRepo.save(sourceAccount)
        accountJpaRepo.save(targetAccount)
    }

}