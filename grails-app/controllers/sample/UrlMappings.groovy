package sample

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

        "/"(view: "/index")
        "500"(view: '/error')

        "/api/users"(controller: "user") {
            action = [GET: "index", POST: "create"]
        }

        "/api/users/$userId"(controller: "user") {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
        }

        "/api/products"(controller: "product"){
            action = [GET: "index", POST: "create"]
        }

        "/api/products/$productId"(controller: "product") {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
        }

        "/api/orders"(controller: "order"){
            action = [GET: "index", POST: "create"]
        }

        "/api/orders/$orderId"(controller: "order"){
            action = [GET: "show", PUT: "update"]
        }
    }
}
