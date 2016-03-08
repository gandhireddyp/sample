package com.sample.domain


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class ProductIntegSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test create Product"() {
        given:
        def productName = "productName"
        def productDescription = "productDescription"

        when:
        Product product = new Product(name: productName, description: productDescription, price: 156.5f)
        def savedProduct = product.save()

        then:
        savedProduct.getId()
        savedProduct.getName() == productName
        savedProduct.getDescription() == productDescription
        savedProduct.getPrice() == 156.5f

        //Check the number of products
        Product.count == 1
    }

    void "test create Product with invalid values"() {
        given:
        def productName = null
        def productDescription = "productDescription"

        when:
        Product product = new Product(name: productName, description: productDescription, price: 156.5f)
        product.save()

        then:
        product.hasErrors()

        //Check the number of products
        Product.count == 0
    }

    //test create product with invalid values
    //test create product with values

    //test update with non-existing id
    // test update with existing id

    //test delete with non-existing id
    //test delete with existing id
}
