package com.juanferdev.appperrona.core.api.dto

import com.juanferdev.appperrona.core.models.Dog


class DogDTOMapper {

    fun fromDogDTOListToDogDomainList(dogDTOList: List<DogDTO>) =
        dogDTOList.map { dogDTO ->
            fromDogDTOToDogDomain(dogDTO)

        }

    fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
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