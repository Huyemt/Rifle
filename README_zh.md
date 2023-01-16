## 什么是Rifle？
<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>中文名为步枪，是一个基于<kbd>Java</kbd>编写的类控制台程序。<br>

它是以<kbd>[Command](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/Command.java)</kbd>(命令)的方式实现对应的功能。支持命令参数类型有：<kbd>[OrderArguments](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/others/OrderArguments.java)</kbd>(顺序参数)、<kbd>[KeyArguments](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/others/KeyArguments.java)</kbd>(关键字参数)。同时它具有<kbd>[Module](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/Module.java)</kbd>(模块)加载的功能，能够加载使用者或其他开发者编写的模块，拓展性较高。<br>

与此同时，它支持您将一条<kbd>[Command](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/Command.java)</kbd>当作任务挂起到任务列表中执行，您可以继续进行其他命令操作。
## 它的工作流程是什么样子的？
当<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>对象被实例化后，运行内部方法:<br>
> * ```load()```
>  * 实例化<kbd>[TaskMap](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/TaskMap.java)</kbd>用于存放<kbd>[Task](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/Task.java)</kbd>(任务)
>  * 实例化<kbd>[MainLogger](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/utils/MainLogger.java)</kbd>用于<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>的日志输出
>  * 实例化<kbd>[CommandMap](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/CommandMap.java)</kbd>用于存放<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>的<kbd>[Command](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/Command.java)</kbd>(命令)
>  * 实例化<kbd>[ModuleManager](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/ModuleManager.java)</kbd>用于存放已经被<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>加载的<kbd>[Module](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/Module.java)</kbd>(模块)
>  * 执行方法```initDataFiles()```
>    * 检测是否已经存在文件夹<kbd>modules</kbd>，不存在就创建它
>  * 执行方法```initMainCommands()```，注册<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>的主要命令
>    * 执行<kbd>[ModuleManager](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/ModuleManager.java)</kbd>的```loadModules()```方法，加载文件夹<kbd>modules</kbd>的所有<kbd>[Module](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/Module.java)</kbd>(模块)
> * ```run()```
>   * 实例化<kbd>[ConsoleThread](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/thrdeads/ConsoleThread.java)</kbd>线程，用于监听用户输入的命令
>   * 执行方法```getTaskMap().startCheck()```，开启对 <kbd>[TaskMap](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/TaskMap.java)</kbd>里面存放的<kbd>[Task](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/Task.java)</kbd>进行存活检测，生命周期结束的任务将被移除
## 使用命令的规范是怎么样的？
顺序参数的例子:<br>
```commandline
test a b c
```
关键字参数的例子:<br> 
```commandline
test -a a -b b b -c -d
```
## 它能干什么？
您可以把它当作一个处理日常数据用的工具，也可以把它打造成一个对您的工作领域有用的利器。使用效果几乎由您决定！
## 它会持续更新吗？
理论上来说，我会持续更新这个项目。<br>

但我现在已经是高二的学生了，所以接下来一年的时间里，<kbd>Rifle</kbd>只会在假期的时候偶尔更新。<br>

感谢您对此项目的支持。
***
<p align="right">感谢您对 Rifle 支持<br>By <a href="https://github.com/Huyemt">Huyemt</a><br>Edited on January 14th, 2023</p>