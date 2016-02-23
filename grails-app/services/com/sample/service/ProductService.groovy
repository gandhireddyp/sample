package com.sample.service

import com.sample.domain.Product
import grails.transaction.Transactional

@Transactional
class ProductService {

    def findAll() {
        return Product.list()
    }

    def createProduct(Product product) {
        return product.save(failOnError: true);
    }

    def updateProduct(Product product, long productId) {
        def fetchedProduct = Product.get(productId)
        if (fetchedProduct) {
            fetchedProduct.name = product.name
            fetchedProduct.description = product.description
            fetchedProduct.price = product.price
            return fetchedProduct.save(flush: true)
        }
        return null
    }

    def deleteProduct(long productId) {
        Product fetchedProduct = Product.get(productId);
        if (fetchedProduct) {
            fetchedProduct.delete()
        }
    }

    def fetchProduct(long productId) {
        return Product.get(productId);
    }

}
