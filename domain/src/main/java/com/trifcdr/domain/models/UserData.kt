package com.trifcdr.domain.models

data class UserData(
    val email: String,
    val password: String? = null,
    val age: Int,
    val gender: String,
    val accessToken: String? = null,
    val currentLevel: String? = null,
    val nextLevel: String? = null,
    val minXp: Int? = null,
    val maxXp: Int? = null,
    val currentXp: Int? = null
)