# [返回教程](../README.md)
# [返回内置函数目录](../builtFunc.md)
***

# bytes
## 介绍
`字符串`转`字节字符串`
## 参数
| 参数       | 参数类型     | 描述              | 是否选填 |
|:---------|:---------|:----------------|:-----|
| obj      | 数字，字符串   | 需要转为`字节字符串`的值   |      |
| encoding | 字符串，Null | 编码名称（默认`utf-8`） | √    |
## 返回值
`字节字符串`
## 示例
```bullet
obj := "Hello, World"

// b'\x48\x65\x6c\x6c\x6f\x2c\x20\x57\x6f\x72\x6c\x64'
println( bytes( obj ) )
```

# str
## 介绍
`任意类型`转`字符串`
## 参数
| 参数       | 参数类型     | 描述              | 是否选填 |
|:---------|:---------|:----------------|:-----|
| obj      | 任意类型     | 需要转为`字符串`的值     |      |
| encoding | 字符串，Null | 编码名称（默认`utf-8`） | √    |
## 返回值
`字符串`
## 示例
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
## 介绍
`字符串`转`数字`
## 参数
| 参数       | 参数类型   | 描述         | 是否选填 |
|:---------|:-------|:-----------|:-----|
| obj      | 数字，字符串 | 需要转为`数字`的值 |      |
## 返回值
`数字`
## 示例
```bullet
// 1111111.10086
println( num( "000001111111.10086000000" ) )
```

# isbstr
## 介绍
判断值是否为`字节字符串`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
```bullet
// false
println( isbstr( "Bullet" ) )

// true
println( isbstr( "\x5A" ) )

// false
println( isbstr( 10086 ) )
```

# isbyte
## 介绍
_注意：`字节字符串`也属于`字节`_

判断值是否为`字节`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
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
## 介绍
判断值是否为`字符串`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
```bullet
// true
println( isstr( "Bullet" ) )

// false
println( isstr( "\x5A\x5A" ) )

// false
println( isstr( 10086 ) )
```

# isnum
## 介绍
判断值是否为`数字`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
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
## 介绍
判断值是否为`列表`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
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
## 介绍
判断值是否为`字典`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
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
## 介绍
判断值是否为`null`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
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
## 介绍
判断值是否为`布尔值`
## 参数
| 参数       | 参数类型     | 描述     | 是否选填 |
|:---------|:---------|:-------|:-----|
| obj      | 任意类型     | 需要判断的值 |      |
## 返回值
`布尔值`
## 示例
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