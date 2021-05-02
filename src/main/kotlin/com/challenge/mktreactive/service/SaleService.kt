package com.example.marketplace.service

import com.challenge.mktreactive.dto.SaleDTO
import com.challenge.mktreactive.entity.Sale
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.SaleRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SaleService(
    val productService: ProductService,
    val userService: UserService,
    val saleRepository: SaleRepository,
    val buyerService: BuyerService,
) {
    fun purchaseProduct(saleDTO: SaleDTO): Sale {
        val product = productService.findById(saleDTO.productId);
        var buyer = buyerService.findByUserId(saleDTO.userId)
        if (buyer.id == null){
            val user = userService.findById(saleDTO.userId);
            buyer = buyerService.createBuyer(user)
        }

        return saleRepository.save(
            Sale(
                buyer = buyer,
                product = product
            )
        )
    }

    fun findAll(): List<Sale> =
        saleRepository.findAll()

    fun findById(id: String): Sale =
        saleRepository.findById(id)
            .orElseThrow { NotFoundException("Sale with id $id not found") }

    fun findByProductAndClient(productId: String, buyerId: String): Sale? =
        saleRepository.findByProductAndClient(productId, buyerId, Sort.by(Sort.Direction.ASC, "id"))

    fun deleteById(id: String) {
        val sale = findById(id)
        saleRepository.delete(sale)
    }

}
