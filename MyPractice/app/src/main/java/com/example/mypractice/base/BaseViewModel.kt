package com.example.mypractice.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState: IUiState, UiIntent: IUiIntent> : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(initUiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow
    protected abstract fun initUiState(): UiState

    private val _uiIntentFlow: Channel<UiIntent> = Channel()
    private val uiIntentFlow: Flow<UiIntent> = _uiIntentFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            uiIntentFlow.collect {
                handleIntent(it)
            }
        }
    }

    protected abstract fun handleIntent(intent: IUiIntent)

    fun sendUiIntent(uiIntent: UiIntent) {
        viewModelScope.launch {
            _uiIntentFlow.send(uiIntent)
        }
    }

    protected fun sendUiState(copy: UiState.() -> UiState) {
        _uiStateFlow.update {
            copy(_uiStateFlow.value)
        }
    }

    protected fun <T: Any> requestDataWithFlow(
        showLoading: Boolean = true,
        request: suspend () -> BaseData<T>,
        successCallback: (T) -> Unit,
        failCallback: suspend (String) -> Unit = { errMsg ->
            //默认异常处理
        }
    ) {
        viewModelScope.launch {
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    ReqState.Success -> {
                        sendLoadUiState(LoadUiState.showMainView)
                        baseData.data?.let {
                            successCallback(it)
                        }
                    }
                    ReqState.Error -> {
                        baseData.msg?.let {
                            error(it)
                        }
                    }
                }
            } catch (e: Exception) {
                    e.message ?.let {
                        failCallback(it)
                    }
            }
        }
    }
}