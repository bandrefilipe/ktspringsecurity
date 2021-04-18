package io.bandrefilipe.ktspringsecurity

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

    @GetMapping
    fun greet(): String = "<h1>Hello, World!</h1>"
}
