package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.SaleDTO
import com.challenge.mktreactive.entity.Sale
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.SaleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SaleService() {

    @Autowired
    protected lateinit var productService: ProductService

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var saleRepository: SaleRepository

    @Autowired
    protected lateinit var buyerService: BuyerService


    fun purchaseProduct(saleDTO: SaleDTO): Mono<Sale> =
        productService.findById(saleDTO.productId)
            .flatMap { product ->
                buyerService.findByUserId(saleDTO.userId)
                    .flatMap { buyer ->
                        if (buyer == null) {
                            userService.findById(saleDTO.userId)
                                .flatMap { user ->
                                    buyerService.createBuyer(user)
                                }
                        }

                        Sale(
                            buyer = buyer,
                            product = product
                        ).let { sale ->
                            saleRepository.save(sale)
                        }
                    }

            }


    fun findAll(): Flux<Sale> =
        saleRepository.findAll()

    fun findById(id: String): Mono<Sale> =
        saleRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Sale with id $id not found")))

    fun findByProductAndClient(productId: String, buyerId: String): Mono<Sale>? =
        saleRepository.findByProductAndClient(productId, buyerId, Sort.by(Sort.Direction.ASC, "id"))

    fun deleteById(id: String): Mono<Void> =
        findById(id)
            .flatMap { sale ->
                saleRepository.delete(sale)
            }

}
