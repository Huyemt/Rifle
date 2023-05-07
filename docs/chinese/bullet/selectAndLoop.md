# [返回教程](README.md)
***
# 选择结构
`选择结构`又叫`判断体`，由`if`和`else`关键字拼接而成，可以根据条件执行不同的代码块。
## 简单组成
```bullet
A := 10

// 如果 A 的值等于 10
if ( A == 10 ) {
    // 打印 A == 10
    println( "A == 10" )
} else {
    // 否则打印 A != 10
    println( "A != 10" )
}
```
## 同体多选择
```bullet
A := 10

// 如果 A 的值等于 10
if ( A > 10 ) {
    // 打印 A > 10
    println( "A > 10" )
} else if ( A != 10 ) {
    // 否则如果A != 10

    // 打印 A != 10
    println( "A != 10" )
} else {
    // 否则

    // 打印 A 的值
    println( "A == " + str(A) )
}

// 输出： A == 10
```
## 嵌套选择
```bullet
A := 10

if ( A > 10 ) {
    if ( A < 12 )
        println("10 < A < 12")
} else {
    if ( A > 0 )
        println("0 < A < 10")
}

// 输出：0 < A < 10
```
# 循环结构
`循环结构`是计算机编程必不可少的结构。
<br><br>
`break`关键字用于`终止循环`
<br>
`continue`关键字用于`跳过当前循环`
## until
`Bullet`弃用了传统的`while`循环体，改用`until`循环体代替，这使得代码能够更加容易地被开发者阅读。
<br>
<br>
`until`循环体的工作是使`目的`达成。
<br><br>
`else`体被执行的前提是，循环头必须被执行过一次以上
### 有限循环
```bullet
A := 0

until ( A == 10 ) {
    A += 1
} else {
    // 10
    println("done")
}

// 10
println( A )
```
### 无限循环
```bullet
until ( false ) {
    println( "looping..." )
}
```
## for
`else`体被执行的前提是，循环头必须被执行过一次以上
### 有限循环
```bullet
A := 5

for ( i := 0; i < A; i += 1 ) {
    println( i )
} else {
    println("done")
}

// 0
// 1
// 2
// 3
// 4
// done
```
### 无限循环
```bullet
for (; true; ) {
    println( "looping..." )
}
```