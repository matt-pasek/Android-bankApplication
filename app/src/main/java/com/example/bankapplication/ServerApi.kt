package com.example.bankapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


data class User (
    val token: String,
)

data class Login (
    val username: String,
    val password: String
)

interface ServerApi {
    companion object {
        const val BASE_URL = "http://192.168.1.104:8080/"
    }

    @POST("/api/user/login")
    fun userLogin(@Body login: String): Call<User>
}