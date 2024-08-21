package com.trifcdr.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormAnswer(
    @SerialName("question_id")
    val id: Int,
    @SerialName("answer")
    val answer: Int
)