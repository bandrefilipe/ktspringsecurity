package io.bandrefilipe.ktspringsecurity.constants

private const val PREFIX: String = "ROLE_"

const val AUTHORITY_USER: String = "$PREFIX$ROLE_USER"
const val AUTHORITY_ADMIN: String = "$PREFIX$ROLE_ADMIN"
const val AUTHORITY_USER_ONLY: String = "$PREFIX$ROLE_USER_ONLY"
