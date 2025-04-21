package com.adamdawi.learning_journal_api.database.repository

import com.adamdawi.learning_journal_api.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
}