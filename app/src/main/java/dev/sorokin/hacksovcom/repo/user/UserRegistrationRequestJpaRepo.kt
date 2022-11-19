package dev.sorokin.hacksovcom.repo.user

import dev.sorokin.hacksovcom.service.user.domain.UserRegistrationRequest
import org.springframework.data.jpa.repository.JpaRepository

interface UserRegistrationRequestJpaRepo: JpaRepository<UserRegistrationRequest, Long> {
}