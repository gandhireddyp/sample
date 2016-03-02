package com.sample.controller

import com.sample.domain.Product
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional
class ProductController {

    def productService

    def index() {
        render(productService.findAll() as JSON)
    }

    def create() {
        Product product = new Product(JSON.parse(request))
        render(productService.createProduct(product))
    }

    def update() {
        Product product = new Product(JSON.parse(request))
        def updatedProduct = productService.updateProduct(product, params.productId as long)
        if (updatedProduct) {
            render(updatedProduct as JSON)
        } else {
            render status: NOT_FOUND
        }
    }

    def delete() {
        productService.deleteProduct(params.productId as long)
        render status: OK
    }

    def show() {
        def product = productService.fetchProduct(params.productId as long)
        if (product) {
            render(product as JSON)
        } else {
            render status: NOT_FOUND
        }

    }


}
