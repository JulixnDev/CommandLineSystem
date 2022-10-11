# Documentation -> CommandHelper

  
---  

Every Command needs a CommandHelper annotation in witch we specify the name. We can optionally set also a description
or  
aliases.

```kotlin  
@CommandHelper(  
    name = "my",  
    description = "My cool command",  
    alias = [  
        "my-command"  
    ]  
)  
class MyCommand : CloudCommand()   
```  

## -> CommandArguments

  
---  

We have also the option to set arguments.  
In the argument you must set a type, after witch the CommandProcessor will check it and make it available in a map for  
direct access.

```kotlin  
@CommandHelper(  
    name = "my",  
    arguemnts = [  
        CommandArgument(name = "hello", type = OptionType.TEXT)  
    ]  
)  
class MyCommand : CloudCommand() {  
    override fun run(arguments: HashMap<String, Any>) {  
        println(arguments["hello"])  
    }  
}  
```  

Arguments have many options to configure.

We start with the options for the type.

|  Name   | Input                                                  |  
|:-------:|--------------------------------------------------------|  
|   ALL   | Here you can use anything over text, numbers           |  
|  Text   | Here are only alphanumeric symbols and numbers allowed |  
| Number  | All numbers                                            |  
| Boolean | y, yes, true, n, no, false                             |  
|  EMail  | An simple email like "admin@admin.de"                  |  
|   Url   | Urls like "admin.de"                                   |  
|         |                                                        |  
| Master  | This can't be used in arguments                        |  
|  None   | This can't be used in arguments                        |  

Arguments can also be optional this means it can be recognized as argument if it is set, but it also can be null.

To make an argument optional set optional to true.  
It also can have a description.

```kotlin  
@CommandHelper(  
    name = "my",  
    arguemnts = [  
        CommandArgument(name = "hello", type = OptionType.TEXT, optional = true, description = "My good description!")  
    ]  
)  
class MyCommand : CloudCommand() {  
    override fun run(arguments: HashMap<String, Any>) {  
        println(arguments["hello"])  
    }  
}  
```  

At this point our command looks in the terminal something like this: ``my Welcome``!  
Where 'Welcome' is the input for the argument 'hello'

If we use ``my @yeah`` it won't allow it because it does not match the pattern.

## -> CommandOptions

  
---

We don't only have arguments to collect input's. We also have options witch are all optional and started with `--`
like `--bye <value>`. An option does not need a value.

If we want that our option have a value we have to set the type to something of the table, excluding Master and None.

|  Name   | Input                                                     |  
|:-------:|-----------------------------------------------------------|  
|   ALL   | Here you can use anything over text, numbers              |  
|  Text   | Here are only alphanumeric symbols and numbers allowed    |  
| Number  | All numbers                                               |  
| Boolean | y, yes, true, n, no, false                                |  
|  EMail  | An simple email like "admin@admin.de"                     |  
|   Url   | Urls like "admin.de"                                      |  
|         |                                                           |  
| Master  | This need to be set if option has childs                  |  
|  None   | This need to be set if the option should not have a value |  

This can look something like this:

```kotlin  
@CommandHelper(  
    name = "my",  
    options = [
	    CommandOption(name = "bye", type = OptionType.TEXT, description = "The message to say goodbye")
    ]
)  
class MyCommand : CloudCommand() {  
    override fun run(arguments: HashMap<String, Any>) {  
        println(arguments["hello"])  
    }  
}  
```  

We also can set a description.

If we want that the option doesn't have a value yout need to set the type to NONE.

```kotlin  
@CommandHelper(  
    name = "my",  
    options = [
	    CommandOption(name = "bye", type = OptionType.NONE, description = "The message to say goodbye")
    ]
)  
class MyCommand : CloudCommand() {  
    override fun run(arguments: HashMap<String, Any>) {  
        println(arguments["hello"])  
    }  
}  
```  

But how I mentioned aboth an option also can have childs. These are accessable via `--master-child`.
To use childs you set the type to MASTER and specify all childs under childs.

```kotlin
@CommandHelper(
    name = "my",
    options = [
        CommandOption(
            name = "bye", type = OptionType.MASTER, description = "The message to say goodbye", childs = [
                CommandOption(name = "default", type = OptionType.TEXT)
            ]
        )
    ]
)
class MyCommand : CloudCommand() {
    override fun run(arguments: HashMap<String, Any>) {
        println(arguments["hello"])
    }
} 
```

We can also collect multiple value through one option if we set multiple to true.

```kotlin
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
    }
} 
```

### -> Work with Options

To get an option we use `getOptionValue("name")`. To see if an option was set we can use `isOptionProvided("name")`.

In use that looks like this:

```kotlin
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
```
