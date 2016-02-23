package com.sample.controller

import com.sample.domain.OrderStatus
import com.sample.domain.Order
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.NOT_FOUND

@Transactional
class OrderController {

    def orderService

    def index() {
        render(orderService.findAll() as JSON)
    }

    def create() {
        Order order = new Order(JSON.parse(request))
        order.status = OrderStatus.PLACED // new Order
        render(orderService.createOrder(order))
    }

    def show() {
        long orderId = params.orderId as long
        render(orderService.fetchOrder(orderId) as JSON)
    }

    def update() {
        Order order = new Order(JSON.parse(request))
        def updatedOrder = orderService.updateOrder(order, params.orderId as long)
        if (updatedOrder) {
            render updatedOrder as JSON
        }

        render status: NOT_FOUND
    }

}
