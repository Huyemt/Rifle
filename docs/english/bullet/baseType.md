# [Return to the tutorial](README.md)
***
# Base Type
1. [x] [Number](#Number)
2. [x] [String](#String)
3. [x] [Boolean](#Boolean)
4. [x] [List](#List)
5. [x] [Dictionary](#Dictionary)
6. [x] [Byte](#Byte)
7. [x] [ByteString](#ByteString)
***
## <a id="Number">Number</a>
Digital type is a basic type in Bullet language, which can automatically calculate high precision.

_Note: plastic and floating-point are juxtaposed as digital fonts_

```bullet
// 0.3
println(0.1 + 0.2)

// 100
println(50 + 50)

// 100.5
println(100 + 0.5)

// 0
println(10 % 2)

// 0
println(10 % 2 * 5)

// 9624
println(19248 / 2)

// 0.0001311943374981
println(1289375492183759821375982 / 9827981273982137598213579281)

// 10
println(10 % 2 ** 5)

// 21365151767491096751669875171068924
println(112895471982547 * 189247198247189471892)

// 35552161055436443755508084240274132793089385439178238514467957508007844000544076747395716898238059499948047971683174020714017399142985160795716787474869523888793381177257875338657581884723240816721344981881645879755708642174837032776956529001159752127860003806642176
println(18947124124512 ** 20)

// The problem of unreasonable decimals caused by division of two numbers
a := 1 / 3

// 0.33333333333333333333
println( a )

// Finally, the molecular value is calculated
// 1
println( a * 3 )
```
***
## <a id="String">String</a>
String type is a basic type in Bullet language, and users can operate it very flexibly.

_Note 1: Two values of a complex index cannot be negative_

_Note 2: When the left value of a complex index is greater than the right value, the operation is reversed_

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

// Reversed
// dlroW ,olleH
println(aStr[len(aStr):])

// olleH
println(aStr[5:])
```
***
## <a id="Boolean">Boolean</a>
Boolean type belongs to the basic type in Bullet language, and it is the decision maker who controls the implementation of selection structure and' circulation structure.

_Note: in Bullet language, `&&` is equivalent to `and`, `||` is equivalent to `or`_

```bullet
// true
println(true)

// false
println(false)

// false
println(true && false)
println(true and false)

// true
println(false && false)
println(false and false)

// true
println(true || false)
println(true or false)

// false
println(false || false)
println(false or false)
```
***
## <a id="List">List</a>
List belongs to the basic type in Bullet language, which is indexed by numbers and can accommodate different types of values.

_Note 1: The index usage rules of `List` are the same as those of `String`_

_Note 2：You can get the value of the last item directly by using `[]`_

```bullet
// [2,3]
println([0, 1, 2, 3, 4][2:4])

var aList = [0, 1, 2, 3, 4]

// [0,1,2,3,4]
println(aList)

// Take the last item
// 4
println(aList[])

// [0,1,2]
println(aList[:3])

// reverse
// [4,3,2]
println(aList[5:2])

aList[] = 5

// [0,1,2,3,4,5]
println(aList)

// Additional data
aList[] = []
aList[][] = 1
aList[][] = 2
aList[][] = 3

// [0,1,2,3,4,5,[1,2,3]]
println(aList)

// Self-assignment
aList[] = aList
// [0,1,2,3,4,5,[1,2,3],[0,1,2,3,4,5,[1,2,3]]]
println(aList)
```
***
## <a id="Dictionary">Dictionary</a>
Dictionary belongs to the basic type in Bullet language, and it stores data in the format of `key-value`.

_Note 1: The key of `Dictionary` must be `String`, and the access is also indexed by `String`_

_Note 2: You can directly get the value of the last item by using `[]`_

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

// Take the value of the last item
// {"a":1}
println(aDict[])

aDict[][] = len(aDict[])
aDict[][] = len(aDict[])
aDict[][] = len(aDict[])

// [0,1,2]
println(aDict[])
```
***
## <a id="Byte">Byte</a>
## <a id="ByteString">ByteString</a>
Both Byte and Bytestring belong to basic types in Bullet language, in which `ByteString` is a collection of `bytes`.

_Note: ByteString can also access the value with a numerical index, but it cannot be assigned_

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