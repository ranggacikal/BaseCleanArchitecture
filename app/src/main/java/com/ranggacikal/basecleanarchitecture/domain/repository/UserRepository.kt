package com.ranggacikal.basecleanarchitecture.domain.repository

import com.ranggacikal.basecleanarchitecture.data.remote.dto.EditUserDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.LoginDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.UserByIdDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.UserDto

interface UserRepository {
    suspend fun postLogin(email: String, password: String): LoginDto
    suspend fun getDataUser(): List<UserDto>
    suspend fun getUserById(id_user: String): List<UserByIdDto>
    suspend fun postEditUser(
        id_user: String,
        nama_lengkap: String,
        no_handphone: String,
        email: String,
        password: String,
        kode_team: String
    ): EditUserDto
}