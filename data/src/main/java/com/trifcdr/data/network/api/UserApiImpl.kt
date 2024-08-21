package com.trifcdr.data.network.api

import com.trifcdr.data.network.URL
import com.trifcdr.data.network.models.FormAnswer
import com.trifcdr.data.network.models.Resource
import com.trifcdr.data.network.models.user.Token
import com.trifcdr.data.network.models.user.User
import com.trifcdr.domain.models.UserData
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserApiImpl(
    private val httpClient: HttpClient
) : UserApi {
    override suspend fun registerUser(userData: UserData): Resource<String> {
        var result: Resource<String> = Resource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            try {
                result = Resource.Success(
                    httpClient.post<String> {
                        url(REGISTRATION)
                        body = User(
                            email = userData.email,
                            password = userData.password,
                            age = userData.age,
                            gender = userData.gender
                        )
                    }
                )
            } catch (e: Exception) {
                if (e is ResponseException && e.response.status == HttpStatusCode.Conflict) {
                    result = Resource.BadRequest
                } else if (e is ResponseException && e.response.status == HttpStatusCode.UnprocessableEntity) {
                    result = Resource.ValidationError
                } else {
                    result = Resource.Failure(e)
                }
            }
        }.join()
        return result
    }

    override suspend fun authUser(login: String, password: String): Resource<Token> {
        var result: Resource<Token> = Resource.Unauthorized
        CoroutineScope(Dispatchers.IO).launch {
            try {
                result = Resource.Success(
                    httpClient.post<Token> {
                        url(AUTHORIZATION)
                        body = User(
                            email = login,
                            password = password
                        )

                    }
                )
            } catch (e: Exception) {
                if (e is ResponseException && e.response.status == HttpStatusCode.BadRequest) {
                    result = Resource.BadRequest
                } else if (e is ResponseException && e.response.status == HttpStatusCode.Conflict) {
                    result = Resource.ValidationError
                } else {
                    result = Resource.Failure(e)
                }
            }
        }.join()
        return result
    }

    override suspend fun getUserData(token: String): Resource<User> {
        var result: Resource<User> = Resource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            try {
                result = Resource.Success(
                    httpClient.get<User> {
                        url(USERDATA)
                        header("Authorization", "Bearer $token")
                    }
                )
            } catch (e: Exception) {
                if (e is ResponseException && e.response.status == HttpStatusCode.Unauthorized) {
                    result = Resource.BadRequest
                } else {
                    result = Resource.Failure(e)
                }
            }
        }.join()
        return result
    }

    override suspend fun getForm(token: String, id: Int): Resource<String> {
        var result: Resource<String> = Resource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            try {
                result = Resource.Success(
                    httpClient.get<String> {
                        url("$FORMS/$id")
                        header("Authorization", "Bearer $token")
                    }
                )
            } catch (e: Exception) {
                if (e is ResponseException && e.response.status == HttpStatusCode.Unauthorized) {
                    result = Resource.Unauthorized
                } else if (e is ResponseException && e.response.status == HttpStatusCode.Forbidden) {
                    result = Resource.Forbidden
                } else {
                    result = Resource.Failure(e)
                }
            }
        }.join()
        return result
    }

    override suspend fun sendForm(token: String, form: List<FormAnswer>): Resource<String> {
        var result: Resource<String> = Resource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            try {
                result = Resource.Success(
                    httpClient.post<String> {
                        url(FORMS)
                        header("Authorization", "Bearer $token")
                        body = form
                    }
                )
            } catch (e: Exception) {
                result = Resource.Failure(e)
            }
        }.join()
        return result
    }

    override suspend fun userHistory(token: String): Resource<String> {
        var result: Resource<String> = Resource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            result = try {
                Resource.Success(
                    httpClient.get<String> {
                        url(USER_HISTORY)
                        header("Authorization", "Bearer $token")
                    }
                )
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }.join()
        return result
    }


    companion object {
        private const val REGISTRATION = "$URL/users/register"
        private const val AUTHORIZATION = "$URL/users/login"
        private const val USERDATA = "$URL/users/me"
        private const val USER_HISTORY = "$URL/users/answers"
        private const val FORMS = "$URL/forms"
    }
}