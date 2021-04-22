package io.bandrefilipe.ktspringsecurity.adapters

import io.bandrefilipe.ktspringsecurity.constants.ROLE_ADMIN
import io.bandrefilipe.ktspringsecurity.constants.ROLE_USER
import io.bandrefilipe.ktspringsecurity.constants.ROLE_USER_ONLY
import mu.KotlinLogging
import org.springframework.security.config.annotation.web.builders.HttpSecurity

fun configureAuthorization(http: HttpSecurity) {
    log.debug { "Configuring authorization" }
    http.authorizeRequests()
        .antMatchers("/", "/h2-console/**", "static/**").permitAll()
        .antMatchers("/user").hasAnyRole(ROLE_ADMIN, ROLE_USER, ROLE_USER_ONLY)
        .antMatchers("/admin").hasRole(ROLE_ADMIN)
        .antMatchers("/user-only").hasRole(ROLE_USER_ONLY)
        .and().formLogin()
}

private val log = KotlinLogging.logger {}
