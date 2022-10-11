package dev.julixn.cls.command

import dev.julixn.cls.command.options.OptionType

abstract class CloudCommand : Command {

    private var disabled: Boolean = false

    private lateinit var options: HashMap<String, Any?>

    fun provide(arguments: HashMap<String, Any>, options: HashMap<String, Any?>) {
        this.options = options
        run(arguments)
    }

    override fun isOptionProvided(name: String): Boolean {
        return options.containsKey(name)
    }

    override fun getOptionValue(name: String): Any? {
        if (!isOptionProvided(name)) return null

        return options[name]
    }

    override fun printInfo() {
        val commandHelper = getCommandHelper()
        if (commandHelper == null) {
            println("CommandHelper is null!")
            return
        }

        println("====== CommandInfo ======")
        println("Name > ${commandHelper.name}")
        println("Description > ${commandHelper.description}")
        println("Enabled > ${!isDisabled()}")

        // Alias
        if (commandHelper.aliases.isNotEmpty()) {
            println("Aliases > ")
            commandHelper.aliases.forEach { alias ->
                run {
                    println("   - $alias")
                }
            }
        }

        // Arguments
        if (commandHelper.arguments.isNotEmpty()) {
            println("Arguments > ")
            println("     Name   |  Description  |  Type  |  Optional ")
            println("    ---------------------------------------------")
            commandHelper.arguments.forEach { argument ->
                run {
                    println("   > ${argument.name} | ${argument.description} | ${argument.type} | ${argument.optional}")
                }
            }
        }

        // Options
        if (commandHelper.options.isNotEmpty()) {
            println("Options > ")
            println("     Name   |  Description  |  Type ")
            println("    --------------------------------")
            commandHelper.options.forEach { option ->
                run {
                    println("   --${option.name} | ${option.description} | ${option.type}")
                    if (option.type == OptionType.MASTER) {
                        option.childs.forEach { child ->
                            println("   |  -${child.name} | ${child.description} | ${child.type}")
                        }
                    }
                }
            }
        }
        println("=========================")
    }

    override fun getCommandHelper(): CommandHelper? {
        return this::class.annotations.find { it.annotationClass == CommandHelper::class } as CommandHelper?
    }

    override fun isDisabled(): Boolean {
        return disabled
    }

    override fun setDisabled(value: Boolean) {
        this.disabled = value
    }

}