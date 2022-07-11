package com.ranggacikal.basecleanarchitecture.presentation.detail_user

import com.ranggacikal.basecleanarchitecture.domain.model.UserByIdModel

sealed class DetailUserState {
    object Init: DetailUserState()

    data class OnLoad(val onLoad: Boolean): DetailUserState()
    data class SuccessLoad(val listUser: List<UserByIdModel>) : DetailUserState()
}
