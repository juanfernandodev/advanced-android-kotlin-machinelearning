package com.juanferdev.appperrona.api

import com.juanferdev.appperrona.BASE_URL
import com.juanferdev.appperrona.GET_ALL_DOGS_URL
import com.juanferdev.appperrona.SIGN_IN_URL
import com.juanferdev.appperrona.SIGN_UP_URL
import com.juanferdev.appperrona.api.dto.SignInDTO
import com.juanferdev.appperrona.api.dto.SignUpDTO
import com.juanferdev.appperrona.api.responses.AuthApiResponse
import com.juanferdev.appperrona.api.responses.DogListApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun signIn(@Body signInDTO: SignInDTO): AuthApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}