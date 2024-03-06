package com.juanferdev.appperrona.api.dto

import com.juanferdev.appperrona.parcelables.User

class UserDTOMapper {

    fun fromUserDTOToUserDomain(userDTO: UserDTO): User =
        User(userDTO.id, userDTO.email, userDTO.authenticationToken)

}