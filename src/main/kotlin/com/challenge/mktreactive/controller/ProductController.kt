package com.challenge.mktreactive.controller

import com.example.marketplace.service.ProductService
import com.challenge.mktreactive.data.ProductVO
import com.challenge.mktreactive.dto.ProductDTO
import com.challenge.mktreactive.entity.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("products")
internal class ProductController(val productService: ProductService) {

    val logger: Logger = Logger.getLogger(ProductController::class.java.canonicalName)

    @PostMapping
    fun createProduct(@RequestBody productDTO: ProductDTO): ResponseEntity<ProductVO> {
        val createdProduct = productService.createProduct(productDTO)
        logger.info("product created $createdProduct")
        return ResponseEntity
            .ok(
                ProductVO.fromEntity(createdProduct)
            )
    }

    @GetMapping
    fun getAllProduct(): ResponseEntity<List<ProductVO>> {
        val products = productService.findAll()
        return ResponseEntity
            .ok(
                products.map { ProductVO.fromEntity(it) }
            )
    }

    @GetMapping("/{id}")
    fun getProductDetail(@PathVariable("id") id: String): ResponseEntity<ProductVO> {

        val product = productService.findById(id)
        logger.info("product $product")

        return ResponseEntity.ok(ProductVO.fromEntity(product))

    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: String,
        @RequestBody request: ProductDTO
    ): ResponseEntity<ProductVO> {
        val updatedCompany = productService.updateProduct(id, request)

        return ResponseEntity
            .ok(
                ProductVO.fromEntity(updatedCompany)
            )
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Void> {
        productService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/forceProductRating")
    fun forceProductRating(): ResponseEntity<MutableList<Product>> {
        val productResponse = productService.rateProduct()
        return ResponseEntity.ok(productResponse)
    }

}