package io.bandrefilipe.ktspringsecurity.services

import io.bandrefilipe.ktspringsecurity.constants.JPA_BASED_AUTHENTICATION
import io.bandrefilipe.ktspringsecurity.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.core.convert.ConversionService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Profile(JPA_BASED_AUTHENTICATION)
private class UserDetailsServiceImpl(
    @Autowired private val conversionService: ConversionService,
    @Autowired private val userRepository: UserRepository
) : UserDetailsService {

    init {
        log.debug { "Creating bean ${UserDetailsServiceImpl::class.simpleName}" }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return username
            ?.let { userRepository.findByUsernameIgnoreCase(it) }
            ?.let { conversionService.convert(it, UserDetails::class.java) }
            ?: throw UsernameNotFoundException(username)
    }
}

private val log = KotlinLogging.logger {}
