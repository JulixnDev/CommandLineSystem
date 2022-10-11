package dev.julixn.cls.security

import dev.julixn.cls.logging.Logger

class RemoteLogin {

    companion object {
        private fun login(user: String, password: String = "", connection: LoginConnection? = null): LoginResult {
            val con = connection ?: LoginConnection.default
            val connectionId = con.connect(user, password)
            return LoginResult(accept = connectionId != null, connection = con, sessionId = connectionId)
        }

        fun inject(arguments: List<String>): LoginSession? {
            val credentials = if (arguments.size == 1) {
                val value = arguments[0]
                if (value.contains(":"))
                    arrayOf(value.split(":")[0], value.split(":")[1])
                else
                    arrayOf(value)
            } else
                arguments.toTypedArray()

            val loginResult = when (credentials.size) {
                0 -> null
                1 -> login(credentials[0])
                2 -> login(credentials[0], credentials[1])
                else -> login(credentials[0], credentials[1], LoginConnection(credentials[2]))
            }

            if (loginResult == null) {
                Logger.error("You have to pass login information!")
                return null
            }

            if (!loginResult.accept) {
                Logger.error("The given credentials or the AuthenticationServer are invalid!")
                return null
            }

            println(loginResult.sessionId.toString())
            return LoginSession(loginResult.sessionId!!, loginResult.connection)
        }
    }

}