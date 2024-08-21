package com.trifcdr.domain.usecase

import com.trifcdr.domain.models.AnswersHistoryData
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.repository.UserRepository

class GetUserHistoryUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(): DataResource<AnswersHistoryData> {
        return userRepository.getHistory()
    }

}