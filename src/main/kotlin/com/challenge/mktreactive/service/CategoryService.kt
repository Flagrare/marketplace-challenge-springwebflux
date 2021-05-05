package com.challenge.mktreactive.service

import com.challenge.mktreactive.dto.CategoryDTO
import com.challenge.mktreactive.entity.Category
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.CategoryRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryService(val categoryRepository: CategoryRepository) {

    fun findAll(): Flux<Category> =
        categoryRepository.findAll()

    fun findById(id: String): Mono<Category> =
        categoryRepository.findById(id)
            .switchIfEmpty(Mono.error { NotFoundException("Category with id $id not found") })


    fun findByName(name: String): Mono<Category> =
        categoryRepository.findByName(name)

    fun createCategory(category: CategoryDTO): Mono<Category> =
        categoryRepository.save(
            Category(
                name = category.name,
                desc = category.desc,
            )
        )

    fun updateCategory(id: String, categoryDTO: CategoryDTO): Mono<Category> = findById(id)
        .flatMap { category ->
            category.name = categoryDTO.name
            category.desc = categoryDTO.desc

            categoryRepository.save(category)
        }


    fun deleteById(id: String): Mono<Void> = findById(id)
        .flatMap { category ->
            categoryRepository.delete(category)
        }
}
