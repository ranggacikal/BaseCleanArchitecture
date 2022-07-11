package com.ranggacikal.basecleanarchitecture.data.remote.dto

import com.ranggacikal.basecleanarchitecture.domain.model.EditUserModel

data class EditUserDto(
    val pesan: String,
    val status: Int
)

fun EditUserDto.toEditUserModel(): EditUserModel{
    return EditUserModel(
        pesan = pesan,
        status = status
    )
}