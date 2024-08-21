package com.trifcdr.feedbacker

import androidx.lifecycle.ViewModel
import com.trifcdr.domain.usecase.IsUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainActViewModel @Inject constructor(
    private val isUserLoginUseCase: IsUserLoginUseCase

) : ViewModel() {
    fun isAuth() = flow {
        try {
            emit(isUserLoginUseCase.execute())
        } catch (e: Exception) {
            emit(false)
        }
    }
}