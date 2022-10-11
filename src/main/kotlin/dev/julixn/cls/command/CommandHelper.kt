package dev.julixn.cls.command

import dev.julixn.cls.command.arguments.CommandArgument
import dev.julixn.cls.command.options.CommandOption

annotation class CommandHelper(
    val name: String,
    val description: String = "No description",
    val aliases: Array<String> = [],
    val arguments: Array<CommandArgument> = [],
    val options: Array<CommandOption> = []
)
