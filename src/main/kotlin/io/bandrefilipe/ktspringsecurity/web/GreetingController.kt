package io.bandrefilipe.ktspringsecurity.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GreetingController {

    @GetMapping
    fun greet(): String = "greet"

    @GetMapping(path = ["/user"])
    fun greetUser(): String = "greet-user"

    @GetMapping(path = ["/user-only"])
    fun greetUserOnly(): String = "greet-user-only"

    @GetMapping(path = ["/admin"])
    fun greetAdmin(): String = "greet-admin"
}
