package com.sample.domain

class Product {

    String name
    String description
    float price
    static hasMany = [orders : Order]

    static constraints = {
        name(blank: false, maxSize: 50, unique: true)
        description(blank: false, maxSize: 400)
        price(nullable: false)
    }

    String getDisplayString(){
        return name
    }

    static mapping = {
        orders cascade:  "all-delete-orphan"
    }
}
