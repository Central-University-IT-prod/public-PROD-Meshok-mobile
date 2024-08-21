package com.trifcdr.data.storage

import com.trifcdr.data.network.models.user.User
import com.trifcdr.domain.models.UserData

interface AppStorage {

    suspend fun saveToken(key: String): Boolean

    suspend fun getToken(): String

    suspend fun getUserData(): User

    suspend fun saveUserData(user: UserData): Boolean

}