package dev.julixn.cls

import dev.julixn.cls.command.CommonCommandManager
import dev.julixn.cls.command.integration.CommandManagementCommand
import dev.julixn.cls.command.integration.InfoCommand
import dev.julixn.cls.terminal.CommandLogicProcessor
import dev.julixn.cls.terminal.Terminal

class CommandLineSystem {

    companion object {
        const val prefix: String = "CommandLineSystem"

        lateinit var instance: CommandLineSystem

        fun get(): CommandLineSystem {
            return this.instance
        }

        fun createNew() {
            CommandLineSystem()
        }
    }

    val commandManager = CommonCommandManager()
    val commandLogicProcessor = CommandLogicProcessor()
    val terminal = Terminal()

    init {
        instance = this
        terminal.init()

        commandManager.registerCommands(InfoCommand(), CommandManagementCommand())
    }

    suspend fun start() {
        terminal.createListener()
        terminal.terminalListener.joinCycle()
    }

}