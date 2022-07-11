package com.ranggacikal.basecleanarchitecture.domain.use_case.edit_user

import com.ranggacikal.basecleanarchitecture.common.Resource
import com.ranggacikal.basecleanarchitecture.data.remote.dto.toEditUserModel
import com.ranggacikal.basecleanarchitecture.domain.model.EditUserModel
import com.ranggacikal.basecleanarchitecture.domain.model.LoginModel
import com.ranggacikal.basecleanarchitecture.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        id_user: String,
        nama_lengkap: String,
        no_handphone: String,
        email: String,
        password: String,
        kode_team: String
    ): Flow<Resource<EditUserModel>> = flow {
        try {
            emit(Resource.Loading())
            val editStatus = repository.postEditUser(
                id_user,
                nama_lengkap,
                no_handphone,
                email,
                password,
                kode_team
            ).toEditUserModel()
            emit(Resource.Success(editStatus))
        } catch (e: HttpException) {
            val editStatus = repository.postEditUser(
                id_user,
                nama_lengkap,
                no_handphone,
                email,
                password,
                kode_team
            ).toEditUserModel()
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occured",
                    editStatus
                )
            )
        } catch (e: IOException) {
            val editStatus = repository.postEditUser(
                id_user,
                nama_lengkap,
                no_handphone,
                email,
                password,
                kode_team
            ).toEditUserModel()
            emit(
                Resource.Error(
                    "Couldn't reach server. check your internet connection",
                    editStatus
                )
            )
        }
    }
}