package com.trifcdr.domain.usecase

import com.trifcdr.domain.repository.UserRepository

class LogoutUserUseCase(
    private val repository: UserRepository
) {

    suspend fun execute() {
        repository.logout()
    }

}