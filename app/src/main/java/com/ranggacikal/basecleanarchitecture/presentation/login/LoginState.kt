package com.ranggacikal.basecleanarchitecture.presentation.login

sealed class LoginState {
    object Init: LoginState()
    data class OnLoad(val onLoad: Boolean): LoginState()
    data class OnLoadSuccess(val status: Int, val message: String ) : LoginState()
}