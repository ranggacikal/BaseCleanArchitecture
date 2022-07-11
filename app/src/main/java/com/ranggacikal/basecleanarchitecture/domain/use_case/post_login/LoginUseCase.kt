package com.ranggacikal.basecleanarchitecture.domain.use_case.post_login

import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.data.remote.dto.toLoginModel
import com.ranggacikal.basecleanarchitecture.domain.model.LoginModel
import com.ranggacikal.basecleanarchitecture.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
){
    operator fun invoke(email: String, password: String): Flow<Resource<LoginModel>> = flow {
        try {
            emit(Resource.Loading<LoginModel>())
            val login = repository.postLogin(email, password).toLoginModel()
            emit(Resource.Success<LoginModel>(login))
        } catch (e: HttpException){
            val login = repository.postLogin(email, password).toLoginModel()
            emit(
                Resource.Error<LoginModel>(
                    e.localizedMessage ?: "An unexpected error occured",
                    login
                )
            )
        } catch (e: IOException){
            val login = repository.postLogin(email, password).toLoginModel()
            emit(
                Resource.Error<LoginModel>(
                    "Couldn't reach server. check your internet connection",
                    login
                )
            )
        }
    }
}