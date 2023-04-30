## [返回教程](README.md)
***
## 前言
每一种编程语言都应该支持`函数`与`变量`，这些东西在高级语言中的地位与化学中的原子一样重要。编程语言结构的本质其实是许许多多的函数与变量拼凑而成。
***
## 介绍
在`Bullet`语言中，我们将`func`作为函数声明的关键字，`var`作为变量声明的关键字。
<br>
而且我们拥有像`Golang`一样的变量推导式，使得程序编写的过程中可以更为简洁。
***
## 变量
_注意：Bullet规定程序员对所有不存在的变量进行操作时，都需要使用下面的方法进行声明_

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
#### 字符串
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

#### 列表
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

#### 字典
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

// 取最后一项的值
// {"a":1}
println(aDict[])

aDict[][] = len(aDict[])
aDict[][] = len(aDict[])
aDict[][] = len(aDict[])

// [0,1,2]
println(aDict[])
```
***
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
#### 无参数无返回值
```bullet
func aFunc {
    println("Hello, world")
}

// 调用
aFunc()
```
<br>

#### 有参数无返回值
```bullet
func aFunc( param ) {
    println(param)
}

// 调用
aFunc("Hello, world")
```
<br>

#### 无参数有返回值
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

#### 有参数有返回值
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
#### 默认参数
```bullet
func aFunc( msg="Hello, World" ) {
    println( msg )
}

// 调用
// Hello, World
aFunc()

// Hi, Bullet
aFunc("Hi, Bullet")
```
### 高级用法
`Bullet`允许开发者在调用参数的时候，指定一个参数名称进行传参。
<br>
```bullet
func test(name, age=0, height=175) {
    println(name, age, height)
}

// 指定参数传参
// Bullet	0	175
test(name="Bullet")

// 指定参数按顺序传参
// Bullet	1	180
test(name="Bullet", age=1, height=180)

// 打乱指定参数的顺序
// Bullet	1	180
test(age=1, height=180, name="Bullet")

// 漏掉部分参数
// Bullet	0	180
test(name="Bullet", height=180)

// 直接传参
// Bullet	1	180
test("Bullet", 1, 180)

bullet := "Bullet"

// 传变量 与 计算
// Bullet	0	180
test(bullet, height=175+5)

// 自动复位传参
// Bullet	1	180
test("Bullet", height=180, 1) // name, height, age
```