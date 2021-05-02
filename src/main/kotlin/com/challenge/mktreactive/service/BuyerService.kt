package com.example.marketplace.service

import com.challenge.mktreactive.dto.BuyerDTO
import com.challenge.mktreactive.entity.Buyer
import com.challenge.mktreactive.entity.User
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.BuyerRepository
import org.springframework.stereotype.Service

@Service
class BuyerService(val buyerRepository: BuyerRepository) {

    fun findById(id: String): Buyer =
        buyerRepository.findById(id)
            .orElseThrow { NotFoundException("Buyer with id $id not found") }

    fun findByUserId(userId: String): Buyer =
        buyerRepository.findByUserId(userId)

    fun createBuyer(user: User): Buyer =
        buyerRepository.save(
            Buyer(
                user = user
            )
        )

    fun updateBuyer(id: String, buyerDTO: BuyerDTO): Buyer {
        val buyerToUpdate = findById(id)

        return buyerRepository.save(
            buyerToUpdate.apply {
                user.name = buyerDTO.user.name
            }
        )
    }


    fun deleteById(id: String) {
        val buyer = findById(id)
        buyerRepository.delete(buyer)
    }
}