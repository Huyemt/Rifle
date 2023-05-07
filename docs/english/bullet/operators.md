# [Return to the tutorial](README.md)
***
# Priority of Operators
| Type                        | Operator                   | Sequence           |
|:----------------------------|:---------------------------|:-------------------|
| suffix                      | ()                         |                    |
| pow                         | **                         |                    |
| unary                       | +, -, !                    | From right to left |
| multiplication and division | *, /, %                    | From left to right |
| add and subtract            | +, -                       | From left to right |
| relationship                | <, <=, >, >=               | From left to right |
| equal                       | ==, !=                     | From left to right |
| logical and                 | &&                         |                    |
| logical or                  | &#124;&#124;               |                    |
| assignment                  | =, +=. -=. *=, /=, %=, **= | From right to left |
| comma                       | ,                          |                    |
***
# Arithmetic Operators
| Operator | Description | Usage  |
|:---------|:------------|:-------|
| +        | add         | A + B  |
| -        | subtract    | A - B  |
| *        | multiply    | A * B  |
| /        | division    | A / B  |
| **       | pow         | A ** B |
| %        | mod         | A % B  |

## Digital example
```bullet
A := 90
B := 10

// 100
println( A + B )

// 80
println( A - B )

// 900
println( A * B )

// 9
println( A / B )

// 34867844010000000000
println( A ** B )

// 0
println( A % B )

// 80
println( A + -B )
```

## String example
_Note: Arithmetic operators for strings are only supported for `+` and `*`_

```bullet
A := "A"
B := "B"

// AB
println( A + B )

// AA
println( A * 2 )
```

# Relational operators
_Note: The final result type of the relational operators is Boolean._

| Operator | Description      | Usage  |
|:---------|:-----------------|:-------|
| ==       | Equal            | A == B |
| !=       | Not Equal        | A != B |
| \>       | Greater          | A > B  |
| <        | Lesser           | A < B  |
| \>=      | Greater or Equal | A >= B |
| <=       | Lesser or Equal  | A <= B |

## Digital example
```bullet
A := 90
B := 10

// false
println( A == B )

// true
println( A != B )

// true
println( A > B )

// false
println( A < B )

// true
println( A >= B )

// false
println( A <= B )
```

## String example
_Note: The relational operators for strings only support `==` and `!=`_

```bullet
A := "A"
B := "B"

// false
println( A == B )

// true
println( A != B )
```

## List example
_Note: The relational operators for lists only support `==` and `!=`_

```bullet
A := []
B := [1]

// false
println( A == B )

// true
println( A != B )
```

## Dictionary example
_Note: The relational operators for dictionaries only support `==`and `!=`_

```bullet
A := {"a":1}
B := {"a":2}

// false
println( A == B )

// true
println( A != B )
```

## Mixed example
```bullet
A := []
B := {"B":1}

// false
println( A == B )

// true
println( A != B )
```

# Logical Operators
_Note: The final result type calculated by logical operators is Boolean_

| Operator     | Description | Usage            |
|:-------------|:------------|:-----------------|
| &&           | Logical and | A && B           |
| &#124;&#124; | Logical or  | A &#124;&#124; B |
| !            | Logical not | !(A && B)        |

`&&` can be replaced by `and`
<br>
`||` can be replaced by `or`
<br>
`!` can be replaced by `not`
```bullet
A := true
B := false

// false
println( A and B )
println( A && B )

// true
println( A or B )
println( A || B )

// false
println( !A )
println( not A )

// true
println( !(A && B) )
println( not (A && B) )
```

# Assigning operators
_Note: The operation in assignment operator is consistent with the law of arithmetic operator_

| Operator | Description                           | Usage   |
|:---------|:--------------------------------------|:--------|
| =        | Assign a value to a declared variable | A = B   |
| :=       | Declare variables and assign values   | A := B  |
| +=       | Addition assignment                   | A += B  |
| -=       | Subtraction assignment                | A -= B  |
| *=       | Multiplication assignment             | A *= B  |
| /=       | Division assignment                   | A /= B  |
| **=      | Power assignment                      | A **= B |
| %=       | Modular assignment                    | A %= B  |

## Detail
```bullet
A := 10
```
same as
```bullet
var A = 10
```

## Example
```bullet
// Declare variables
A := 1

// 11
A += 10
println( A )

// 1
A -= 10
println( A )

// 10
A *= 10
println( A )

// 1
A /= 10
println( A )

// 1
A **= 10
println( A )

// 1
A %= 10
println( A )
```

## Advanced example
```bullet
// 4
A := 1
A += A += A += 1
println( A )
```