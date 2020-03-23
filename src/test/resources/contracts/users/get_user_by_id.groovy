package contracts.users

import org.springframework.cloud.contract.spec.Contract
[

Contract.make {
    description "should return 'Not Found Error' by unknown id"

    request {
        url "/users/2"
        method GET()
    }

    response {
        status NOT_FOUND()
    }
},

Contract.make {
    description "should return user by id"

    request {
        url "/users/1"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body ("""
                {
                    "id": "1",
                    "name": "name",
                    "email": "name@mail.com"
                }
        """)
    }
}

]