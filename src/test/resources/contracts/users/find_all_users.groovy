package contracts.users

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return all users"

    request {
        url "/users"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (
                id: "id",
                name: "name",
                email: "name@mail.com"
        )
    }
}