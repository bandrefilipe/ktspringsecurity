package io.bandrefilipe.ktspringsecurity.models

import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    val username: String? = null,

    val password: String? = null,

    val active: Boolean = false,

    val roles: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int = username?.hashCode() ?: 0

    override fun toString(): String {
        return "User(id=$id, username=$username, password=$password, active=$active, roles=$roles)"
    }
}
