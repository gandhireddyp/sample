import com.sample.domain.Order
import com.sample.domain.Product
import com.sample.domain.User
import grails.rest.render.json.JsonRenderer

// Place your Spring DSL code here
beans = {
    userRenderer(JsonRenderer, User) {
        excludes = ["class", "orders", "password"]
    }

    productRenderer(JsonRenderer, Product) {
        excludes = ["class", "orders"]
    }

}
