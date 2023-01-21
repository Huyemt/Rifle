## 模块使用
1. 请将您想要的模块放到<kbd>Rifle</kbd>的根目录之下的`modules`文件夹里面。
2. 启动<kbd>Rifle</kbd>，使用 `use` 命令使用指定模块。
## 基本命令规范
顺序参数:
```
commandName arg1 arg2
```
关键字参数:
```
commandName -key1 arg1 arg2 --key2 --key3
```
更多格式还需根据该命令的帮助进行操作。
## 内置命令
* `help`<br>
  * 查看命令帮助
    ```
    help -cmd <commandName1> <commandName2>... 
    ```
    ```
    help <commandName1> <commandName2>...
    ```
* `cls`<br>
  * 清屏
* `exit`<br>
  * 退出<kbd>Rifle</kbd>程序
* `ml`<br>
  * 查看已经被<kbd>Rifle</kbd>加载的模块信息
      ```
      ml
      ```
      ```
      ml <moduleName1> <moduleName2>...
      ```
* `use`<br>
  * 使用一个模块<br>
  当我们使用一个模块的时候，<kbd>Rifle</kbd>会引用该模块的命令
      ```
      use <moduleName>
      ```
* `quit`<br>
  * 退出当前使用的模块<br>
  当我们退出对应模块的时候，我们就无法使用该模块的命令
* `tl`<br>
  * 查看正在运行的任务ID列表
* `task`<br>
  * 将一条命令当作任务运行在后台<br>
  此操作会将任务送入任务ID列表
      ```
      task <command>
      ```
* `tkill`<br>
  * 通过任务ID取消该任务
    ```
    tkill <taskId1> <taskId2>...
    ```
## 快捷操作
1. 当您正在使用一个模块的时候，如果您想要快速退出该模块，您可以使用按键<kbd>Ctrl + C</kbd>退出。