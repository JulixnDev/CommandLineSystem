package dev.julixn.cls.logging

import com.bugsnag.Bugsnag
import com.bugsnag.Severity
import dev.julixn.cls.CommandLineSystem
import dev.julixn.cls.exceptions.SimpleTextException

class BugsnagStore {
    companion object {
        val bugsnag = Bugsnag("4522562c8dd3dea55a60890fff4f4407")
        var useBugsnag: Boolean = false
    }
}

object Logger {
    fun <T> info(value: T) {
        this.log(value, LogLevel.INFO)
    }

    fun <T> warning(value: T) {
        this.log(value, LogLevel.WARNING)
    }

    fun <T> error(value: T) {
        this.log(value, LogLevel.ERROR)
    }

    // Pass the value to the needed log function
    private fun <T> log(value: T, level: LogLevel) {
        when (value) {
            is Exception -> this.logException(value as Exception, level)
            is Throwable -> this.logThrowable(value as Throwable, level)
            else -> this.logString(value.toString(), level)
        }
    }

    // Log by message
    private fun logString(message: String, level: LogLevel) {
        println("${CommandLineSystem.prefix} | $level -> $message")
        if (BugsnagStore.useBugsnag)
            BugsnagStore.bugsnag.notify(SimpleTextException(message), level.severity)
    }

    // Log by exception
    private fun logException(exception: Exception, level: LogLevel) {
        println("${CommandLineSystem.prefix} | $level -> ${exception.message}")
        if (BugsnagStore.useBugsnag)
            BugsnagStore.bugsnag.notify(exception, level.severity)
    }

    // Log by throwable
    private fun logThrowable(throwable: Throwable, level: LogLevel) {
        println("${CommandLineSystem.prefix} | $level -> ${throwable.message}")
        if (BugsnagStore.useBugsnag)
            BugsnagStore.bugsnag.notify(throwable, level.severity)
    }
}

enum class LogLevel(val severity: Severity) {
    INFO(Severity.INFO), WARNING(Severity.WARNING), ERROR(Severity.ERROR)
}