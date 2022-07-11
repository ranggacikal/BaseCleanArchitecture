package com.ranggacikal.basecleanarchitecture.data.repository

import com.ranggacikal.basecleanarchitecture.data.remote.ApiInterface
import com.ranggacikal.basecleanarchitecture.data.remote.dto.EditUserDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.LoginDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.UserByIdDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.UserDto
import com.ranggacikal.basecleanarchitecture.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : UserRepository {
    override suspend fun postLogin(email: String, password: String): LoginDto {
        return api.postLogin(email, password)
    }

    override suspend fun getDataUser(): List<UserDto> {
        return api.getDataUser()
    }

    override suspend fun getUserById(id_user: String): List<UserByIdDto> {
        return api.userById(id_user)
    }

    override suspend fun postEditUser(
        id_user: String,
        nama_lengkap: String,
        no_handphone: String,
        email: String,
        password: String,
        kode_team: String
    ): EditUserDto {
        return api.editUser(id_user, nama_lengkap, no_handphone, email, password, kode_team)
    }

}