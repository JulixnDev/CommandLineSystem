package dev.julixn.cls.command.options

annotation class CommandOption(
    val name: String,
    val type: OptionType,
    val description: String = "No description",
    val multiple: Boolean = false,
    val childs: Array<CommandOption> = []
)