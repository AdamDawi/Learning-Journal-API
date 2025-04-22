package com.adamdawi.learning_journal_api.controllers

import com.adamdawi.learning_journal_api.controllers.LearningSessionController.LearningSessionResponse
import com.adamdawi.learning_journal_api.database.model.LearningMaterial
import com.adamdawi.learning_journal_api.database.model.LearningSession
import com.adamdawi.learning_journal_api.database.repository.LearningSessionRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.Instant


// POST http:/localhost:8080/learning_sessions
// GET http:/localhost:8080/learning_sessions?ownerId=213
// DELETE http:/localhost:8080/learning_sessions/321

@RestController
@RequestMapping("/learning_sessions")
class LearningSessionController(
    private val repository: LearningSessionRepository
) {

    data class LearningSessionRequest(
        val id: String?,
        @field:NotBlank(message = "Subject for the learning session is required")
        val subject: String,
        val durationMinutes: Int,
        val materials: List<LearningMaterial> = emptyList(),
        val notes: String? = null
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
    fun save(
        @Valid @RequestBody body: LearningSessionRequest
    ): LearningSessionResponse {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        val learningSession = repository.save(
            LearningSession(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                subject = body.subject,
                durationMinutes = body.durationMinutes,
                materials = body.materials,
                notes = body.notes,
                date = Instant.now(),
                ownerId = ObjectId(ownerId)
            )
        )
        return learningSession.toLearningSessionResponse()
    }

    @GetMapping
    fun findByOwnerId(): List<LearningSessionResponse> {
        // ownerId from token
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toLearningSessionResponse()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val learningSession = repository.findById(ObjectId(id)).orElseThrow{
            IllegalArgumentException("Learning session not found")
        }
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        if(learningSession.ownerId.toHexString() == ownerId){
            repository.deleteById(ObjectId(id))
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