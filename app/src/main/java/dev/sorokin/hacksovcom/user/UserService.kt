package dev.sorokin.hacksovcom.user

import dev.sorokin.hacksovcom.user.UserRepoMongo
import dev.sorokin.hacksovcom.user.domain.User
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class UserService(
    val userRepo: UserRepoMongo
) {

    fun findByLogin(login: String): User? {
        return userRepo.findUserByLogin(login)
    }

    fun findById(id: String): User? {
        return userRepo.findUserById(id)
    }

    fun registerUser(login: String, password: String) {
        if(findByLogin(login) != null)
            throw RuntimeException("Логин занят")
        saveUser(User(null, login, password))
    }

    private fun saveUser(user: User): User {
        return userRepo.save(user)
    }

}