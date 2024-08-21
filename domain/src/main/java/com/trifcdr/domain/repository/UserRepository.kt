package com.trifcdr.domain.repository

import com.trifcdr.domain.models.AnswersHistoryData
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.FormAnswerData
import com.trifcdr.domain.models.FormData
import com.trifcdr.domain.models.QuestionData
import com.trifcdr.domain.models.UserData

interface UserRepository {

    suspend fun registerUser(user: UserData): DataResource<UserData>
    suspend fun getUserData(): DataResource<UserData>
    suspend fun getForm(id: Int): DataResource<FormData>
    suspend fun authUser(email: String, password: String): DataResource<UserData>
    suspend fun sendForm(form: List<FormAnswerData>): Boolean
    suspend fun getHistory(): DataResource<AnswersHistoryData>
    suspend fun isUserAuth(): Boolean

    suspend fun logout()
}