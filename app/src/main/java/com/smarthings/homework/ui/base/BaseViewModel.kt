package com.smarthings.homework.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smarthings.data.common.CoroutineContextProvider
import com.smarthings.data.connectivity.Connectivity
import com.smarthings.homework.extensions.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel<T : Any> : ViewModel(), KoinComponent {

    protected val coroutineContext: CoroutineContextProvider by inject()
    private val connectivity: Connectivity by inject()

    protected val _viewState = MutableLiveData<ViewState<T>>()
    val viewState: LiveData<ViewState<T>> get() = _viewState

    protected fun executeUseCase(action: suspend () -> Unit) {
        _viewState.value = Loading(); launch { action() }
    }
}