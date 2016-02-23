package com.sample.domain

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(OrderStatus)
class OrderStatusSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test new OrderStatus with null value"() {
        when:
        def orderStatus = new OrderStatus(status: null)
        orderStatus.validate()

        then:
        orderStatus.hasErrors()

        orderStatus.errors.getFieldError("status").rejectedValue == null
        orderStatus.errors.getFieldError("status").code == "nullable"

    }


    void "test new OrderStatus with valid value"() {
        when:
        def orderStatus = new OrderStatus(status: OrderStatus.Status.DELIVERED)
        orderStatus.validate()

        then:
        orderStatus.hasErrors() == false
        orderStatus.status == OrderStatus.Status.DELIVERED
    }
}
