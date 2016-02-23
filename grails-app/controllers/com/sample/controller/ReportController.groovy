package com.sample.controller

import com.sample.domain.OrderStatus

class ReportController {

    def searchForOrders(){
        long userId = params.userId as long
        long productId = params.productId as long
        def orderStatus = params.orderStatus as OrderStatus



    }
}
