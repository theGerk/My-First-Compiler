# A simple compiler for a mini version of Pascal. #

----------

## General ##

This package has the Main class and classes that are non-specific to the project

### Main ###

Static class, with main function and other general static functions

### Pair ###

A generalized pair, able to group together two objects.

### UID Generator ###

This is able to generate unique identifiers. It is used to make labels for the output MIPS so there are no naming conflicts.

----------

## Scanner ##

The scanner package manages the scanner, which reads in the input file (the Pascal file), and tokenizes the input.

----------

## Parser ##

The parser package manages the parser, which takes tokens from the scanner and checks to make sure they are in valid order, and produces and parse tree in the process.

---------

## Symbol Table ##

The symbol table package contains classes that are able to manage and hold all data associated with a symbol or identifier that is parsed from the pascal code.

---------

## Syntax Tree ##

The syntax tree package contains a plethora of classes, with the exception of some abstract classes for the purposes of abstraction, each class here represents one 'object' that is parsed out of the pascal. Each different class represents some different idea, and thus each class is able to generate it's own assembly code. Currently the ```toMips``` function will output a string of MIPS assembly code.