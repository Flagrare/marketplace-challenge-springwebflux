package com.example.marketplace.service

import com.challenge.mktreactive.dto.CategoryDTO
import com.challenge.mktreactive.entity.Category
import com.challenge.mktreactive.exception.NotFoundException
import com.challenge.mktreactive.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(val categoryRepository: CategoryRepository) {

    fun findAll(): List<Category> =
        categoryRepository.findAll()

    fun findById(id: String): Category =
        categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Category with id $id not found") }


    fun findByName(name: String): Category =
        categoryRepository.findByName(name)

    fun createCategory(category: CategoryDTO): Category =
        categoryRepository.save(
            Category(
                name = category.name,
                desc = category.desc,
            )
        )

    fun updateCategory(id: String, categoryDTO: CategoryDTO): Category {
        val categoryToUpdate = findById(id)

        return categoryRepository.save(
            categoryToUpdate.apply {
                name = categoryDTO.name
                desc = categoryDTO.desc
            }
        )
    }


    fun deleteById(id: String) {
        val category = findById(id)

        categoryRepository.delete(category)
    }
}