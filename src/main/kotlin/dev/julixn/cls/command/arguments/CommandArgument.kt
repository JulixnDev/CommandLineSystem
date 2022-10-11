package dev.julixn.cls.command.arguments

import dev.julixn.cls.command.options.OptionType

annotation class CommandArgument(
    val name: String,
    val type: OptionType,
    val optional: Boolean = false,
    val description: String = "No description"
)