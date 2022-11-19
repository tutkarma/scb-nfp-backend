package dev.sorokin.hacksovcom.service.user

import dev.sorokin.hacksovcom.api.controller.user.dto.RegisterDto
import dev.sorokin.hacksovcom.api.security.Required
import dev.sorokin.hacksovcom.api.toTimestamp
import dev.sorokin.hacksovcom.repo.user.UserJpaRepo
import dev.sorokin.hacksovcom.repo.user.UserRegistrationRequestJpaRepo
import dev.sorokin.hacksovcom.service.account.CurrencyAccountService
import dev.sorokin.hacksovcom.service.user.domain.User
import dev.sorokin.hacksovcom.service.user.domain.UserRegistrationRequest
import dev.sorokin.hacksovcom.service.user.domain.UserRole
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant
import java.util.TimeZone

@Service
class UserService(
    val userRepo: UserJpaRepo,
    val passwordEncoder: PasswordEncoder,
    val userRegistrationRepo: UserRegistrationRequestJpaRepo,
    val accountService: CurrencyAccountService,
) {

    fun findByName(login: String): User? {
        return userRepo.findUserByName(login)
    }

    fun findById(id: Long): User? {
        return userRepo.findById(id).orElse(null)
    }

    fun registerUser(dto: RegisterDto) {
        if(findByName(dto.name) != null)
            throw RuntimeException("Логин занят")
        val hashedPass = passwordEncoder.encode(dto.password)
        dto.password = hashedPass
        createRegistrationRequest(dto)
    }

    @Required(UserRole.ADMIN)
    fun getAllRegistrationRequests(): List<UserRegistrationRequest> {
        return userRegistrationRepo.findAll()
    }

    @Required(UserRole.ADMIN)
    fun getAllUsers(): List<User> {
        return userRepo.findAll()
    }

    @Required(UserRole.ADMIN)
    fun acceptRegistration(id: Long, isAccepted: Boolean) {
        val registrationRequest = userRegistrationRepo.findById(id).orElse(null) ?:
            throw java.lang.RuntimeException("Registration request not found")

        if(isAccepted){
            saveUser(registrationRequest)
        }
        userRegistrationRepo.deleteById(registrationRequest.id!!)
    }

    @Required(UserRole.ADMIN)
    fun setBlockedToUser(id: Long, isBlocked: Boolean) {
        var user = findById(id) ?: throw RuntimeException("User not found with id $id");
        user.isBlocked = isBlocked
        userRepo.save(user);
    }

    fun saveUser(dto: UserRegistrationRequest): User {
        return userRepo.save(dto.toUser()).also {
            accountService.createDefaultAccount(it)
        }
    }

    private fun createRegistrationRequest(dto: RegisterDto) {
        userRegistrationRepo.save(dto.toRequest())
    }

}

private fun RegisterDto.toRequest(): UserRegistrationRequest {
    return UserRegistrationRequest(
        null,
        name,
        phone,
        email,
        passport,
        password,
        birthDate.toTimestamp(),
        Timestamp.from(Instant.now())
    )
}

private fun UserRegistrationRequest.toUser(): User {
    return User(
        null,
        name,
        phone,
        email,
        passport,
        password,
        birthDate,
        false,
        UserRole.USER.id
    )
}
