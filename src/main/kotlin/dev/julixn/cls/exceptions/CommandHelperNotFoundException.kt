package dev.julixn.cls.exceptions

class CommandHelperNotFoundException(className: String) : Exception("CommandHelper not found in class $className!")