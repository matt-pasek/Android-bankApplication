package com.example.bankapplication

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiRequests {
    companion object {
        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val retrofit = Retrofit.Builder()
            .baseUrl(ServerApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()


        private val api: ServerApi = retrofit.create(ServerApi::class.java)

        fun userLogin(login: String): User? {
            api.userLogin(login).execute().body()?.let {
                Log.d("API", "token: $it")
                return it
            }
            return null
        }
    }
}