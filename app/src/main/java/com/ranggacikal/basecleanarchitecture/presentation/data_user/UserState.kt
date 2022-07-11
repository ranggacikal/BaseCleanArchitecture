package com.ranggacikal.basecleanarchitecture.presentation.data_user

import com.ranggacikal.basecleanarchitecture.domain.model.UserModel

sealed class UserState {
    object Init: UserState()

    data class OnLoad(val onLoad: Boolean): UserState()
    data class SuccessLoad(val listUser: List<UserModel>) : UserState()
}