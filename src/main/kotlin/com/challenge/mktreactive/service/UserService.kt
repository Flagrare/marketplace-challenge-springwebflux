package com.example.marketplace.service

import com.challenge.mktreactive.dto.UserDTO
import com.challenge.mktreactive.entity.User
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun findById(id: String): User =
        userRepository.findById(id)
            .orElseThrow { NotFoundException("User with id $id not found") }

    fun createSeller(userDTO: UserDTO): User =
        userRepository.save(
            User(
                name = userDTO.name,
            )
        )

    fun updateSeller(id: String, userDTO: UserDTO): User {
        val UserToUpdate = findById(id)

        return userRepository.save(
            UserToUpdate.apply {
                name = userDTO.name
            }
        )
    }


    fun deleteById(id: String) {
        val user = findById(id)

        userRepository.delete(user)
    }
}