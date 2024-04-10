package com.juanferdev.appperrona.api

import com.juanferdev.appperrona.ADD_DOG_TO_USER_URL
import com.juanferdev.appperrona.GET_ALL_DOGS_URL
import com.juanferdev.appperrona.GET_DOG_BY_ML_ID
import com.juanferdev.appperrona.GET_USER_DOGS
import com.juanferdev.appperrona.SIGN_IN_URL
import com.juanferdev.appperrona.SIGN_UP_URL
import com.juanferdev.appperrona.api.dto.AddDogToUserDTO
import com.juanferdev.appperrona.api.dto.SignInDTO
import com.juanferdev.appperrona.api.dto.SignUpDTO
import com.juanferdev.appperrona.api.responses.AuthApiResponse
import com.juanferdev.appperrona.api.responses.DefaultResponse
import com.juanferdev.appperrona.api.responses.DogApiResponse
import com.juanferdev.appperrona.api.responses.DogListApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun signIn(@Body signInDTO: SignInDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS)
    suspend fun getUserDogs(): DogListApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getRecognizedDog(@Query("ml_id") capturedDogId: String): DogApiResponse

}
