package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Sale
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SaleRepository : ReactiveMongoRepository<Sale, String> {
    @Query("{ product: { productId: ?0 } }")
    fun findByProductId(productId: String, sort: Sort): Flux<Sale>

    @Query("""{ 
            product: { productId: ?0}
            buyer: { 
                User: { id: ?1 }
             }
        }""")
    fun findByProductAndClient(productId: String, buyerId: String, sort: Sort): Mono<Sale>


}
