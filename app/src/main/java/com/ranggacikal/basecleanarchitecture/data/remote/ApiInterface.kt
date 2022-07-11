package com.ranggacikal.basecleanarchitecture.data.remote

import com.ranggacikal.basecleanarchitecture.data.remote.dto.EditUserDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.LoginDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.UserByIdDto
import com.ranggacikal.basecleanarchitecture.data.remote.dto.UserDto
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginDto

    @GET("getDataUserById")
    suspend fun userById(
        @Query("id_user") id_user: String
    ): List<UserByIdDto>

    @GET("getDataUser")
    suspend fun getDataUser(): List<UserDto>

    @FormUrlEncoded
    @POST("editUser")
    suspend fun editUser(
        @Field("id_user") id_user: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("no_handphone") no_handphone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("kode_team") kode_team: String
    ): EditUserDto
}