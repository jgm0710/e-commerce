package com.example.ecommerce.adepter.out.persistence.product

import com.example.ecommerce.adepter.out.persistence.product.ProductCategoryEntity.Companion.toEntity
import com.example.ecommerce.adepter.out.persistence.product.QProductCategoryEntity.productCategoryEntity
import com.example.ecommerce.application.port.product.out.DeleteProductCategoryPort
import com.example.ecommerce.application.port.product.out.ProductCategoryQueryPort
import com.example.ecommerce.application.port.product.out.SaveProductCategoryPort
import com.example.ecommerce.domain.product.ProductCategory
import com.example.ecommerce.domain.product.ProductCategoryId
import com.example.ecommerce.global.pagination.PageQuery
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ProductCategoryPersistenceAdapter(
    private val productCategoryJpaRepository: ProductCategoryJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : SaveProductCategoryPort, DeleteProductCategoryPort, ProductCategoryQueryPort {

    override fun save(productCategory: ProductCategory): ProductCategory {
        return productCategoryJpaRepository.save(productCategory.toEntity()).toDomain()
    }

    override fun deleteById(productCategoryId: ProductCategoryId) {
        productCategoryJpaRepository.deleteById(productCategoryId.value)
    }

    override fun findById(productCategoryId: ProductCategoryId): ProductCategory? {
        return productCategoryJpaRepository.findByIdOrNull(productCategoryId.value)?.toDomain()
    }

    override fun findAll(): List<ProductCategory> {
        return productCategoryJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findAllBy(
        productCategoryId: ProductCategoryId?,
        name: String?,
        code: String?,
        pageQuery: PageQuery
    ): List<ProductCategory> {
        return jpaQueryFactory
            .select(productCategoryEntity)
            .from(productCategoryEntity)
            .where(
                eqId(productCategoryId),
                likeName(name),
                eqCode(code),
            )
            .offset(pageQuery.offset)
            .limit(pageQuery.limit)
            .fetch()
            .map { it.toDomain() }
    }

    private fun eqCode(code: String?): BooleanExpression? = code?.run { productCategoryEntity.code.eq(this) }

    private fun likeName(name: String?): BooleanExpression? = name?.run {  productCategoryEntity.name.contains(this)}

    private fun eqId(productCategoryId: ProductCategoryId?): BooleanExpression? =
        productCategoryId?.run { productCategoryEntity.id.eq(this.value) }

    override fun existsByName(name: String): Boolean {
        return productCategoryJpaRepository.existsByName(name)
    }

    override fun existsByCode(code: String): Boolean {
        return productCategoryJpaRepository.existsByCode(code)
    }
}