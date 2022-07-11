package com.ranggacikal.basecleanarchitecture.data.remote.dto

import com.ranggacikal.basecleanarchitecture.domain.model.UserModel

data class UserDto(
    val email: String,
    val id_user: String,
    val kode_team: String,
    val nama_lengkap: String,
    val no_handphone: String,
    val password: String
)

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        email = email,
        id_user = id_user,
        kode_team = kode_team,
        nama_lengkap =  nama_lengkap,
        no_handphone = no_handphone,
        password = password
    )
}