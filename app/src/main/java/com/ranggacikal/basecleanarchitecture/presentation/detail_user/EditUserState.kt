package com.ranggacikal.basecleanarchitecture.presentation.detail_user

sealed class EditUserState {
    object Init : EditUserState()
    data class OnLoad(val onLoad: Boolean) : EditUserState()
    data class SuccessPost(val status: Int, val message: String) : EditUserState()
}
