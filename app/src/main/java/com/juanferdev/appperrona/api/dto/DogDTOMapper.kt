package com.juanferdev.appperrona.api.dto

import com.juanferdev.appperrona.models.Dog

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
                heightFemale.toString(),
                heightMale.toString(),
                imageUrl,
                lifeExpectancy,
                temperament,
                weightFemale,
                weightMale
            )
        }
    }
}