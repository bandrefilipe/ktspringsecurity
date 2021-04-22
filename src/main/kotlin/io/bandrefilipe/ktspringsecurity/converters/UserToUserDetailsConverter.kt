package io.bandrefilipe.ktspringsecurity.converters

import io.bandrefilipe.ktspringsecurity.models.User
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
private class UserToUserDetailsConverter : Converter<User, UserDetails> {

    init {
        log.debug { "Creating component ${UserToUserDetailsConverter::class.simpleName}" }
    }

    override fun convert(source: User): UserDetails {
        log.trace { "Converter.convert(source=$source)" }
        return UserDetailsImpl(source)
    }
}

private class UserDetailsImpl(user: User) : UserDetails {

    private val username: String? = user.username
    private val password: String? = user.password
    private val active: Boolean = user.active
    private val authorities: MutableList<SimpleGrantedAuthority> = user.roles
        ?.split(",")
        ?.map { SimpleGrantedAuthority(it) }
        ?.toMutableList()
        ?: mutableListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getPassword(): String = password!!

    override fun getUsername(): String = username!!

    override fun isAccountNonExpired(): Boolean = active

    override fun isAccountNonLocked(): Boolean = active

    override fun isCredentialsNonExpired(): Boolean = active

    override fun isEnabled(): Boolean = active
}

private val log = KotlinLogging.logger {}
