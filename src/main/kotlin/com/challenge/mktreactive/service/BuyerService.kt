package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.BuyerDTO
import com.challenge.mktreactive.entity.Buyer
import com.challenge.mktreactive.entity.User
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.BuyerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BuyerService() {

    @Autowired
    protected lateinit var buyerRepository: BuyerRepository

    fun findById(id: String): Mono<Buyer> =
        buyerRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Buyer with id $id not found")))

    fun findByUserId(userId: String): Mono<Buyer> =
        buyerRepository.findByUserId(userId)

    fun createBuyer(user: User): Mono<Buyer> =
        buyerRepository.save(
            Buyer(
                user = user
            )
        )

    fun updateBuyer(id: String, buyerDTO: BuyerDTO): Mono<Buyer> =
        findById(id)
            .flatMap { buyer ->
                buyer.user.name = buyerDTO.user.name
                buyerRepository.save(buyer)
            }


    fun deleteById(id: String): Mono<Void> =
        findById(id)
            .flatMap { buyer ->
                buyerRepository.delete(buyer)
            }

}