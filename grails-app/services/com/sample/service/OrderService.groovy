package com.sample.service

import com.sample.domain.OrderStatus
import com.sample.domain.Order
import grails.transaction.Transactional

@Transactional
class OrderService {

    def createOrder(Order order) {
        return order.save(failOnError: true)
    }

    def findAll() {
        return Order.list();
    }

    def fetchOrder(long orderId) {
        return Order.get(orderId)
    }

    /**
     * Updates only the status of the order
     *
     * @param order
     * @param orderId
     * @return
     */
    def updateOrder(Order order, long orderId) {
        def fetchedOrder = Order.get(orderId)
        if (fetchedOrder) {
             fetchedOrder.setStatus(order.status)
            return fetchedOrder.save(flush: true)
        }
        return null
    }

    def search(long userId, long productId, OrderStatus status){
        Order.fin
    }
}
