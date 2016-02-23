package com.sample.domain

enum OrderStatus {

    PLACED("PLACED"), DISPATCHED("DISPATCHED"), DELIVERED("DELIVERED")

    final String value

    OrderStatus(String value) {
        this.value = value
    }

    static list() {
        [PLACED, DISPATCHED, DELIVERED]
    }

    static OrderStatus valueOfName(String name){
        values().find{ it.name == name}
    }

}