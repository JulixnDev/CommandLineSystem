package dev.julixn.cls.command

import dev.julixn.cls.exceptions.CommandHelperNotFoundException
import dev.julixn.cls.exceptions.CommandNameAlreadyExistsException
import dev.julixn.cls.logging.Logger

class CommonCommandManager : CommandManager {

    private val commandMap: HashMap<String, Command> = hashMapOf()

    override fun registerCommand(command: Command): Boolean {
        if (command.getCommandHelper() == null) {
            Logger.error(CommandHelperNotFoundException(command.javaClass.simpleName))
            return false
        }

        if (commandMap.containsKey(command.getCommandHelper()!!.name)) {
            Logger.error(
                CommandNameAlreadyExistsException(command.getCommandHelper()!!.name)
            )
            return false
        }

        commandMap[command.getCommandHelper()!!.name] = command
        return true
    }

    override fun registerCommands(vararg commands: Command) {
        commands.forEach { registerCommand(it) }
    }

    override fun unregisterCommand(name: String): Boolean {
        if (commandMap.containsKey(name))
            return this.unregisterCommand(commandMap[name]!!)

        return false
    }

    override fun unregisterCommand(commandHelper: CommandHelper): Boolean {
        return this.commandMap.remove(commandHelper.name) != null
    }

    override fun unregisterCommand(command: Command): Boolean {
        if (command.getCommandHelper() == null)
            return false

        return this.commandMap.remove(command.getCommandHelper()!!.name) != null
    }

    override fun disableCommand(name: String): Boolean {
        if (!commandMap.containsKey(name))
            return false

        commandMap[name]!!.setDisabled(true)
        return true
    }

    override fun disableCommand(command: Command): Boolean {
        if (command.getCommandHelper() == null || !commandMap.containsKey(command.getCommandHelper()!!.name))
            return false

        commandMap[command.getCommandHelper()!!.name]!!.setDisabled(true)
        return true
    }

    override fun enableCommand(name: String): Boolean {
        if (!commandMap.containsKey(name))
            return false

        commandMap[name]!!.setDisabled(false)
        return true
    }

    override fun enableCommand(command: Command): Boolean {
        if (command.getCommandHelper() == null || !commandMap.containsKey(command.getCommandHelper()!!.name))
            return false

        commandMap[command.getCommandHelper()!!.name]!!.setDisabled(false)
        return true
    }

    override fun getCommand(name: String): Command? {
        if (commandMap.containsKey(name))
            return commandMap[name]

        var command: Command? = null

        commandMap.values.filter { it.getCommandHelper() != null }.forEach {
            if (it.getCommandHelper()!!.aliases.find { _it -> _it == name } != null)
                command = it
        }

        return command
    }

    override fun getAllCommands(): Map<String, Command> {
        return this.commandMap
    }
}