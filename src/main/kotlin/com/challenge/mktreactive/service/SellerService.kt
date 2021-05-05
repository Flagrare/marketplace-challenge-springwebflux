package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.SellerDTO
import com.challenge.mktreactive.entity.Seller
import com.challenge.mktreactive.entity.User
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.SellerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SellerService() {

    @Autowired
    protected lateinit var sellerRepository: SellerRepository


    fun findById(id: String): Mono<Seller> =
        sellerRepository.findById(id)
            .switchIfEmpty(Mono.error { NotFoundException("Seller with id $id not found") })

    fun findByUserId(userId: String): Mono<Seller> =
        sellerRepository.findByUserId(userId)

    fun createSeller(user: User): Mono<Seller> =
        sellerRepository.save(
            Seller(
                user = user
            )
        )

    fun updateSeller(id: String, sellerDTO: SellerDTO): Mono<Seller> = findById(id)
        .flatMap { seller ->
            seller.user.name = sellerDTO.user.name
            sellerRepository.save(seller)
        }


    fun deleteById(id: String): Mono<Void> =
        findById(id)
            .flatMap { seller ->
                sellerRepository.delete(seller)
            }
}
