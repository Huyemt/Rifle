# [返回教程](README.md)
***
# 介绍
`Bullet`设计初衷出来的目的是让`Rifle`模块的编写变得更加容易上手。
# 示例
## 简单的模块
一个简单的模块，必须拥有以下[程序属性](attribute.md):
1. name （名称）
2. version （版本号）
3. authors （作者列表）
4. canBeSelect （是否可选用）

```bullet
# name = "MyFirstBtModule"
# version = "1.0.0"
# authors = ["Huyemt", "Teaclon", "Kevin"]
# canBeSelect = true

func onLoad {
    println("已加载本模块")
}

func onDisable {
    println("已卸载本模块")
}

func onSelected {
    println("已选用本模块")
}

func onQuit {
    println("已取消选用本模块")
}
```

## 指令实现
指令实现需要开发者添加`commands`程序属性。
<br>
其类型为`字典`，格式如下:
```bullet
{ "指令名称": ["实现功能的函数名称", "指令介绍", ["指令用法1", "指令用法2", ... ]], ... }
```
具体实现:
```bullet
# name = "MyFirstBtModule"
# version = "1.0.0"
# authors = ["Huyemt", "Teaclon", "Kevin"]
# canBeSelect = true
# commands = {
    "test": ["cmd_test", "测试指令", ["test", "test <args...>"]]
}

func onLoad {
    println("已加载本模块")
}

func onDisable {
    println("已卸载本模块")
}

func onSelected {
    println("已选用本模块")
}

func onQuit {
    println("已取消选用本模块")
}

// 实现指令的功能
// args这个名字可以随便更换，不过必须要有参数作为数据入口，它属于列表
func cmd_test( args ) {
    if ( len( args ) == 0 )
        println( "调试Test" )
    else
        println( "调试Test -> " + str(args) )
}
```

## 更多属性
1. name （名称 -> 字符串）
2. version （版本号 -> 字符串）
3. authors （作者列表 -> 列表或者字符串）
4. canBeSelect （是否可选用 -> 布尔值）
5. website （相关网址 -> 字符串）
6. description （模块简介 -> 字符串）