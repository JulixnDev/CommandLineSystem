package example.commands

import dev.julixn.cls.command.CloudCommand
import dev.julixn.cls.command.CommandHelper
import dev.julixn.cls.command.options.CommandOption
import dev.julixn.cls.command.options.OptionType

@CommandHelper(
    name = "my",
    options = [
        CommandOption(
            name = "bye", type = OptionType.MASTER, description = "The message to say goodbye", childs = [
                CommandOption(name = "default", type = OptionType.TEXT, multiple = true)
            ]
        )
    ]
)
class MyCommand : CloudCommand() {
    override fun run(arguments: HashMap<String, Any>) {
        println(arguments["hello"])
        if (isOptionProvided("bye-default"))
            println(getOptionValue("bye-default"))
    }
}