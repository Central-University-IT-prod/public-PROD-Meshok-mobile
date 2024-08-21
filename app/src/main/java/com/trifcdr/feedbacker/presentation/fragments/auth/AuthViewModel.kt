package com.trifcdr.feedbacker.presentation.fragments.auth

import androidx.lifecycle.ViewModel
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.usecase.AuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUserUseCase: AuthUserUseCase
) : ViewModel() {

    fun authUser(email: String, password: String) = flow {
        try {
            emit(authUserUseCase.execute(email, password))
        } catch (e: Exception) {
            emit(DataResource.Failure(e))
        }
    }
}