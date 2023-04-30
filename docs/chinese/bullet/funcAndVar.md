## [返回教程](README.md)
***
## 前言
每一种编程语言都应该支持`函数`与`变量`，这些东西在高级语言中的地位与化学中的原子一样重要。编程语言结构的本质其实是许许多多的函数与变量拼凑而成。
## 介绍
在`Bullet`语言中，我们将`func`作为函数声明的关键字，`var`作为变量声明的关键字。
<br>
而且我们拥有像`Golang`一样的变量推导式，使得程序编写的过程中可以更为简洁。

## 变量
```bullet
var i = 10
```
等效于
<br>

```bullet
i := 10
```
<br>

### 变量声明演示
1. 字符串
```bullet
aStr := "Hello, world"

// Hello, world
println(aStr)

// Hello
println(aStr[:5])

// olleH
println(aStr[5:0])
```
<br>

2. 列表
```bullet
var aList = [0, 1, 2, 3, 4]

// [0,1,2,3,4]
println(aList)

// 取最后一项
// 4
println(aList[])

// [0,1,2]
println(aList[:3])

// [4,3,2]
println(aList[5:2])

aList[] = 5

// [0,1,2,3,4,5]
println(aList)

aList[] = []
aList[][] = 1
aList[][] = 2
aList[][] = 3

// [0,1,2,3,4,5,[1,2,3]]
println(aList)

// 自身赋值
aList[] = aList
// [0,1,2,3,4,5,[1,2,3],[0,1,2,3,4,5,[1,2,3]]]
println(aList)
```
<br>

3. 字典
```bullet
aDict := {
    "a": 1,
    "b": 1.1,
    "c": "str",
    "d": [1, 2, 3],
    "e": false,
    "f": {
        "a": 1
    }
}

// {"a":1,"b":1.1,"c":"str","d":[1,2,3],"e":false,"f":{"a":1}}
println(aDict)

// str
println(aDict["c"])

// 3
println(aDict["d"][])

// 1
println(aDict["f"]["a"])

// 取最后一项
// {"a":1}
println(aDict[])
```
## 函数
_注意：Bullet目前不支持嵌套函数的写法。_

在日常编写代码时，会经常遇到写不需要传入参数的函数。
<br>
```bullet
func voidFunction() {
    // statements...
}
```
等效于
```bullet
func voidFunction {
    // statements...
}
```
<br>

### 函数的四种类型

1. **无参数无返回值**
```bullet
func aFunc {
    println("Hello, world")
}

// 调用
aFunc()
```
<br>

2. **有参数无返回值**
```bullet
func aFunc( param ) {
    println(param)
}

// 调用
aFunc("Hello, world")
```
<br>

3. **无参数有返回值**
```bullet
aK := 1
func aFunc {
    aK += 1
    return aK
}

// 调用
println(aFunc())
```
<br>

4. **有参数有返回值**
```bullet
func aFunc( list ) {
    list[] = len(list)
    return list
}

// 调用
a := aFunc( [] )

// [0]
println(a)

aFunc(a)

// [0,1]
println(a)
```