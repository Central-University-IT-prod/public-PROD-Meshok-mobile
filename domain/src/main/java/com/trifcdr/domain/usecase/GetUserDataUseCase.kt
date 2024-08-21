package com.trifcdr.domain.usecase

import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.UserData
import com.trifcdr.domain.repository.UserRepository

class GetUserDataUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(): DataResource<UserData> {
        return userRepository.getUserData()
    }
}
