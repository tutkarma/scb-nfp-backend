package dev.sorokin.hacksovcom.repo

import dev.sorokin.hacksovcom.service.user.domain.User
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.Table

@Repository
interface UserJpaRepo: JpaRepository<User, Long> {
    fun findUserByName(name: String): User?
    fun findUserByPhone(phone: String): User?
}