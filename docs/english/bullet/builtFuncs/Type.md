# [Return to the tutorial](../README.md)
# [Returns a list of built-in functions](../builtFunc.md)
***

# bytes
## Introduce
`String` to `ByteString`
## Parameters
| Name     | Type           | Description                                     | Optional |
|:---------|:---------------|:------------------------------------------------|:---------|
| obj      | Number, String | Value that needs to be converted to `ByteSring` |          |
| encoding | String, Null   | Encoding name ( default `utf-8` )               | √        |
## Return value
`ByteString`
## Example
```bullet
obj := "Hello, World"

// b'\x48\x65\x6c\x6c\x6f\x2c\x20\x57\x6f\x72\x6c\x64'
println( bytes( obj ) )
```

# str
## Introduce
`Any type` to `String`
## Parameters
| Name     | Type         | Description                                  | Optional |
|:---------|:-------------|:---------------------------------------------|:---------|
| obj      | Any type     | Value that needs to be converted to `String` |          |
| encoding | String, Null | Encoding name ( default `utf-8` )            | √        |
## Return value
`String`
## Example
```bullet
bStr := "\x48\x65\x6c\x6c\x6f\x2c\x20\x57\x6f\x72\x6c\x64"
byte := bStr[0]
num := 10086.999
dict := {"a": 2}
list := [0, 1, dict]

// Hello, World
println( str( bStr ) )

// H
println( str( byte ) )

// 10086.999
println( str( num ) )

// {"a":2}
println( str( dict ) )

// [0,1,{"a":2}]
println( str( list ) )

// null
println( str( null ) )

// true
println( str( true ) )
```

# num
## Introduce
`String` to `Number`
## Parameters
| Name | Type           | Description                                  | Optional |
|:-----|:---------------|:---------------------------------------------|:---------|
| obj  | Number, String | Value that needs to be converted to `Number` |          |
## Return value
`Number`
## Example
```bullet
// 1111111.10086
println( num( "000001111111.10086000000" ) )
```

# isbstr
## Introduce
Judging whether the value is `ByteString`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( isbstr( "Bullet" ) )

// true
println( isbstr( "\x5A" ) )

// false
println( isbstr( 10086 ) )
```

# isbyte
## Introduce
_Note: `ByteString` also belongs to `Byte`_

Judging whether the value is `Byte`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( isbyte( "Bullet" ) )

// true
println( isbyte( "\x5A\x5A" ) )

// true
println( isbyte( "\x5A\x5A"[0] ) )

// false
println( isbstr( 10086 ) )
```

# isstr
## Introduce
Judging whether the value is `String`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// true
println( isstr( "Bullet" ) )

// false
println( isstr( "\x5A\x5A" ) )

// false
println( isstr( 10086 ) )
```

# isnum
## Introduce
Judging whether the value is `Number`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( isnum( "Bullet" ) )

// false
println( isnum( "\x5A\x5A" ) )

// true
println( isnum( 10086 ) )

// true
println( isnum( 10086.999 ) )
```

# islist
## Introduce
Judging whether the value is `List`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( islist( "Bullet" ) )

// false
println( islist( "\x5A\x5A" ) )

// false
println( islist( 10086 ) )

// true
println( islist( [] ) )
```

# isdict
## Introduce
Judging whether the value is `Dictionary`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( isdict( "Bullet" ) )

// false
println( isdict( "\x5A\x5A" ) )

// false
println( isdict( 10086 ) )

// false
println( isdict( [] ) )

// true
println( isdict( {} ) )
```

# isnull
## Introduce
Judging whether the value is `null`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( isnull( "Bullet" ) )

// false
println( isnull( "\x5A\x5A" ) )

// false
println( isnull( 10086 ) )

// false
println( isnull( [] ) )

// false
println( isnull( {} ) )

// true
println( isnull( null ) )
```

# isbool
## Introduce
Judging whether the value is `Boolean`
## Parameters
| Name | Type     | Description        | Optional |
|:-----|:---------|:-------------------|:---------|
| obj  | Any type | Value to be judged |          |
## Return value
`Boolean`
## Example
```bullet
// false
println( isbool( "Bullet" ) )

// false
println( isbool( "\x5A\x5A" ) )

// false
println( isbool( 10086 ) )

// false
println( isbool( [] ) )

// false
println( isbool( {} ) )

// false
println( isbool( null ) )

// true
println( isbool( false ) )
```