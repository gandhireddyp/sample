package com.sample.service

import com.sample.domain.OrderStatus
import com.sample.domain.Orders
import grails.transaction.Transactional

@Transactional
class OrdersService {

    def createOrder(Orders order) {
        return order.save(failOnError: true)
    }

    def findAll() {
        return Orders.list();
    }

    def fetchOrder(long orderId) {
        return Orders.get(orderId)
    }

    /**
     * Updates only the status of the order
     *
     * @param order
     * @param orderId
     * @return
     */
    def updateOrder(Orders order, long orderId) {
        def fetchedOrder = Orders.get(orderId)
        if (fetchedOrder) {
             fetchedOrder.setStatus(order.status)
            return fetchedOrder.save(flush: true)
        }
        return null
    }
}
