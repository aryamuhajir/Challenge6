package com.binar.challenge5.network

import com.binar.challenge5.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("apifilm.php")
    fun getAllFilm() : Call<List<GetAllFilmResponseItem>>

    @POST ("register.php/")
    @FormUrlEncoded
    fun registerUser(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("username") username :String
    ) : Call<ResponseRegister>

    @POST("login.php")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<Responseuser>

    @POST("updateuser.php")
    @FormUrlEncoded
    fun updateUser(
        @Field("id") id : Int,
        @Field("username") username: String,
        @Field("complete_name") completename : String,
        @Field("address") address : String,
        @Field("dateofbirth") dateofbirth : String,
    ) : Call<Responseuser>

    @POST("updateuser.php")
    @FormUrlEncoded
    fun updateUser2(
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("complete_name") completename: String,
        @Field("address") address: String,
        @Field("dateofbirth") dateofbirth: String,
    ): Call<List<Responseuser>>


}