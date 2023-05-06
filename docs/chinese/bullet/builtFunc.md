# [返回教程](README.md)
***
# 通用
## len
## println
## print

# 数据
## jsonE
所需参数(1)：content
<br><br>
将`列表`或者`字典`转换为`JSON格式`的`字符串`

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
## jsonD
所需参数(1)：content
<br><br>
将`JSON格式`的`字符串`转换为`列表`或者`字典`

```bullet
data := "[{\"name\":\"Huyemt\",\"age\":{\"value\":17}}]"

data = jsonD( data )

// Huyemt
println( data[0]["name"] )
```
# 网络
## nget
## npost

# 密码学
## md5
## sha1
## sha256
## sha512
## urlE
## urlD
## base64E
## base64D
## aesE
## aesD
## rsaE
## rsaD

# 类型
## bytes
## str
## num
## isbstr
## isbyte
## isstr
## isnum
## islist
## isdict
## isnull
## isbool