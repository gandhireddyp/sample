package com.sample.controller

import com.sample.domain.OrderStatus
import com.sample.domain.Orders
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.NOT_FOUND

@Transactional
class OrdersController {

    def ordersService

    def index() {
        render(ordersService.findAll() as JSON)
    }

    def create() {
        Orders order = new Orders(JSON.parse(request))
        order.status = OrderStatus.PLACED // new Order
        render(ordersService.createOrder(order))
    }

    def show() {
        long orderId = params.orderId as long
        render(ordersService.fetchOrder(orderId) as JSON)
    }

    def update() {
        Orders order = new Orders(JSON.parse(request))
        def updatedOrder = ordersService.updateOrder(order, params.orderId as long)
        if (updatedOrder) {
            render updatedOrder as JSON
        }

        render status: NOT_FOUND
    }

}
