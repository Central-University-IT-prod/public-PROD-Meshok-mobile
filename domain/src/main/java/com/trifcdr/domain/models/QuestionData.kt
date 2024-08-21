package com.trifcdr.domain.models

data class QuestionData(
    val id: Int,
    val description: String,
    val type: String,
    val category: String,
    val orgId: Int
)