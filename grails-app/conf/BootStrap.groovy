import com.sample.domain.OrderStatus
import com.sample.domain.Role
import com.sample.domain.User
import com.sample.domain.UserRole

class BootStrap {

    def init = { servletContext ->
        if(User.count() == 0){
            createSampleData()
        }
    }
    def destroy = {
    }

    private createSampleData(){
        println("Creating Sample data - START")

        // Create predefined user roles
        def roleUser = new Role(authority: "USER").save(failOnError: true)
        def roleAdmin = new Role(authority: "ADMIN").save(failOnError: true)

        //

        //Create Orders statuses
        //def statusPlaced = OrderStatus.valueOf("PLACED").save(failOnError: true)
        //def statusDispatched = OrderStatus.valueOf("DISPATCHED").save(failOnError: true)
       // def statusDelivered = OrderStatus.valueOf("DELIVERED").save(failOnError: true)

        //Create Admin user
        def adminUser = new User(username:"admin", email: "admin@admin.com", password: "password").save(failOnError: true)

        new UserRole(user: adminUser, role: roleAdmin).save(failOnError: true)

        println("Creating Sample data - COMPLETE")
    }
}
