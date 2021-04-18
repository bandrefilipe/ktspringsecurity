package io.bandrefilipe.ktspringsecurity

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@Profile(IN_MEMORY_AUTHENTICATION)
class InMemoryAuthenticationConfig : WebSecurityConfigurerAdapter() {

    init {
        log.debug { "Creating bean ${InMemoryAuthenticationConfig::class.simpleName}" }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        log.debug { "Configuring in-memory authentication" }
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("admin")
            .roles(ADMIN, USER)
            .and()
            .withUser("user")
            .password("user")
            .roles(USER)
    }

    override fun configure(http: HttpSecurity) {
        log.debug { "Configuring authorization" }
        http.authorizeRequests()
            .antMatchers("/", "static/**").permitAll()
            .antMatchers("/user").hasAnyRole(ADMIN, USER)
            .antMatchers("/admin").hasRole(ADMIN)
            .and().formLogin()
    }

    @Bean
    fun noOpPasswordEncoder(): PasswordEncoder {
        val bean = NoOpPasswordEncoder.getInstance()
        log.debug { "Creating bean ${bean::class.simpleName}" }
        return bean
    }
}

private val log = KotlinLogging.logger {}
