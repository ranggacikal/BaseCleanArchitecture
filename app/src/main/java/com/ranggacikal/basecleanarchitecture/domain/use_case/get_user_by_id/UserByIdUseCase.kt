package com.ranggacikal.basecleanarchitecture.domain.use_case.get_user_by_id

import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.data.remote.dto.toUserByIdModel
import com.ranggacikal.basecleanarchitecture.data.remote.dto.toUserModel
import com.ranggacikal.basecleanarchitecture.domain.model.UserByIdModel
import com.ranggacikal.basecleanarchitecture.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(id_user: String): Flow<Resource<List<UserByIdModel>>> = flow {
        try {
           emit(Resource.Loading())
           val userById = repository.getUserById(id_user).map { it.toUserByIdModel() }
            emit(Resource.Success(userById))
        } catch (e: HttpException){
            val userById = repository.getUserById(id_user).map { it.toUserByIdModel() }
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occured",
                    userById
                )
            )
        } catch (e: IOException){
            val userById = repository.getUserById(id_user).map { it.toUserByIdModel() }
            emit(
                Resource.Error(
                    "Couldn't reach server. check your internet connection",
                    userById
                )
            )
        }
    }
}