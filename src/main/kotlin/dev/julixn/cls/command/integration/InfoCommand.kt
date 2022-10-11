package dev.julixn.cls.command.integration

import dev.julixn.cls.CommandLineSystem
import dev.julixn.cls.command.CloudCommand
import dev.julixn.cls.command.CommandHelper
import dev.julixn.cls.command.arguments.CommandArgument
import dev.julixn.cls.command.options.OptionType

@CommandHelper(
    name = "info",
    arguments = [
        CommandArgument(name = "command", type = OptionType.TEXT)
    ]
)
class InfoCommand : CloudCommand() {
    override fun run(arguments: HashMap<String, Any>) {
        val command = CommandLineSystem.get().commandManager.getCommand(arguments["command"].toString())
            ?: return println("The given command was not found!")

        command.printInfo()
    }
}