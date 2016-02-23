package com.sample.controller

import com.sample.domain.Product
import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional
class ProductController {

    def productService

    def index(){
        render(productService.findAll() as JSON)
    }

    def create(){
        Product product = new Product(JSON.parse(request))
        render(productService.createProduct(product))
    }

    def update(){
        Product product = new Product(JSON.parse(request))
        def updatedProduct = productService.updateProduct(product, params.productId as long)
        if(updatedProduct){
            render(updatedProduct as JSON)
        }
        render status: NOT_FOUND
    }

    def delete() {
        productService.deleteProduct(params.productId as long)
        render status: OK
    }

    def show() {
        render(productService.fetchProduct(params.productId as long) as JSON)
    }


}
