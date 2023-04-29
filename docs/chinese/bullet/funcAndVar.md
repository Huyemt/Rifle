## [返回教程](README.md)
***
## 前言
每一种编程语言都应该支持`函数`与`变量`，这些东西在高级语言中的地位与化学中的原子一样重要。编程语言结构的本质其实是许许多多的函数与变量拼凑而成。
## 介绍
在`Bullet`语言中，我们将`func`作为函数声明的关键字，`var`作为变量声明的关键字。
<br>
而且我们拥有像`Golang`一样的变量推导式
<br>
```bullet
var i = 10
```
等效于
<br>

```bullet
i := 10
```
## 变量
字符串
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

列表
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
```
<br>

字典
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
```