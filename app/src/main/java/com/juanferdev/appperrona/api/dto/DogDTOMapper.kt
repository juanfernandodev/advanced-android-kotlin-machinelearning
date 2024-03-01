package com.juanferdev.appperrona.api.dto

import com.juanferdev.appperrona.Dog

class DogDTOMapper {

    fun fromDogDTOListToDogDomainList(dogDTOList: List<DogDTO>) =
        dogDTOList.map { dogDTO ->
            fromDogDTOToDogDomain(dogDTO)

        }

    private fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        with(dogDTO) {
            return Dog(
                id,
                index,
                name,
                type,
                heightFemale,
                heightMale,
                imageUrl,
                lifeExpectancy,
                temperament,
                weightFemale,
                weightMale
            )
        }
    }
}