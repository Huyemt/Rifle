## What is Rifle?
<kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd> is a console-like program written based on <kbd>Java</kbd>.<br>

It realizes the corresponding function in the form of <kbd>[Command](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/Command.java)</kbd>. The supported command parameter types are: <kbd>[OrderArguments](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/others/OrderArguments.java)</kbd>, <kbd>[KeyArguments](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/others/KeyArguments.java)</kbd>. At the same time, it has the function of <kbd>[Module](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/Module.java)</kbd> loading, and can load modules written by users or other developers, so it is highly extensible.<br>

At the same time, it allows you to suspend a command as a task to be executed in the task list, and you can continue with other command operations.
## What is its workflow like?
When the <kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd> object is instantiated, run the internal method:
> * ```load()```
>   * Instantiating <kbd>[TaskMap](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/TaskMap.java)</kbd> is used to store <kbd>[Task](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/Task.java)</kbd>.
>   * Instantiating <kbd>[MainLogger](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/utils/MainLogger.java)</kbd> for the log output of <kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>.
>   * Instantiating <kbd>[CommandMap](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/CommandMap.java)</kbd> is used to store the <kbd>[Command](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/command/Command.java)</kbd> of <kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>.
>   * Instantiating <kbd>[ModuleManager](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/module/ModuleManager.java)</kbd> is used to store modules that have been loaded by <kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>.
>   * Executing the method ```initDataFiles()```
>       * Detect whether the folder <kbd>modules</kbd> already exists, and create it if it does not exist.
>   * Executing the method ```initMainCommands()``` to register the main command of <kbd>[Rifle](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/Rifle.java)</kbd>.
> * ```run()```
>   * Instantiating <kbd>[ConsoleThread](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/threads/ConsoleThread.java)</kbd> thread, which is used to listen to the commands input by the user.
>   * Executing the method ```getTaskMap().startCheck()``` to start the survival detection of the tasks stored in <kbd>[TaskMap](https://github.com/Huyemt/Rifle/blob/main/src/main/java/rifle/task/TaskMap.java)</kbd>, and the tasks whose life cycle ends will be removed.
## What is the specification for using commands?
Sequential parameter:<br>
```commandline
test a b c
```
Keyword parameter:<br>
```commandline
test -a a -b b b -c -d
```
## Development document
[Click me](https://github.com/Huyemt/Rifle/tree/main/docs)
## What can it do?
You can use it as a tool for processing daily data, or you can make it a useful tool for your work field. The use effect is almost up to you!
## Will it be updated continuously?
Theoretically, I will keep updating this project.<br>

But I am now a sophomore in high school, so for the next year, Rifle will only be updated occasionally during the holidays.<br>

Thank you for your support of this project.
***
<p align="right">Thank you for your support of Rifle.<br>By <a href="https://github.com/Huyemt">Huyemt</a><br>Edited on January 14th, 2023</p>