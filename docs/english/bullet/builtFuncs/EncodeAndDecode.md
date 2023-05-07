# [Return to the tutorial](../README.md)
# [Returns a list of built-in functions](../builtFunc.md)
***

# urlE
## Introduce
Encode the `String` with `URL`.
## Parameters
| Name    | Type    | Description                         | Optional |
|:--------|:--------|:------------------------------------|:---------|
| content | String  | Value to be encoded                 |          |
| mark    | Boolean | Whether to encode punctuation marks | √        |
## Return value
`String`
## Example
```bullet
content := "abc , def"

// abc%20%2C%20def
println( urlE( content ) )

// abc%20,%20def
println( urlE( content, false ) )
```

# urlD
## Introduce
Decode the `String` with `URL`.
## Parameters
| Name    | Type   | Description         | Optional |
|:--------|:-------|:--------------------|:---------|
| content | String | Value to be decoded |          |
## Return value
`String`
## Example
```bullet
content := "abc%20%2C%20def"

// abc , def
println( urlD( content ) )

// abc , def
println( urlD( "abc%20,%20def" ) )
```

# base64E
## Introduce
Convert `String`, `Dictionary`, `Number` and `ByteString` into `String` or `ByteString` of `Base64`
## Parameters
| Name    | Type                             | Description         | Optional |
|:--------|:---------------------------------|:--------------------|:---------|
| content | String, List, Number, ByteString | Value to be encoded |          |
## Return value
`String` or `ByteString` or `List` or `Dictionary`
## Example
```bullet
content := "Hello, world"

// SGVsbG8sIHdvcmxk
println( base64E( content ) )

// b'\x53\x47\x56\x73\x62\x47\x38\x73\x49\x48\x64\x76\x63\x6d\x78\x6b'
println( base64E( bytes(content) ) )
```

# base64D
## Introduce
Convert `String` or `ByteString` of `Base64` into `String` and `ByteString`
## Parameters
| Name    | Type               | Description         | Optional |
|:--------|:-------------------|:--------------------|:---------|
| content | String, ByteString | Value to be encoded |          |
## Return value
`String` or `ByteString` or `List` or `Dictionary`
## Example
```bullet
data := "[{\"name\":\"Huyemt\",\"age\":{\"value\":17}}]"

data = jsonD( data )

// Huyemt
println( data[0]["name"] )
```