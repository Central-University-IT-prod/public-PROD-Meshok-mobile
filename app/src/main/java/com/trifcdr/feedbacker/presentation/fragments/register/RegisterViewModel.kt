package com.trifcdr.feedbacker.presentation.fragments.register

import androidx.lifecycle.ViewModel
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {


    fun registerUser(user: com.trifcdr.domain.models.UserData) = flow {
        try {
            emit(registerUserUseCase.execute(user))
        } catch (e: Exception) {
            emit(DataResource.Failure(e))
        }
    }
}