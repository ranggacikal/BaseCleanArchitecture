package com.ranggacikal.basecleanarchitecture.domain.use_case.get_user

import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.data.remote.dto.toUserModel
import com.ranggacikal.basecleanarchitecture.domain.model.UserModel
import com.ranggacikal.basecleanarchitecture.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<UserModel>>> = flow {
        try {
            emit(Resource.Loading())
            val users = repository.getDataUser().map { it.toUserModel() }
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            val users = repository.getDataUser().map { it.toUserModel() }
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occured",
                    users
                )
            )
        } catch (e: IOException) {
            val users = repository.getDataUser().map { it.toUserModel() }
            emit(
                Resource.Error(
                    "Couldn't reach server. check your internet connection",
                    users
                )
            )
        }
    }
}