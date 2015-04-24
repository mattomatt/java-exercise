import com.bosejam.auth.Role

class BootStrap {

    def init = { servletContext ->
        new Role(authority:'ROLE_USER').save( failOnError:true)
        new Role(authority:'ROLE_TRANSLATOR').save( failOnError:true)
        new Role(authority:'ROLE_PRODUCER').save( failOnError:true)
        new Role(authority:'ROLE_FACEBOOK').save( failOnError:true)
    }
    def destroy = {
    }
}
