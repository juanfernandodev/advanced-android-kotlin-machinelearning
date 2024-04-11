package com.juanferdev.appperrona.repositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.ApiService
import com.juanferdev.appperrona.api.dto.AddDogToUserDTO
import com.juanferdev.appperrona.api.dto.DogDTO
import com.juanferdev.appperrona.api.responses.DefaultResponse
import com.juanferdev.appperrona.api.responses.DogApiResponse
import com.juanferdev.appperrona.api.responses.DogListApiResponse
import com.juanferdev.appperrona.api.responses.DogListResponse
import com.juanferdev.appperrona.api.responses.DogResponse
import com.juanferdev.appperrona.doglist.DogRepository
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DogRepositoryTest {

    private val apiServiceMock = mock(ApiService::class.java)
    private val httpException = mock(HttpException::class.java)
    private val dogInBothCollections = DogDTO(
        id = 1L,
        index = 1,
        name = "Peluche"
    )
    private val allDogListResponse = DogListResponse(
        dogs = listOf(
            dogInBothCollections,
            DogDTO(
                id = 2L,
                index = 2,
                name = "Mona"
            ),
            DogDTO(
                id = 3L,
                index = 3,
                name = "Tarzan"
            )
        )
    )

    private val userDogListResponse = DogListResponse(
        dogs = listOf(
            dogInBothCollections
        )
    )

    @Test
    fun getDogCollectionWhenAllIsSuccessThenGetDogList(): Unit = runBlocking {
        //Give
        `when`(apiServiceMock.getAllDogs()).thenReturn(
            DogListApiResponse(
                message = String(),
                isSuccess = true,
                data = allDogListResponse
            )
        )
        `when`(apiServiceMock.getUserDogs()).thenReturn(
            DogListApiResponse(
                message = String(),
                isSuccess = true,
                data = userDogListResponse
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        //When
        val apiResponseStatus = dogRepository.getDogCollection() as ApiResponseStatus.Success
        //Then
        assert(apiResponseStatus.data.isNotEmpty())
    }

    @Test
    fun getDogCollectionWhenGetAllDogsThrowExceptionThenApiResponseStatusHasErrorMessage(): Unit =
        runBlocking {
            //Give
            doAnswer { throw Exception("sign_up_error") }.`when`(apiServiceMock).getAllDogs()
            val dogRepository = DogRepository(
                apiService = apiServiceMock,
                dispatcherIO = UnconfinedTestDispatcher()
            )
            //When
            val apiResponseStatus = dogRepository.getDogCollection() as ApiResponseStatus.Error
            //Then
            assertEquals(R.string.error_sign_up, apiResponseStatus.messageId)
        }

    @Test
    fun getDogCollectionWhenGetAllDogsThrowUnknownHostExceptionThenApiResponseStatusHasErrorMessage(): Unit =
        runBlocking {
            //Give
            doAnswer { throw UnknownHostException() }.`when`(apiServiceMock).getAllDogs()
            val dogRepository = DogRepository(
                apiService = apiServiceMock,
                dispatcherIO = UnconfinedTestDispatcher()
            )
            //When
            val apiResponseStatus = dogRepository.getDogCollection() as ApiResponseStatus.Error
            //Then
            assertEquals(R.string.error_no_internet, apiResponseStatus.messageId)
        }

    @Test
    fun getDogCollectionWhenGetUserDogsThrowHttpExceptionThenApiResponseStatusHasErrorMessage(): Unit =
        runBlocking {
            //Give
            `when`(apiServiceMock.getAllDogs()).thenReturn(
                DogListApiResponse(
                    message = String(),
                    isSuccess = true,
                    data = allDogListResponse
                )
            )
            `when`(httpException.code()).thenReturn(
                R.string.error_network
            )
            doAnswer { throw httpException }.`when`(apiServiceMock).getUserDogs()
            val dogRepository = DogRepository(
                apiService = apiServiceMock,
                dispatcherIO = UnconfinedTestDispatcher()
            )
            //When
            val apiResponseStatus = dogRepository.getDogCollection() as ApiResponseStatus.Error
            //Then
            assertEquals(R.string.error_network, apiResponseStatus.messageId)
        }

    @Test
    fun getDogCollectionWhenUserHasDogsThenCollectionHasTheDogOfTheUser(): Unit = runBlocking {
        //Give
        `when`(apiServiceMock.getAllDogs()).thenReturn(
            DogListApiResponse(
                message = String(),
                isSuccess = true,
                data = allDogListResponse
            )
        )
        `when`(apiServiceMock.getUserDogs()).thenReturn(
            DogListApiResponse(
                message = String(),
                isSuccess = true,
                data = userDogListResponse
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection() as ApiResponseStatus.Success
        assertNotNull(apiResponseStatus.data.firstOrNull { it.name == "Peluche" }) //Peluche is in both collections
    }

    @Test
    fun getDogCollectionWhenUserHaveNotADogThenDogIsNotInCollection(): Unit = runBlocking {
        //Give
        `when`(apiServiceMock.getAllDogs()).thenReturn(
            DogListApiResponse(
                message = String(),
                isSuccess = true,
                data = allDogListResponse
            )
        )
        `when`(apiServiceMock.getUserDogs()).thenReturn(
            DogListApiResponse(
                message = String(),
                isSuccess = true,
                data = userDogListResponse
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection() as ApiResponseStatus.Success
        val indexDogIsNotInCollection = 2
        assertFalse(
            apiResponseStatus.data.firstOrNull { it.index == indexDogIsNotInCollection }?.inCollection
                ?: true
        )
    }

    @Test
    fun addDogToUserWhenAllIsOkThenStatusIsSuccess(): Unit = runBlocking {
        val dogId = 1L
        val addDogToUserDTO = AddDogToUserDTO(dogId = dogId)
        `when`(apiServiceMock.addDogToUser(addDogToUserDTO)).thenReturn(
            DefaultResponse(
                message = "All is Good",
                isSuccess = true
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        val apiResponseStatus = dogRepository.addDogToUser(dogId = dogId)
        assert(apiResponseStatus is ApiResponseStatus.Success)
    }

    @Test
    fun addDogToUserWhenResponseIsNotSuccessThenStatusHasErrorMessage(): Unit = runBlocking {
        val dogId = 1L
        val errorMessage = "Dog already belongs to user"
        val addDogToUserDTO = AddDogToUserDTO(dogId = dogId)
        `when`(apiServiceMock.addDogToUser(addDogToUserDTO)).thenReturn(
            DefaultResponse(
                message = errorMessage,
                isSuccess = false
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        val apiResponseStatus = dogRepository.addDogToUser(dogId = dogId) as ApiResponseStatus.Error
        assertEquals(R.string.dog_already_belongs_to_user, apiResponseStatus.messageId)
    }

    @Test
    fun getRecognizedDogWhenAllIsOkThenStatusIsSuccess(): Unit = runBlocking {
        val capturedDogId = "1"
        `when`(apiServiceMock.getRecognizedDog(capturedDogId)).thenReturn(
            DogApiResponse(
                message = "All look good",
                isSuccess = true,
                data = DogResponse(
                    dog = dogInBothCollections
                )
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        val apiResponseStatus = dogRepository.getRecognizedDog(capturedDogId = capturedDogId)
        assert(apiResponseStatus is ApiResponseStatus.Success)
    }

    @Test
    fun getRecognizedDogWhenResponseIsNotSuccessThenStatusIsSuccess(): Unit = runBlocking {
        val capturedDogId = "1"
        `when`(apiServiceMock.getRecognizedDog(capturedDogId)).thenReturn(
            DogApiResponse(
                message = "There was an error",
                isSuccess = false,
                data = DogResponse(
                    dog = dogInBothCollections
                )
            )
        )
        val dogRepository = DogRepository(
            apiService = apiServiceMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        val apiResponseStatus =
            dogRepository.getRecognizedDog(capturedDogId = capturedDogId) as ApiResponseStatus.Error
        assertEquals(R.string.unknown_error, apiResponseStatus.messageId)
    }

}
