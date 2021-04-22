package io.bandrefilipe.ktspringsecurity.repository

import io.bandrefilipe.ktspringsecurity.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {

    fun findByUsernameIgnoreCase(username: String): User?
}
