import com.sample.domain.Product
import com.sample.domain.User
import grails.rest.render.json.JsonRenderer

// Place your Spring DSL code here
beans = {

    userRenderer(JsonRenderer, User) {
        excludes = ["orders", "password"]
    }

    productRenderer(JsonRenderer, Product) {
        excludes = ["orders"]
    }
}
