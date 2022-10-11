package dev.julixn.cls.command

interface Command {

    fun run(arguments: HashMap<String, Any>)

    fun isOptionProvided(name: String): Boolean

    fun getOptionValue(name: String): Any?

    fun printInfo()

    fun getCommandHelper(): CommandHelper?

    fun isDisabled(): Boolean
    
    fun setDisabled(value: Boolean)

}