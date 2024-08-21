package com.trifcdr.domain.usecase

import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.UserData
import com.trifcdr.domain.repository.UserRepository

class AuthUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(email: String, password: String): DataResource<UserData> {
        return userRepository.authUser(email, password)
    }
}