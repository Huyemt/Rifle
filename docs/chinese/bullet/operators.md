# [返回教程](README.md)
***
# 运算符的优先级
| 类别  | 运算符                        | 顺序   |
|:----|:---------------------------|:-----|
| 后缀  | ()                         |      |
| 幂   | **                         |      |
| 一元  | +, -, !                    | 从右到左 |
| 乘除  | *, /, %                    | 从左到右 |
| 加减  | +, -                       | 从左到右 |
| 关系  | <, <=, >, >=               | 从左到右 |
| 相等  | ==, !=                     | 从左到右 |
| 逻辑与 | &&                         |      |
| 逻辑或 | &#124;&#124;               |      |
| 赋值  | =, +=. -=. *=, /=, %=, **= | 从右到左 |
| 逗号  | ,                          |      |
***
# 算术运算符
| 运算符 | 描述  | 用法     |
|:----|:----|:-------|
| +   | 相加  | A + B  |
| -   | 相减  | A - B  |
| *   | 相乘  | A * B  |
| /   | 相除  | A / B  |
| **  | 幂   | A ** B |
| %   | 求余  | A % B  |

## 数字示例
```bullet
A := 90
B := 10

// 100
println( A + B )

// 80
println( A - B )

// 900
println( A * B )

// 9
println( A / B )

// 34867844010000000000
println( A ** B )

// 0
println( A % B )

// 80
println( A + -B )
```

## 字符串示例
_注意：字符串的算术运算符只支持`+`与`*`_

```bullet
A := "A"
B := "B"

// AB
println( A + B )

// AA
println( A * 2 )
```

# 关系运算符
_注意：关系运算符计算出的最终结果类型是布尔类型(Boolean)_

| 运算符 | 描述    | 用法     |
|:----|:------|:-------|
| ==  | 等于    | A == B |
| !=  | 不等于   | A != B |
| \>  | 大于    | A > B  |
| <   | 小于    | A < B  |
| \>= | 大于或等于 | A >= B |
| <=  | 小于或等于 | A <= B |

## 数字示例
```bullet
A := 90
B := 10

// false
println( A == B )

// true
println( A != B )

// true
println( A > B )

// false
println( A < B )

// true
println( A >= B )

// false
println( A <= B )
```

## 字符串示例
_注意：字符串的关系运算符只支持`==`与`!=`_

```bullet
A := "A"
B := "B"

// false
println( A == B )

// true
println( A != B )
```

## 列表示例
_注意：列表的关系运算符只支持`==`与`!=`_

```bullet
A := []
B := [1]

// false
println( A == B )

// true
println( A != B )
```

## 字典示例
_注意：字典的关系运算符只支持`==`与`!=`_

```bullet
A := {"a":1}
B := {"a":2}

// false
println( A == B )

// true
println( A != B )
```

## 混合示例
```bullet
A := []
B := {"B":1}

// false
println( A == B )

// true
println( A != B )
```

# 逻辑运算符
_注意：逻辑运算符计算出的最终结果类型是布尔类型(Boolean)_

| 运算符          | 描述       | 用法               |
|:-------------|:---------|:-----------------|
| &&           | 逻辑与      | A && B           |
| &#124;&#124; | 逻辑或      | A &#124;&#124; B |
| !            | 逻辑非      | !(A && B)        |

`&&` 可替换为 `and`
<br>
`||` 可替换为 `or`
<br>
`!` 可替换为 `not`
```bullet
A := true
B := false

// false
println( A and B )
println( A && B )

// true
println( A or B )
println( A || B )

// false
println( !A )
println( not A )

// true
println( !(A && B) )
println( not (A && B) )
```

# 赋值运算符
_注意：赋值运算符中的运算与算术运算符的法则一致_

| 运算符 | 描述       | 用法      |
|:----|:---------|:--------|
| =   | 为已声明变量赋值 | A = B   |
| :=  | 声明变量并赋值  | A := B  |
| +=  | 加赋值      | A += B  |
| -=  | 减赋值      | A -= B  |
| *=  | 乘赋值      | A *= B  |
| /=  | 除赋值      | A /= B  |
| **= | 幂赋值      | A **= B |
| %=  | 模赋值      | A %= B  |

## 细节
```bullet
A := 10
```
等同于
```bullet
var A = 10
```

## 示例
```bullet
// 声明变量
A := 1

// 11
A += 10
println( A )

// 1
A -= 10
println( A )

// 10
A *= 10
println( A )

// 1
A /= 10
println( A )

// 1
A **= 10
println( A )

// 1
A %= 10
println( A )
```

## 进阶示例
```bullet
// 4
A := 1
A += A += A += 1
println( A )
```