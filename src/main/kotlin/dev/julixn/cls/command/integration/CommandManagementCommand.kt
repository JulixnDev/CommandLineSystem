package dev.julixn.cls.command.integration

import dev.julixn.cls.CommandLineSystem
import dev.julixn.cls.command.CloudCommand
import dev.julixn.cls.command.CommandHelper
import dev.julixn.cls.command.arguments.CommandArgument
import dev.julixn.cls.command.options.OptionType
import dev.julixn.cls.logging.Logger

@CommandHelper(
    name = "commandmanagement",
    aliases = ["commandmanager", "commandman", "cmdman", "cmdmanager"],
    arguments = [
        CommandArgument(name = "action", type = OptionType.TEXT, description = "<enable|disable|list>"),
        CommandArgument(name = "command", type = OptionType.TEXT, optional = true)
    ]
)
class CommandManagementCommand : CloudCommand() {
    override fun run(arguments: HashMap<String, Any>) {
        val enableCommand = arguments["action"].toString().lowercase() == "enable"
        val disableCommand = arguments["action"].toString().lowercase() == "disable"

        if (enableCommand || disableCommand) {
            if (!arguments.containsKey("command"))
                return Logger.error("You need to specify a command that should be enabled or disabled!")

            handleCommand(arguments["command"]!!.toString(), enableCommand)
        } else if (arguments["action"].toString().lowercase() == "list")
            listCommands()
        else
            Logger.info("No matching action found! Use <enable|disable|list>")
    }

    private fun handleCommand(name: String, enable: Boolean) {
        val success = if (enable)
            CommandLineSystem.get().commandManager.enableCommand(name)
        else
            CommandLineSystem.get().commandManager.disableCommand(name)

        if (success)
            println("${CommandLineSystem.prefix} -> The command '$name' was successfully ${if (enable) "enabled" else "disabled"}!")
        else
            println("${CommandLineSystem.prefix} -> The command '$name' can't be ${if (enable) "enabled" else "disabled"}!")
    }

    private fun listCommands() {
        println("== All Commands ==")
        println("  ID |  Name  | Enabled ")
        println(" ------------------------")
        CommandLineSystem.get().commandManager.getAllCommands().values.forEachIndexed { index, command ->
            println(" - ${index + 1} | ${command.getCommandHelper()!!.name} | ${!command.isDisabled()}")
        }
        println("==================")
    }
}