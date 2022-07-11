package com.ranggacikal.basecleanarchitecture.presentation.data_user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.domain.model.UserModel
import com.ranggacikal.basecleanarchitecture.domain.use_case.get_user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: UserUseCase
) : ViewModel() {
    private val state = MutableStateFlow<UserState>(UserState.Init)
    val mState: StateFlow<UserState> get() = state

    private fun setLoading() {
        state.value = UserState.OnLoad(true)
    }

    private fun hideLoading() {
        state.value = UserState.OnLoad(false)
    }

    private fun loadData(coins: List<UserModel>){
        state.value = UserState.SuccessLoad(coins)
    }

    fun getUsers() {
        Log.d("isCalledViewModel", "getCoinsCalled: true")
        getUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("isCalledViewModel", "isSuccess: true = ${result.data}")
                    hideLoading()
                    result.data?.let { loadData(it) }
//                    Log.d("isCalledViewModel", "isLoading: ${state.value.isLoading}")
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