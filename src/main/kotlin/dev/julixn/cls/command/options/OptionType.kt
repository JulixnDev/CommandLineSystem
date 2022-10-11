package dev.julixn.cls.command.options

enum class OptionType(val regex: Regex) {

    TEXT(Regex("([A-Za-z0-9])\\w+")),
    NUMBER(Regex("([0-9]*)")),
    BOOLEAN(Regex("(yes|no|y|n|true|false)")),
    EMAIL(Regex("([a-zA-Z0-9]+@[a-zA-Z0-9]+[.][a-zA-Z0-9]+)")),
    URL(Regex("([a-zA-Z0-9]+[.][a-zA-Z0-9]+)")),
    NONE(Regex("")),
    MASTER(Regex("")),
    ALL(Regex(".*"))

}