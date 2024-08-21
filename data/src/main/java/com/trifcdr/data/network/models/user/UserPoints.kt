package com.trifcdr.data.network.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPoints(
    @SerialName("current_level")
    val currentLevel: String,
    @SerialName("next_level")
    val nextLevel: String? = null,
    @SerialName("min_xp")
    val minXp: Int,
    @SerialName("max_xp")
    val maxXp: Int,
    @SerialName("current_xp")
    val currentXp: Int
)