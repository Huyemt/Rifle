## JDK Version
Make sure that your JDK environment is `JDK 11` or above before use. Recommended `adopt-openj9-11.0.11`
## Module using
1. Please put the modules you want in the `modules` folder under the root directory of <kbd>Rifle</kbd>.
2. Start <kbd>Rifle</kbd> and use the `use` command to use the specified module.
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
  * View command help
      ```
      help -cmd <commandName1> <commandName2>... 
      ```
      ```
      help <commandName1> <commandName2>...
      ```
* `cls`<br>
  * Clear the console screen
* `exit`<br>
  * Exit <kbd>Rifle</kbd> program
* `ml`<br>
  * View the information of modules that have been loaded by <kbd>Rifle</kbd>
      ```
      ml
      ```
      ```
      ml <moduleName1> <moduleName2>...
      ```
* `mhelp`<br>
  * View the commands of the module
      ```
      mhelp
      ```
      ```
      mhelp <moduleName>
      ```
* `use`<br>
  * Use a module<br>
    When we use a module, <kbd>Rifle</kbd> will refer to the command of the module
      ```
      use <moduleName>
      ```
* `quit`<br>
  * Exit the currently used module<br>When we exit the corresponding module, we can't use the command of the module
* `tl`<br>
  * View the list of running task ids
* `task`<br>
  * Run a command as a task in the background<br>This action will send the task to the task ID list
      ```
      task <command>
      ```
* `tkill`<br>
  * Cancel the task by the task ID
      ```
      tkill <taskId1> <taskId2>...
      ```
## Quick operation
1. When you are using a module, if you want to exit the module quickly, you can use <kbd>Ctrl + C</kbd> to exit.
2. When you want to complete a command quickly, you can use the key <kbd>Tab</kbd> for intelligent command completion.