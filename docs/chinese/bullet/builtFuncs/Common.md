# [返回教程](../README.md)
# [返回内置函数目录](../builtFunc.md)
***

# len
## 介绍
读取长度
## 参数
| 参数  | 参数类型 | 描述        | 是否选填 |
|:----|:-----|:----------|:-----|
| obj | 任意类型 | 需要读取长度的对象 |      |
## 返回值
`数字`
## 示例
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
## 介绍
自动换行输出
## 参数
变长参数，无准确参数值
## 返回值
`字符串`
## 示例
```bullet
// a
s := println( "a" )

// a    a
println( s, s )
```

# print
## 介绍
输出
## 参数
变长参数，无准确参数值
## 返回值
`字符串`
## 示例
```bullet
s := print( "a" )
print( s, s )

// 输出：aa    a
```