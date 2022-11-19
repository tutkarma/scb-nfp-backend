package dev.sorokin.hacksovcom.repo.account

import dev.sorokin.hacksovcom.service.account.CurrencyAccount
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyAccountJpaRepo: JpaRepository<CurrencyAccount, Long> {
    fun findAllByOwnerId(id: Long): List<CurrencyAccount>
    fun findByOwnerIdAndCurrencyName(id: Long, name: String): CurrencyAccount?
}