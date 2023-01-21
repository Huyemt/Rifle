## Basic command specification
Sequence parameter:
```
commandName arg1 arg2
```
Keyword parameter:
```
commandName -key1 arg1 arg2 --key2 --key3
```
## Built in command
* `help`<br>
    View command help
    ```
    help -cmd <commandName1> <commandName2>... 
    ```
    ```
    help <commandName1> <commandName2>...
    ```
* `cls`<br>
  Clear the console screen
* `exit`<br>
  Exit <kbd>Rifle</kbd> program
* `ml`<br>
  View the information of modules that have been loaded by <kbd>Rifle</kbd>
    ```
    ml
    ```
    ```
    ml <moduleName1> <moduleName2>...
    ```
* `use`<br>
  Use a module<br>
  (When we use a module, <kbd>Rifle</kbd> will refer to the command of the module)
    ```
    use <moduleName>
    ```
* `quit`<br>
  Exit the currently used module<br>
  (When we exit the corresponding module, we can't use the command of the module)
* `tl`<br>
  View the list of running task ids
* `task`<br>
  Run a command as a task in the background<br>
  This action will send the task to the task ID list
    ```
    task <command>
    ```
* `tkill`<br>
  Cancel the task by the task ID
    ```
    tkill <taskId1> <taskId2>...
    ```
## Quick operation
1. When you are using a module, if you want to exit the module quickly, you can use <kbd>Ctrl + C</kbd> to exit.