package com.trifcdr.feedbacker.presentation.fragments.profile

import androidx.lifecycle.ViewModel
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.usecase.GetUserDataUseCase
import com.trifcdr.domain.usecase.GetUserHistoryUseCase
import com.trifcdr.domain.usecase.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserHistoryUseCase: GetUserHistoryUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    fun getUserData() = flow {
        try {
            emit(getUserDataUseCase.execute())
        } catch (e: Exception) {
            emit(DataResource.Failure(e))
        }
    }

    fun getAnswerHistory() = flow {
        emit(getUserHistoryUseCase.execute())
    }

    suspend fun logout() {
        logoutUserUseCase.execute()
    }
}