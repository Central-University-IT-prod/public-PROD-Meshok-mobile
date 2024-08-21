package com.trifcdr.data.repository

import com.trifcdr.data.mappers.mapToDataForm
import com.trifcdr.data.mappers.mapToDomainForm
import com.trifcdr.data.mappers.mapToDomainHistory
import com.trifcdr.data.mappers.mapToDomainUser
import com.trifcdr.data.mappers.mapToDomainUserSerializable
import com.trifcdr.data.network.api.UserApi
import com.trifcdr.data.network.models.Resource
import com.trifcdr.data.storage.AppStorage
import com.trifcdr.domain.models.AnswersHistoryData
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.FormAnswerData
import com.trifcdr.domain.models.FormData
import com.trifcdr.domain.models.UserData
import com.trifcdr.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val appStorage: AppStorage
) : UserRepository {
    override suspend fun registerUser(user: UserData): DataResource<UserData> {
        var res: DataResource<UserData> = DataResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            when (val resource = userApi.registerUser(user)) {
                is Resource.Success -> {
                    val userData = mapToDomainUser(resource.result)
                    appStorage.saveUserData(userData)
                    appStorage.saveToken(userData.accessToken!!)
                    res = DataResource.Success(userData)
                }

                is Resource.ValidationError -> {
                    res = DataResource.ValidationError
                }

                is Resource.BadRequest -> {
                    res = DataResource.BadRequest
                }

                is Resource.Failure -> res = DataResource.Failure(resource.exception)
                else -> res = DataResource.Empty
            }
        }.join()
        return res
    }

    override suspend fun getUserData(): DataResource<UserData> {
        var res: DataResource<UserData> = DataResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            when (val resource = userApi.getUserData(appStorage.getToken())) {
                is Resource.Success -> {
                    val userData = mapToDomainUserSerializable(resource.result)
                    res = DataResource.Success(userData)
                }

                is Resource.ValidationError -> {
                    res = DataResource.ValidationError
                }

                is Resource.BadRequest -> {
                    res = DataResource.BadRequest
                }

                is Resource.Failure -> res = DataResource.Failure(resource.exception)
                else -> res = DataResource.Empty
            }
        }.join()
        return res
    }

    override suspend fun getForm(id: Int): DataResource<FormData> {
        var res: DataResource<FormData> = DataResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            res = when (val resource = userApi.getForm(appStorage.getToken(), id)) {
                is Resource.Success -> {
                    DataResource.Success(mapToDomainForm(resource.result))
                }

                is Resource.ValidationError -> {
                    DataResource.ValidationError
                }

                is Resource.BadRequest -> {
                    DataResource.BadRequest
                }

                is Resource.Failure -> DataResource.Failure(resource.exception)
                else -> DataResource.Empty
            }
        }.join()
        return res
    }

    override suspend fun authUser(email: String, password: String): DataResource<UserData> {
        var res: DataResource<UserData> = DataResource.Unauthorized
        CoroutineScope(Dispatchers.IO).launch {
            when (val resource = userApi.authUser(email, password)) {
                is Resource.Success -> {
                    res = DataResource.Authorized
                    appStorage.saveToken(resource.result.accessToken)
                }

                is Resource.ValidationError -> {
                    res = DataResource.ValidationError
                }

                is Resource.BadRequest -> {
                    res = DataResource.BadRequest
                }

                is Resource.Failure -> res = DataResource.Failure(resource.exception)
                else -> res = DataResource.Empty
            }
        }.join()
        return res
    }

    override suspend fun sendForm(form: List<FormAnswerData>): Boolean {
        var res = false
        CoroutineScope(Dispatchers.IO).launch {
            val resource = userApi.sendForm(appStorage.getToken(), mapToDataForm(form))
            if (resource is Resource.Success) {
                res = true
            }
        }.join()
        return res
    }

    override suspend fun getHistory(): DataResource<AnswersHistoryData> {
        var res: DataResource<AnswersHistoryData> = DataResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            val resource = userApi.userHistory(appStorage.getToken())
            if (resource is Resource.Success) {
                res = DataResource.Success(mapToDomainHistory(resource.result))
            }
        }.join()
        return res
    }

    override suspend fun isUserAuth(): Boolean {
        var res = false
        CoroutineScope(Dispatchers.IO).launch {
            if (appStorage.getToken() != "empty") {
                res = true
            }
        }.join()
        return res
    }

    override suspend fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            appStorage.saveToken("empty")
        }
    }

}