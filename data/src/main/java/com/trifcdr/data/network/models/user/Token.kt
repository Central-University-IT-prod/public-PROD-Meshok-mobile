package com.trifcdr.data.network.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token (
    @SerialName("token")
    val accessToken: String
    )