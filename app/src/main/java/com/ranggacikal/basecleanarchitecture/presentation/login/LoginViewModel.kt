package com.ranggacikal.basecleanarchitecture.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.basecleanarchitecture.common.Constants
import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.domain.use_case.post_login.LoginUseCase
import com.ranggacikal.basecleanarchitecture.presentation.util.Constants.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: LoginUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val state = MutableStateFlow<LoginState>(LoginState.Init)
    val mState: StateFlow<LoginState> get() = state
    var email: String = EMPTY_STRING
    var password: String = EMPTY_STRING

    private fun validateLogin(isValid: Boolean): Boolean {
        return isValid
    }

    private fun setLoading() {
        state.value = LoginState.OnLoad(true)
    }

    private fun hideLoading() {
        state.value = LoginState.OnLoad(false)
    }

    private fun sukses(status: Int, message: String) {
        state.value = LoginState.OnLoadSuccess(status, message)
    }

     fun postLogin() = viewModelScope.launch {
        postLoginUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    hideLoading()
                    result.data?.let { sukses(it.status, it.pesan) }
                }
                is Resource.Error -> {
                    hideLoading()

                }
                is Resource.Loading -> {
                    setLoading()
                }
            }
        }.launchIn(viewModelScope)
    }
}