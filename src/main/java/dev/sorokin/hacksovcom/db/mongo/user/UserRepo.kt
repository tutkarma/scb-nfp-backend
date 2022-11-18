package dev.sorokin.hacksovcom.db.mongo.user

import dev.sorokin.hacksovcom.service.user.domain.User
import org.springframework.data.mongodb.repository.MongoRepository


interface UserRepoMongo: MongoRepository<User, String> {

    fun findUserById(id: String): User?
    fun findUserByLogin(login: String): User?

}