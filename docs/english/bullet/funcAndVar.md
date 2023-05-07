# [Return to the tutorial](README.md)
***
# Foreword
Every programming language should support functions and variables, which are as important as atoms in chemical reactions in high-level languages.
<br>
The essence of programming language structure is actually a patchwork of many functions and variables.
***
# Introduce
In Bullet, we use `func` as the keyword of function declaration and `var` as the keyword of variable declaration.
<br>
Moreover, we have the same variable derivation formula as `Golang`, which makes the process of programming more concise.
***
# Variables
_Note: Bullet stipulates that programmers need to use the following methods to declare all variables that do not exist_

```bullet
var i = 10
```
same as
<br>

```bullet
i := 10
```
<br>

## Demonstration of variable declaration
### Strings
```bullet
aStr := "Hello, world"

// true
println( isstr(aStr) )
```
<br>

### Lists
```bullet
var aList = [0, 1, 2, 3, 4]

// true
println( islist(aList) )
```
<br>

### Dictarytions
```bullet
aDict := {
    "a": 1,
    "b": 1.1,
    "c": "str",
    "d": [1, 2, 3],
    "e": false,
    "f": {
        "a": 1
    }
}

// true
println( isdict(aDict) )
```
***
# Functions
`Bullet` has many useful functions built in. You can query it [click here](builtFunc.md).

_Note: Bullet currently does not support the writing of nested functions_

In daily code writing, you will often encounter functions that don't need to pass in parameters.
<br>
```bullet
func voidFunction() {
    // statements...
}
```
same as
```bullet
func voidFunction {
    // statements...
}
```
<br>

## Five types of functions
### No parameters and no return value
```bullet
func aFunc {
    println("Hello, world")
}

// called
aFunc()
```
<br>

### Carry parameters but no return value
```bullet
func aFunc( param ) {
    println(param)
}

// called
aFunc("Hello, world")
```
<br>

### No parameters but with return value
```bullet
aK := 1
func aFunc {
    aK += 1
    return aK
}

// called
println(aFunc())
```
<br>

### With parameters and return values
```bullet
func aFunc( list ) {
    list[] = len(list)
    return list
}

// 调用
a := aFunc( [] )

// [0]
println(a)

aFunc(a)

// [0,1]
println(a)
```
### Default parameters
```bullet
func aFunc( msg="Hello, World" ) {
    println( msg )
}

// called
// Hello, World
aFunc()

// Hi, Bullet
aFunc("Hi, Bullet")
```
## Advanced usage
### Parameter name passing parameter
`Bullet` allows developers to specify a parameter name when calling parameters.
<br>
```bullet
func test(name, age=0, height=175) {
    println(name, age, height)
}

// Specify parameters to pass parameters
// Bullet	0	175
test(name="Bullet")

// Specifies that the parameters are passed in sequence
// Bullet	1	180
test(name="Bullet", age=1, height=180)

// Disrupt the order of specified parameters
// Bullet	1	180
test(age=1, height=180, name="Bullet")

// Missing some parameters
// Bullet	0	180
test(name="Bullet", height=180)

// Direct transmission of parameters
// Bullet	1	180
test("Bullet", 1, 180)

bullet := "Bullet"

// Variable transfer and calculation
// Bullet	0	180
test(bullet, height=175+5)

// Automatic reset parameter transmission
// Bullet	1	180
test("Bullet", height=180, 1) // name, height, age
```
### Recursion
The most common recursive function is `Fibonacci` sequence.
```bullet
func fibonacci( n ) {
    if ( n <= 2 ) return 1
    
    return fibonacci( n - 1 ) + fibonacci( n - 2 )
}

// 55
println( fibonacci(10) )
```