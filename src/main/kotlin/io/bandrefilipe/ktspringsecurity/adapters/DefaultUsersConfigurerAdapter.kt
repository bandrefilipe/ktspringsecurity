package io.bandrefilipe.ktspringsecurity.adapters

import io.bandrefilipe.ktspringsecurity.constants.ROLE_ADMIN
import io.bandrefilipe.ktspringsecurity.constants.ROLE_USER
import mu.KotlinLogging
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer

fun configureDefaultUsers(builder: UserDetailsManagerConfigurer<*, *>) {
    log.debug { "Configuring default users" }
    builder
        .withUser("admin")
        .password("admin")
        .roles(ROLE_ADMIN, ROLE_USER)
        .and()
        .withUser("user")
        .password("user")
        .roles(ROLE_USER)
}

private val log = KotlinLogging.logger {}
