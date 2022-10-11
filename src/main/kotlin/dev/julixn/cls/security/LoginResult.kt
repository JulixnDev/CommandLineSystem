package dev.julixn.cls.security

import java.util.UUID

data class LoginResult(val accept: Boolean, val connection: LoginConnection, val sessionId: UUID?)