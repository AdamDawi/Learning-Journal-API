package com.adamdawi.learning_journal_api.database.model

data class LearningMaterial(
    val type: MaterialType,
    val title: String,
    val url: String? = null
)

enum class MaterialType {
    BOOK, VIDEO, ARTICLE, COURSE, OTHER
}