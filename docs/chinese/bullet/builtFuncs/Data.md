# [返回教程](../README.md)
# [返回内置函数目录](../builtFunc.md)
***

# jsonE
## 介绍
将`列表`或`字典`转换为`JSON`字符串
## 参数
| 参数      | 参数类型  | 描述                       | 是否选填 |
|:--------|:------|:-------------------------|:-----|
| content | 字典，列表 | 需要编码成`JSON`字符串的`content` |      |
## 返回值
`字符串`
## 示例
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
## 介绍
将`JSON`字符串转换为`列表`或`字典`
## 参数
| 参数      | 参数类型 | 描述                       | 是否选填 |
|:--------|:-----|:-------------------------|:-----|
| content | 字符串  | 需要解码成`字典`或`列表`的`JSON`字符串 |      |
## 返回值
`字典`或`列表`
## 示例
```bullet
data := "[{\"name\":\"Huyemt\",\"age\":{\"value\":17}}]"

data = jsonD( data )

// Huyemt
println( data[0]["name"] )
```