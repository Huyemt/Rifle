# [Return to the tutorial](../README.md)
# [Returns a list of built-in functions](../builtFunc.md)
***

# jsonE
## Introduce
Convert `list` or `dictionary` into JSON string.
## Parameters
| Parameter | Type             | Description                                       | Optional |
|:----------|:-----------------|:--------------------------------------------------|:---------|
| content   | Dictionary, List | Content that needs to be encoded as `JSON` string |          |
## Return value
`String`
## Example
```bullet
data := [
    {
        "name": "Huyemt",
        "age": 17
    }
]

data = jsonE( data )

// [{"name":"Huyemt","age":{"value":17}}]
println( data )
```

# jsonD
## Introduce
Convert JSON string into `List` or `Dictionary`
## Parameters
| Parameter | Type   | Description                                                        | Optional |
|:----------|:-------|:-------------------------------------------------------------------|:---------|
| content   | String | `JSON` string that needs to be decoded into `Dictionary` or `List` |          |
## Return value
`Dictionary` or `List`
## Example
```bullet
data := "[{\"name\":\"Huyemt\",\"age\":{\"value\":17}}]"

data = jsonD( data )

// Huyemt
println( data[0]["name"] )
```