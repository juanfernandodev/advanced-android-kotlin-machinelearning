package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    suspend fun downloadDogs(): List<Dog> {
        return withContext(Dispatchers.IO){
            listOf(
                Dog(
                    1, 1, "Chihuahua", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
                ),
                Dog(
                    2, 1, "Labrador", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
                ), Dog(
                    3, 1, "Retriever", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
                ),
                Dog(
                    4, 1, "San Bernardo", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
                ),
                Dog(
                    6, 1, "Xoloscuincle", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
                )
            )
        }

    }

}