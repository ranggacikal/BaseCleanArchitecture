package com.ranggacikal.basecleanarchitecture.presentation.detail_user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.basecleanarchitecture.common.Constants
import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.domain.model.UserByIdModel
import com.ranggacikal.basecleanarchitecture.domain.model.UserModel
import com.ranggacikal.basecleanarchitecture.domain.use_case.edit_user.EditUserUseCase
import com.ranggacikal.basecleanarchitecture.domain.use_case.get_user.UserUseCase
import com.ranggacikal.basecleanarchitecture.domain.use_case.get_user_by_id.UserByIdUseCase
import com.ranggacikal.basecleanarchitecture.presentation.data_user.UserState
import com.ranggacikal.basecleanarchitecture.presentation.util.Constants.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val getDetailUserUseCase: UserByIdUseCase,
    private val postEditUserUseCase: EditUserUseCase
) : ViewModel() {
    private val state = MutableStateFlow<DetailUserState>(DetailUserState.Init)
    val mState: StateFlow<DetailUserState> get() = state

    private val stateEditUser = MutableStateFlow<EditUserState>(EditUserState.Init)
    val mStateEditUser: StateFlow<EditUserState> get() = stateEditUser

    var idUser: String = EMPTY_STRING
    var password: String = EMPTY_STRING
    var nama_lengkap: String = EMPTY_STRING
    var no_handphone: String = EMPTY_STRING
    var email: String = EMPTY_STRING
    var kode_team: String = EMPTY_STRING

    private fun setLoading() {
        state.value = DetailUserState.OnLoad(true)
    }

    private fun hideLoading() {
        state.value = DetailUserState.OnLoad(false)
    }

    private fun loadData(detailUser: List<UserByIdModel>) {
        state.value = DetailUserState.SuccessLoad(detailUser)
    }

    fun getDetailUser() {
        getDetailUserUseCase(idUser).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    hideLoading()
                    result.data?.let { loadData(it) }
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

    fun editUser() {
        postEditUserUseCase(idUser, nama_lengkap, no_handphone, email, password, kode_team)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        stateEditUser.value = EditUserState.OnLoad(false)
                        result.data?.let {
                            stateEditUser.value = EditUserState.SuccessPost(it.status, it.pesan)
                        }
                    }
                    is Resource.Error -> {
                        stateEditUser.value = EditUserState.OnLoad(false)
                        result.data?.let {
                            stateEditUser.value = EditUserState.SuccessPost(it.status, it.pesan)
                        }
                    }
                    is Resource.Loading -> {
                        stateEditUser.value = EditUserState.OnLoad(false)
                    }
                }
            }.launchIn(viewModelScope)
    }


}