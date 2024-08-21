package com.trifcdr.domain.usecase

import com.trifcdr.domain.repository.UserRepository

class IsUserLoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(): Boolean {
        return userRepository.isUserAuth()
    }

}