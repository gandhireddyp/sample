package com.sample.domain

class Orders {
    int quantity
    float amount
    OrderStatus status
    static  hasOne = [product : Product, user : User]

    static  fetchMode = [product: 'eager', user: 'eager']

    static constraints = {
        quantity(null:false)
        amount(null:false)
        product(nullable: false)
        user(nullable: false)
        status(nullable: false)
    }

    static mapping = {
        table( name: 'orders')
    }

}
