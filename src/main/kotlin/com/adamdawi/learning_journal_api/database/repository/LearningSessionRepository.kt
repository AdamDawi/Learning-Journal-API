package com.adamdawi.learning_journal_api.database.repository

import com.adamdawi.learning_journal_api.database.model.LearningSession
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface LearningSessionRepository: MongoRepository<LearningSession, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<LearningSession>
}