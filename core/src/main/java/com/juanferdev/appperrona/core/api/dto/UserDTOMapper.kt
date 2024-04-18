package com.juanferdev.appperrona.core.api.dto

import com.juanferdev.appperrona.core.models.User


class UserDTOMapper {

    fun fromUserDTOToUserDomain(userDTO: UserDTO): User =
        User(userDTO.id, userDTO.email, userDTO.authenticationToken)

}