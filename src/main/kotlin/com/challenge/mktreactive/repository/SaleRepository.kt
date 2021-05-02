package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Sale
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SaleRepository : MongoRepository<Sale, String> {
    @Query("{ product: { productId: ?0 } }")
    fun findByProductId(productId: String, sort: Sort): MutableList<Sale>

    @Query("""{ 
            product: { productId: ?0}
            buyer: { 
                User: { id: ?1 }
             }
        }""")
    fun findByProductAndClient(productId: String, buyerId: String, sort: Sort): Sale


}
