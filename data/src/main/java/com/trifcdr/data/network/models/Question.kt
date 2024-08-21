package com.trifcdr.data.network.models

data class Question(
    val id: Int,
    val description: String,
    val type: String,
    val category: String,
    val orgId: Int
)