package com.ranggacikal.basecleanarchitecture.domain.model

data class UserModel(
    val email: String,
    val id_user: String,
    val kode_team: String,
    val nama_lengkap: String,
    val no_handphone: String?,
    val password: String
)
