# [Return to the tutorial](README.md)
***
# Introduce
`Bullet` was originally designed to make it easier to write `Rifle` module.
# Example
## Base Module
A simple module must have the following [Program Attribute](attribute.md):
1. name （The name of this Module）
2. version （The version of this Module）
3. authors （The authors of this Module）
4. canBeSelect （Can it be selected）

```bullet
# name = "MyFirstBtModule"
# version = "1.0.0"
# authors = ["Huyemt", "Teaclon", "Kevin"]
# canBeSelect = true

func onLoad {
    println("Loaded")
}

func onDisable {
    println("Disabled")
}

func onSelected {
    println("Selected")
}

func onQuit {
    println("Quit")
}
```

## Command
The realization of instructions requires developers to add `commands` program attributes
<br>
Its type is `Dictionary`, and its format is as follows:
```bullet
{ "Command Name": ["Function Name", "Command introduction", ["Command usage 1", "Command usage 2", ... ]], ... }
```
Specific implementation:
```bullet
# name = "MyFirstBtModule"
# version = "1.0.0"
# authors = ["Huyemt", "Teaclon", "Kevin"]
# canBeSelect = true
# commands = {
    "test": ["cmd_test", "Test Command", ["test", "test <args...>"]]
}

func onLoad {
    println("Loaded")
}

func onDisable {
    println("Disabled")
}

func onSelected {
    println("Selected")
}

func onQuit {
    println("Quit")
}

// Implement the function of the instruction
// The name args can be changed at will, but there must be a parameter as the data entry, which belongs to the list.
func cmd_test( args ) {
    if ( len( args ) == 0 )
        println( "Test" )
    else
        println( "Test -> " + str(args) )
}
```

## More Attributes
1. name （String）
2. version （String）
3. authors （Dictionary or String）
4. canBeSelect （Boolean）
5. website （String）
6. description （String）