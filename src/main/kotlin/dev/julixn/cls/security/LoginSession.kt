package dev.julixn.cls.security

import java.util.*

class LoginSession(
    val sessionId: UUID,
    private val loginConnection: LoginConnection,
    val key: UUID = UUID.randomUUID()
) {

    fun validate(): Boolean {
        val url = loginConnection.url
        val validationUrl = "$url/validate?sessionId=$sessionId"

        // TODO: send request

        return false
    }

}