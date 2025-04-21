package com.adamdawi.learning_journal_api.controllers

import com.adamdawi.learning_journal_api.controllers.LearningSessionController.LearningSessionResponse
import com.adamdawi.learning_journal_api.database.model.LearningMaterial
import com.adamdawi.learning_journal_api.database.model.LearningSession
import com.adamdawi.learning_journal_api.database.repository.LearningSessionRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*
import java.time.Instant


// POST http:/localhost:8080/learning_session
// GET http:/localhost:8080/learning_session?ownerId=213
// DELETE http:/localhost:8080/learning_session/321

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
        @RequestBody body: LearningSessionRequest
    ): LearningSessionResponse {
        val learningSession = repository.save(
            LearningSession(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                subject = body.subject,
                durationMinutes = body.durationMinutes,
                materials = body.materials,
                notes = body.notes,
                date = Instant.now(),
                ownerId = ObjectId()
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

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        repository.deleteById(ObjectId(id))
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