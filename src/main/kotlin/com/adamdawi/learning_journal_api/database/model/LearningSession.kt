package com.adamdawi.learning_journal_api.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "learning_sessions")
data class LearningSession(
    @Id val id: ObjectId = ObjectId.get(),
    val ownerId: ObjectId,
    val date: Instant,
    val subject: String,
    val durationMinutes: Int,
    val materials: List<LearningMaterial> = emptyList(),
    val notes: String? = null
)