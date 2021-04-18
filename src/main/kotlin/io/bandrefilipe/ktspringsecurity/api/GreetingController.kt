package io.bandrefilipe.ktspringsecurity.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

    @GetMapping
    fun greet(): String = "<h1>Hello, World!</h1>"

    @GetMapping(path = ["/user"])
    fun greetUser(): String = "<h1>Hello, User!</h1>"

    @GetMapping(path = ["/admin"])
    fun greetAdmin(): String = "<h1>Hello, Admin!</h1>"
}
