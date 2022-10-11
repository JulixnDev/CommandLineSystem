package dev.julixn.cls.terminal

import dev.julixn.cls.CommandLineSystem
import dev.julixn.cls.command.CloudCommand
import dev.julixn.cls.command.arguments.CommandArgument
import dev.julixn.cls.command.options.OptionType
import dev.julixn.cls.utils.remove

class CommandLogicProcessor {

    fun process(input: String): Boolean {
        val parts = input.split(" ")
        val commandName = parts[0]
        var arguments = parts.subList(1, parts.size)
        val options: HashMap<String, Any?> = hashMapOf()
        val namedArgumentMap: HashMap<String, Any> = hashMapOf()

        val command = CommandLineSystem.get().commandManager.getCommand(commandName) ?: return false

        arguments.forEach { optionName ->
            if (optionName.startsWith("--")) {
                val index = arguments.indexOf(optionName)

                val optionSearchName = if (optionName.contains("-"))
                    optionName.substring(2).split("-")[0]
                else
                    optionName.substring(2)

                val option = command.getCommandHelper()?.options?.find {
                    it.name.lowercase() == optionSearchName.lowercase()
                } ?: return false

                if (option.type == OptionType.MASTER) {
                    val childName = optionName.substring(2).split("-")[1]
                    val child = option.childs.find { it.name == childName } ?: return false

                    val value = if (child.type != OptionType.NONE)
                        arguments[index + 1]
                    else null

                    arguments = remove(arguments.toTypedArray(), index).toList() as List<String>

                    if (child.type != OptionType.NONE)
                        arguments = remove(arguments.toTypedArray(), index).toList() as List<String>

                    options[optionName.substring(2)] = value
                } else {
                    var value: String? = null
                    var valueList: ArrayList<String>? = null

                    if (option.type != OptionType.NONE) {
                        value = arguments[index + 1]

                        if (option.multiple) {
                            valueList = arrayListOf(value)

                            if (!option.type.regex.matches(value))
                                println("The Value does not matches the requirement!")

                            var i = index + 2
                            if (arguments.size > i) {
                                do {
                                    if (!arguments[i].startsWith("--")) {
                                        if (!option.type.regex.matches(value))
                                            println("The Value does not matches the requirement!")

                                        valueList.add(arguments[i])
                                    } else break

                                    i++
                                } while (i < arguments.size)
                            }

                        }
                    }

                    arguments = remove(arguments.toTypedArray(), index).toList() as List<String>

                    if (option.type != OptionType.NONE) {
                        arguments = remove(arguments.toTypedArray(), index).toList() as List<String>

                        if (option.multiple) {
                            var i = 1
                            do {
                                arguments = remove(arguments.toTypedArray(), index).toList() as List<String>
                                i++
                            } while (i < valueList!!.size)
                        }
                    }

                    if (option.multiple)
                        options[optionName.substring(2)] = valueList
                    else
                        options[optionName.substring(2)] = value
                }
            }
        }

        val neededArguments = command.getCommandHelper()?.arguments?.sortedBy {
            it.optional
        }

        val rangeList = if (arguments.size > neededArguments?.size!!)
            neededArguments
        else
            neededArguments.toTypedArray().copyOfRange(0, arguments.size).toList()

        val newOrderedList: ArrayList<CommandArgument> = arrayListOf()

        command.getCommandHelper()?.arguments?.forEach {
            if (rangeList.find { _it -> _it == it } != null) {
                newOrderedList.add(rangeList.find { _it -> _it == it }!!)
            }
        }

        if (arguments.size >= command.getCommandHelper()?.arguments?.filter { !it.optional }?.size!!) {
            newOrderedList.forEachIndexed { index, neededArgument ->
                val givenArgument = arguments[index]

                if (neededArgument.type != OptionType.NONE)
                    if (!neededArgument.type.regex.matches(givenArgument))
                        println("${neededArgument.name} | $givenArgument > The Value does not matches the requirement!")

                namedArgumentMap[neededArgument.name] = givenArgument
            }

            if (command.isDisabled()) {
                println("${CommandLineSystem.prefix} -> This command was disabled! If you want to enable it use 'cmdman enable ${command.getCommandHelper()!!.name}'")
                return false
            }

            (command as CloudCommand).provide(namedArgumentMap, options)
            return true
        } else
            println("You need to specify all needed arguments for the command!")

        return false
    }

}