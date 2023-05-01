## [返回教程](README.md)
***
## 基本类型
1. [x] [Number](#Number)
2. [x] [String](#String)
3. [x] [Boolean](#Boolean)
4. [x] [List](#List)
5. [x] [Dictionary](#Dictionary)
6. [x] [Byte](#Byte)
7. [x] [ByteString](#ByteString)
***
### <a id="Number">Number</a>
`数字型`在`Bullet`语言中属于基本类型，它能够自动计算高精度。

_注意：整形与浮点型一并列为数字型_

```bullet
// 0.3
println(0.1 + 0.2)

// 100
println(50 + 50)

// 100.5
println(100 + 0.5)

// 21365151767491096751669875171068924
println(112895471982547 * 189247198247189471892)

// 35552161055436443755508084240274132793089385439178238514467957508007844000544076747395716898238059499948047971683174020714017399142985160795716787474869523888793381177257875338657581884723240816721344981881645879755708642174837032776956529001159752127860003806642176
println(18947124124512 ** 20)
```
***
### <a id="String">String</a>
`字符串型`在`Bullet`语言中属于基本类型，使用者可以非常灵活地操作它。

_注意1：复杂索引的两个值不能为负数_

_注意2：复杂索引左值大于右值时，即反转操作_

```bullet
// Hello, World
println("Hello, World")

// Hello
println("Hello, World"[0:5])

// He
println("Hello, World"[0:5][0:2])

aStr := "Hello, World"

// Hello
println(aStr[0:5])

// World
println(aStr[7:])

// 反转
// dlroW ,olleH
println(aStr[len(aStr):])

// olleH
println(aStr[5:])
```
***
### <a id="Boolean">Boolean</a>
`布尔型`在`Bullet`语言中属于基本类型，是控制`选择结构`与`循环结构`执行的决策者。

_注意：在Bullet语言中，`&&`等价于`and`，`||`等价于`or`_

```bullet
// true
println(true)

// false
println(false)

// 逻辑与
// false
println(true && false)
println(true and false)

// true
println(false && false)
println(false and false)

// 逻辑或
// true
println(true || false)
println(true or false)

// false
println(false || false)
println(false or false)
```
***
### <a id="List">List</a>
`列表`在`Bullet`语言中属于基本类型，它以数字为索引，可以容纳不同类型的值。

_注意1：`列表`的索引使用规则与`字符串`相同_

_注意2：直接使用`[]`可以直接取得最后一项的值_

```bullet
// [2,3]
println([0, 1, 2, 3, 4][2:4])

var aList = [0, 1, 2, 3, 4]

// [0,1,2,3,4]
println(aList)

// 取最后一项
// 4
println(aList[])

// [0,1,2]
println(aList[:3])

// 反转
// [4,3,2]
println(aList[5:2])

aList[] = 5

// [0,1,2,3,4,5]
println(aList)

// 追加数据
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
***
### <a id="Dictionary">Dictionary</a>
`字典`在`Bullet`语言中属于基本类型，它以`key-value`的格式存储数据。

_注意1：`字典`的键必须是`字符串型`，且访问也以`字符串型`为索引。_

_注意2：直接使用`[]`可以直接取得最后一项的值_

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
### <a id="Byte">Byte</a>
### <a id="ByteString">ByteString</a>
`Byte`与`ByteString`在`Bullet`语言中都属于基本类型，其中`ByteString`是`Byte`的集合体。

_注意：`ByteString`也可以用数字索引访问值，但不可赋值。_

```bullet
byteStr := "\x48\x65\x6c\x6c\x6f\x2c\x20\x57\x6f\x72\x6c\x64"

// b'\x48\x65\x6c\x6c\x6f\x2c\x20\x57\x6f\x72\x6c\x64'
println(byteStr)

// Hello, World
println(str(byteStr))

// 12
println(len(byteStr))

// \x65
println(byteStr[1])

// e
println(str(byteStr[1]))

// true
println( isbyte(byteStr[1]) )

// false
println( isbstr(byteStr[1]) )

// true
println( isbyte(byteStr) )

// true
println( isbstr(byteStr) )
```