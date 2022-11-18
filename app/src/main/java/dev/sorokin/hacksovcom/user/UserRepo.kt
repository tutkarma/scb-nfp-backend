package dev.sorokin.hacksovcom.user

import dev.sorokin.hacksovcom.user.domain.User
import org.springframework.data.mongodb.repository.MongoRepository


interface UserRepoMongo: MongoRepository<User, String> {

    fun findUserById(id: String): User?
    fun findUserByLogin(login: String): User?

}