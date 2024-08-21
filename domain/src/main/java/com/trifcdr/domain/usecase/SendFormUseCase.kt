package com.trifcdr.domain.usecase

import com.trifcdr.domain.models.FormAnswerData
import com.trifcdr.domain.repository.UserRepository

class SendFormUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(form: List<FormAnswerData>): Boolean {
        return userRepository.sendForm(form)
    }

}