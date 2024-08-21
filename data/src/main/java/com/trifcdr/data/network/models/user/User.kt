package com.trifcdr.data.network.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String? = null,
    @SerialName("age")
    val age: Int? = null,
    @SerialName("gender")
    val gender: String? = null,
    val accessToken: String? = null,
    @SerialName("points")
    val points: UserPoints? = null
)