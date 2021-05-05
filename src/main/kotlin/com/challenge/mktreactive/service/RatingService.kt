package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.RatingDTO
import com.challenge.mktreactive.entity.Rating
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.BuyerRepository
import com.challenge.mktreactive.repository.RatingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RatingService() {

    @Autowired
    protected lateinit var ratingRepository: RatingRepository

    @Autowired
    protected lateinit var buyerRepository: BuyerRepository

    @Autowired
    protected lateinit var saleService: SaleService

    @Autowired
    protected lateinit var productService: ProductService

    fun rateProduct(ratingDTO: RatingDTO): Mono<Rating> {
        val product = productService.findById(ratingDTO.productId)
        val buyer = buyerRepository.findByUserId(ratingDTO.userId)

        if ((product.id != null) && (buyer.id != null)) {
            val sale = saleService.findByProductAndClient(product.id, buyer.id)

            if (sale == null) {
                throw (NotFoundException("Buyer or Product not found!"))
            } else {
                return ratingRepository.save(
                    Rating(
                        rate = ratingDTO.rate,
                        sale = sale,
                    )
                )
            }
        } else {
            throw (NotFoundException("Buyer or Product not found!"))
        }

    }

    fun findAll(): Flux<Rating> =
        ratingRepository.findAll()

    fun findById(id: String): Mono<Rating> =
        ratingRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Rating with $id not found")))

    fun deleteById(id: String): Mono<Void> =
        findById(id)
            .flatMap { rating ->
                ratingRepository.delete(rating)
            }
}