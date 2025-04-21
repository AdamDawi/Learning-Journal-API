package com.adamdawi.learning_journal_api.controllers

import com.adamdawi.learning_journal_api.controllers.LearningSessionController.LearningSessionResponse
import com.adamdawi.learning_journal_api.database.model.LearningMaterial
import com.adamdawi.learning_journal_api.database.model.LearningSession
import com.adamdawi.learning_journal_api.database.repository.LearningSessionRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/learning_session")
class LearningSessionController(
    private val repository: LearningSessionRepository
) {

    data class LearningSessionRequest(
        val id: String?,
        val subject: String,
        val durationMinutes: Int,
        val materials: List<LearningMaterial> = emptyList(),
        val notes: String? = null,
        val ownerId: String
    )

    data class LearningSessionResponse(
        val id: String,
        val date: Instant,
        val subject: String,
        val durationMinutes: Int,
        val materials: List<LearningMaterial> = emptyList(),
        val notes: String? = null
    )

    //Insert new or update old
    @PostMapping
    fun save(body: LearningSessionRequest): LearningSessionResponse {
        val learningSession = repository.save(
            LearningSession(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                subject = body.subject,
                durationMinutes = body.durationMinutes,
                materials = body.materials,
                notes = body.notes,
                date = Instant.now(),
                ownerId = ObjectId(body.ownerId)
            )
        )
        return learningSession.toLearningSessionResponse()
    }

    @GetMapping
    fun findByOwnerId(
        @RequestParam(required = true) ownerId: String
    ): List<LearningSessionResponse> {
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toLearningSessionResponse()
        }
    }
}

private fun LearningSession.toLearningSessionResponse(): LearningSessionResponse {
    return LearningSessionResponse(
        id = this.id.toHexString(),
        date = this.date,
        subject = this.subject,
        durationMinutes = this.durationMinutes,
        materials = this.materials,
        notes = this.notes
    )
}