package io.bandrefilipe.ktspringsecurity.configuration

import io.bandrefilipe.ktspringsecurity.adapters.configureAuthorization
import io.bandrefilipe.ktspringsecurity.constants.IN_MEMORY_JDBC_AUTHENTICATION
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@EnableWebSecurity
@Profile(IN_MEMORY_JDBC_AUTHENTICATION)
class InMemoryJDBCAuthenticationConfig(
    @Autowired private val dataSource: DataSource
) : WebSecurityConfigurerAdapter() {

    init {
        log.debug { "Creating bean ${InMemoryJDBCAuthenticationConfig::class.simpleName}" }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        log.debug { "Configuring JDBC-based authentication" }
        log.debug { "usersByUsernameQuery: $usersByUsernameQuery" }
        log.debug { "authoritiesByUsernameQuery: $authoritiesByUsernameQuery" }
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(usersByUsernameQuery)
            .authoritiesByUsernameQuery(authoritiesByUsernameQuery)
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

private val log = KotlinLogging.logger {}

private const val usersByUsernameQuery = """
SELECT username, password, enabled
FROM users
WHERE username = ?"""

private const val authoritiesByUsernameQuery = """
SELECT username, authority
FROM authorities
WHERE username = ?"""
