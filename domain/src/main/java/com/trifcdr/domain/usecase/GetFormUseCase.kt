package com.trifcdr.domain.usecase

import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.FormData
import com.trifcdr.domain.repository.UserRepository

class GetFormUseCase(
    private val userRepository: UserRepository
) {

    suspend fun getForm(id: Int): DataResource<FormData> {
        return userRepository.getForm(id)
    }

}