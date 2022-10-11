package dev.julixn.cls.command

interface CommandManager {

    fun registerCommand(command: Command): Boolean
    fun registerCommands(vararg commands: Command)

    fun unregisterCommand(name: String): Boolean
    fun unregisterCommand(commandHelper: CommandHelper): Boolean
    fun unregisterCommand(command: Command): Boolean

    fun disableCommand(name: String): Boolean
    fun disableCommand(command: Command): Boolean

    fun enableCommand(name: String): Boolean
    fun enableCommand(command: Command): Boolean

    fun getCommand(name: String): Command?
    fun getAllCommands(): Map<String, Command>

}