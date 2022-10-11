package dev.julixn.cls.terminal

import dev.julixn.cls.CommandLineSystem
import dev.julixn.cls.utils.scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class Terminal {

    lateinit var terminalListener: TerminalListener

    fun init() {
        terminalListener = TerminalListener()
    }

    fun createListener() {
        terminalListener.createCycle()
    }

    class TerminalListener {
        private val reader = BufferedReader(InputStreamReader(System.`in`))

        fun readNext(): String {
            return reader.readLine()
        }

        var paused: Boolean = false

        private lateinit var commandCycle: Job

        fun createCycle() {
            commandCycle = scope.launch {
                print("\r${CommandLineSystem.prefix} > ")
                var line: String = withContext(Dispatchers.IO) {
                    reader.readLine()
                }

                while (!paused) {
                    CommandLineSystem.get().commandLogicProcessor.process(line)

                    print("\r${CommandLineSystem.prefix} > ")
                    line = withContext(Dispatchers.IO) {
                        reader.readLine()
                    }
                }
            }
        }

        fun pause(paused: Boolean) {
            this.paused = paused
            if (paused)
                print("\r")
            else
                print("\r${CommandLineSystem.prefix} > ")
        }

        fun startCycle() {
            commandCycle.start()
        }

        suspend fun joinCycle() {
            commandCycle.join()
        }

        fun cancelCycle() {
            commandCycle.cancel()
        }
    }

}