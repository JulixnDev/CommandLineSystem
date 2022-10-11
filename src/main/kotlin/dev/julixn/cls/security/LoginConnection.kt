package dev.julixn.cls.security

import java.util.*

class LoginConnection(val url: String) {

    companion object {
        val default: LoginConnection = LoginConnection("")
    }

    fun connect(user: String, password: String = ""): UUID? {
        // TODO integrate LoginFeature
        return UUID.randomUUID()
    }

}