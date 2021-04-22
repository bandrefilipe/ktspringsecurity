package io.bandrefilipe.ktspringsecurity.configuration

import io.bandrefilipe.ktspringsecurity.adapters.configureAuthorization
import io.bandrefilipe.ktspringsecurity.constants.AUTHORITY_ADMIN
import io.bandrefilipe.ktspringsecurity.constants.AUTHORITY_USER
import io.bandrefilipe.ktspringsecurity.constants.AUTHORITY_USER_ONLY
import io.bandrefilipe.ktspringsecurity.constants.JPA_BASED_AUTHENTICATION
import io.bandrefilipe.ktspringsecurity.models.User
import io.bandrefilipe.ktspringsecurity.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@Profile(JPA_BASED_AUTHENTICATION)
class JpaBasedAuthenticationConfig(
    @Autowired private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

    init {
        log.debug { "Creating bean ${JpaBasedAuthenticationConfig::class.simpleName}" }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        log.debug { "Configuring JPA-based authentication" }
        auth.userDetailsService(userDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        log.debug { "Delegating authorization configuration…" }
        configureAuthorization(http)
    }

    @Bean
    fun noOpPasswordEncoder(): PasswordEncoder {
        val bean = NoOpPasswordEncoder.getInstance()
        log.debug { "Creating bean ${bean::class.simpleName}" }
        return bean
    }
}

@Configuration
@Profile(JPA_BASED_AUTHENTICATION)
class DummyData(
    @Autowired private val userRepository: UserRepository
) : CommandLineRunner {

    init {
        log.debug { "Creating bean ${DummyData::class.simpleName}" }
    }

    override fun run(vararg args: String?) {
        val dummyData = listOf(
            User(
                username = "user",
                password = "user",
                active = true,
                roles = "$AUTHORITY_USER,$AUTHORITY_USER_ONLY"
            ),
            User(
                username = "admin",
                password = "admin",
                active = true,
                roles = "$AUTHORITY_ADMIN,$AUTHORITY_USER"
            ),
            User(
                username = "inactive",
                password = "inactive",
                active = false,
                roles = AUTHORITY_ADMIN
            )
        )
        log.debug { "Saving users: $dummyData" }
        userRepository.saveAll(dummyData)
    }
}

private val log = KotlinLogging.logger {}
