package dev.julixn.cls.exceptions

class CommandNameAlreadyExistsException(commandName: String) :
    Exception("A command with the name '$commandName' already exists!")