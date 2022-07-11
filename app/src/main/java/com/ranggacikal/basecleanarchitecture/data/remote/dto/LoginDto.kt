package com.ranggacikal.basecleanarchitecture.data.remote.dto

import com.ranggacikal.basecleanarchitecture.domain.model.LoginModel

data class LoginDto(
    val pesan: String,
    val status: Int,
    val sukses: Boolean
)

fun LoginDto.toLoginModel(): LoginModel {
    return LoginModel(
        pesan = pesan,
        status = status,
        sukses = sukses
    )
}