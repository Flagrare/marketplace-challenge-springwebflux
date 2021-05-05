package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.UserDTO
import com.challenge.mktreactive.entity.User
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService() {

    @Autowired
    protected lateinit var userRepository: UserRepository

    fun findById(id: String): Mono<User> =
        userRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException(("User with id $id not found"))))

    fun createSeller(userDTO: UserDTO): Mono<User> =
        userRepository.save(
            User(
                name = userDTO.name,
            )
        )

    fun updateSeller(id: String, userDTO: UserDTO): Mono<User> = findById(id)
        .flatMap { user ->
            user.name = userDTO.name
            userRepository.save(user)
        }

    fun deleteById(id: String): Mono<Void> = findById(id)
        .flatMap { user ->
            userRepository.delete(user)

        }

}