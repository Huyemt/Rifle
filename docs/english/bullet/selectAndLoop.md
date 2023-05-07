# [Return to the tutorial](README.md)
***
# Select structure
The `Selection Structure`, also called `Judgment Body`, is composed of `if` and `else` keywords, which can execute different code blocks according to conditions.
## Simple composition
```bullet
A := 10

// If the value of a is equal to 10
if ( A == 10 ) {
    // print A == 10
    println( "A == 10" )
} else {
    // print A != 10
    println( "A != 10" )
}
```
## Homologous multiple selection
```bullet
A := 10

// If the value of a is equal to 10
if ( A > 10 ) {
    // print A > 10
    println( "A > 10" )
} else if ( A != 10 ) {
    // Otherwise, if a != 10

    // println A != 10
    println( "A != 10" )
} else {
    // print the value of A
    println( "A == " + str(A) )
}

// Echo： A == 10
```
## Nested Selection
```bullet
A := 10

if ( A > 10 ) {
    if ( A < 12 )
        println("10 < A < 12")
} else {
    if ( A > 0 )
        println("0 < A < 10")
}

// Echo：0 < A < 10
```
# Looping Structure
`Looping Structure` is an essential structure for computer programming.
<br><br>
The `break` keyword is used to `terminate the looping`.
<br>
The `continue` keyword is used to `skip the current loop`
## until
`Bullet` replaces the traditional `while` loop body with `until` loop body, which makes the code easier for developers to read.
<br>
<br>
The work of the `until` circulation body is to achieve the `purpose`.
<br><br>
The premise that the `else` body is executed is that the loop header must be executed more than once.
### Infinite Looping
```bullet
A := 0

until ( A == 10 ) {
    A += 1
} else {
    // 10
    println("done")
}

// 10
println( A )
```
### Infinite Looping
```bullet
until ( false ) {
    println( "looping..." )
}
```
## for
The premise that the `else` body is executed is that the loop header must be executed more than once.
### Finite Looping
```bullet
A := 5

for ( i := 0; i < A; i += 1 ) {
    println( i )
} else {
    println("done")
}

// 0
// 1
// 2
// 3
// 4
// done
```
### Infinite Looping
```bullet
for (; true; ) {
    println( "looping..." )
}
```