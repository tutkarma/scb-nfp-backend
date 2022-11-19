package dev.sorokin.hacksovcom.repo

import dev.sorokin.hacksovcom.service.user.domain.UserRegistrationRequest
import org.springframework.data.jpa.repository.JpaRepository

interface UserRegistrationRequestJpaRepo: JpaRepository<UserRegistrationRequest, Long> {
}