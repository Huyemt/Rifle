# [返回教程](../README.md)
# [返回内置函数目录](../builtFunc.md)
***

# urlE
## 介绍
将`字符串`进行`URL`编码
## 参数
| 参数      | 参数类型 | 描述                      | 是否选填 |
|:--------|:-----|:------------------------|:-----|
| content | 字符串  | 需要解码成`Base64`的`content` |      |
| mark    | 布尔值  | 是否编码标点符号                | √    |
## 返回值
`字符串`
## 示例
```bullet
content := "abc , def"

// abc%20%2C%20def
println( urlE( content ) )

// abc%20,%20def
println( urlE( content, false ) )
```

# urlD
## 介绍
将`字符串`进行`URL`编码
## 参数
| 参数      | 参数类型 | 描述                      | 是否选填 |
|:--------|:-----|:------------------------|:-----|
| content | 字符串  | 需要解码成`Base64`的`content` |      |
| mark    | 布尔值  | 是否编码标点符号                | √    |
## 返回值
`字符串`
## 示例
```bullet
content := "abc%20%2C%20def"

// abc , def
println( urlD( content ) )

// abc , def
println( urlD( "abc%20,%20def" ) )
```

# base64E
## 介绍
将`字符串`，`字典`，`数字`，`字节字符串`转换为`Base64`的`字符串`或`字节字符串`
## 参数
| 参数      | 参数类型            | 描述                      | 是否选填 |
|:--------|:----------------|:------------------------|:-----|
| content | 字符串，列表，数字，字节字符串 | 需要编码成`Base64`的`content` |      |
## 返回值
`字符串`或`字节字符串`或`列表`
## 示例
```bullet
content := "Hello, world"

// SGVsbG8sIHdvcmxk
println( base64E( content ) )

// b'\x53\x47\x56\x73\x62\x47\x38\x73\x49\x48\x64\x76\x63\x6d\x78\x6b'
println( base64E( bytes(content) ) )
```

# base64D
## 介绍
将`字符串`，`字典`，`数字`，`字节字符串`转换为`Base64`的`字符串`或`字节字符串`
## 参数
| 参数      | 参数类型      | 描述                      | 是否选填 |
|:--------|:----------|:------------------------|:-----|
| content | 字符串，字节字符串 | 需要解码成`Base64`的`content` |      |
## 返回值
`字符串`或`字节字符串`或`列表`
## 示例
```bullet
data := "[{\"name\":\"Huyemt\",\"age\":{\"value\":17}}]"

data = jsonD( data )

// Huyemt
println( data[0]["name"] )
```