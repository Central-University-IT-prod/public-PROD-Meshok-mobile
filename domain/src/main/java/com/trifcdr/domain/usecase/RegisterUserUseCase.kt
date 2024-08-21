package com.trifcdr.domain.usecase

import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.UserData
import com.trifcdr.domain.repository.UserRepository

class RegisterUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(user: UserData): DataResource<UserData> {
        return userRepository.registerUser(user)
    }
}