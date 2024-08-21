package com.trifcdr.feedbacker.presentation.fragments.form

import androidx.lifecycle.ViewModel
import com.trifcdr.domain.models.FormAnswerData
import com.trifcdr.domain.usecase.GetFormUseCase
import com.trifcdr.domain.usecase.SendFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val getFormUseCase: GetFormUseCase,
    private val sendFormUseCase: SendFormUseCase
) : ViewModel() {

    fun getForm(id: Int) = flow {
        emit(getFormUseCase.getForm(id))
    }

    fun sendForm(form: List<FormAnswerData>) = flow {
        emit(sendFormUseCase.execute(form))
    }
}