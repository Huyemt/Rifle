# [Return to the tutorial](../README.md)
# [Returns a list of built-in functions](../builtFunc.md)
***

# len
## Introduce
Read length
## Parameters
| Name | Type     | Description                             | Optional |
|:-----|:---------|:----------------------------------------|:---------|
| obj  | Any type | An object whose length needs to be read |          |
## Return value
`Number`
## Example
```bullet
// 6
println( len("Bullet") )

// 10086
println( len(10086) )

// 10086.1230
println( len(10086.1230) )

// 2
println( len([0, 1]) )

// 2
println( len({"a":0,"b":1}) )

bStr := "\x5A\x6A"

// 2
println( len(bStr) )

// 1
println( len(bStr[0]) )

// 1
println( len(true) )

// 0
println( len(null) )
```

# println
## Introduce
Word wrap output
## Parameters
Variable length parameter, no accurate parameter value.
## Return value
`String`
## Example
```bullet
// a
s := println( "a" )

// a    a
println( s, s )
```

# print
## Introduce
Word wrap output
## Parameters
Variable length parameter, no accurate parameter value.
## Return value
`String`
## Example
```bullet
s := print( "a" )
print( s, s )

// Echo：aa    a
```