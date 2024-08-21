package com.trifcdr.data.network.api

import com.trifcdr.data.network.models.FormAnswer
import com.trifcdr.data.network.models.Resource
import com.trifcdr.data.network.models.user.Token
import com.trifcdr.data.network.models.user.User
import com.trifcdr.domain.models.UserData

interface UserApi {

    suspend fun registerUser(userData: UserData): Resource<String>

    suspend fun authUser(login: String, password: String): Resource<Token>

    suspend fun getUserData(token: String): Resource<User>

    suspend fun getForm(token: String, id: Int): Resource<String>
    suspend fun sendForm(token: String, form: List<FormAnswer>): Resource<String>
    suspend fun userHistory(token: String): Resource<String>
}