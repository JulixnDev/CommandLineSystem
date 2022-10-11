package example

import dev.julixn.cls.CommandLineSystem
import example.commands.MyCommand
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    CommandLineSystem.createNew()
    CommandLineSystem.get().commandManager.registerCommand(MyCommand())
    CommandLineSystem.get().start()
}