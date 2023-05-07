# [返回教程](../README.md)
# [返回内置函数目录](../builtFunc.md)
***

# nget
## 介绍
发送`GET`请求
## 参数
| 参数            | 参数类型         | 描述          | 是否选填 |
|:--------------|:-------------|:------------|:-----|
| url           | 字符串          | 需要访问的网址     |      |
| params        | 字典           | URL参数       | √    |
| headers       | 字典           | 附加协议头       | √    |
| cookies       | 字典           | 附加Cookies   | √    |
| data          | 字典，`JSON`字符串 | 请求正文        | √    |
| allow_rediect | 布尔值          | 是否允许重定向     | √    |
| timeout       | 数字           | 访问超时时间（毫秒级） | √    |  
## 返回值
`字典`

| 项名称         | 描述              | 值类型   |
|:------------|:----------------|:------|
| url         | 访问后的网址          | 字符串   |
| content     | 响应内容的字节字符串      | 字节字符串 |
| html        | 响应内容的`UTF-8`字符串 | 字符串   |
| headers     | 响应协议头           | 字典    |
| cookies     | 响应Cookies       | 字典    |
| status_code | 响应码             | 数字    |
## 示例
```bullet
headers := {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36"
}
params := {
    "name": "Bullet"
}

rsp := nget( "https://www.baidu.com/", headers=headers, params=params )

println( rsp['url'] )
println( rsp['html'] )
```

# npost
发送`POST`请求
## 参数
| 参数            | 参数类型         | 描述          | 是否选填 |
|:--------------|:-------------|:------------|:-----|
| url           | 字符串          | 需要访问的网址     |      |
| params        | 字典           | URL参数       | √    |
| headers       | 字典           | 附加协议头       | √    |
| cookies       | 字典           | 附加Cookies   | √    |
| data          | 字典，`JSON`字符串 | 请求正文        | √    |
| allow_rediect | 布尔值          | 是否允许重定向     | √    |
| timeout       | 数字           | 访问超时时间（毫秒级） | √    |  
## 返回值
`字典`

| 项名称         | 描述              | 值类型   |
|:------------|:----------------|:------|
| url         | 访问后的网址          | 字符串   |
| content     | 响应内容的字节字符串      | 字节字符串 |
| html        | 响应内容的`UTF-8`字符串 | 字符串   |
| headers     | 响应协议头           | 字典    |
| cookies     | 响应Cookies       | 字典    |
| status_code | 响应码             | 数字    |
## 示例
```bullet
headers := {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36"
}
data := "{\"name\":\"Bullet\"}"
rsp := npost( "https://www.baidu.com/", headers=headers, data=data )

println( rsp['url'] )
println( rsp['html'] )
```