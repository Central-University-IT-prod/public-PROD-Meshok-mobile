package com.trifcdr.feedbacker.presentation.fragments.scan

import androidx.lifecycle.ViewModel
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.usecase.GetFormUseCase
import com.trifcdr.domain.usecase.IsUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val getFormUseCase: GetFormUseCase,
    private val isUserLoginUseCase: IsUserLoginUseCase
) : ViewModel() {

    fun getForm(id: Int) = flow {
        try {
            emit(getFormUseCase.getForm(id))
        } catch (e: Exception) {
            emit(DataResource.Failure(e))
        }
    }

    fun isAuth(id: Int) = flow {
        try {
            emit(isUserLoginUseCase.execute())
        } catch (e: Exception) {
            emit(false)
        }
    }

}