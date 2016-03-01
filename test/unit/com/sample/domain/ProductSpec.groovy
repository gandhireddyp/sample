package com.sample.domain

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Product)
class ProductSpec extends Specification {

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



    //test create product with invalid values
    //test create product with values

    //test update with non-existing id
    // test update with existing id

    //test delete with non-existing id
    //test delete with existing id
}
