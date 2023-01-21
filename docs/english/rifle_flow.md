## Terminal implementation
<kbd>Rifle</kbd> uses <kbd>[JLine3](https://github.com/jline/jline3)</kbd> to realize the terminal.
<br>
After <kbd>Rifle</kbd> has done all the preparatory work, command the listening thread to start working.
## Command execution
As a container for storing commands, <kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> can naturally call commands.
<br><br>
We assigned a <kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> to each <kbd>[Module](../../src/main/java/org/rifle/module/Module.java)</kbd>. However, this does not mean that the user can execute the <kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> command owned by the Module when selecting any module. <span style="color:red">Only when the user selects the current module can the commands of this module be used.</span>
<br><br>
If you want to make your customized module global, you can register the Command in the <kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> of <kbd>Rifle</kbd> by the following methods:
```
Rifle.getInstance().getCommandManager().register();
```
<br><br>
Ordinary modules should not register the <kbd>[Command](../../src/main/java/org/rifle/command/Command.java)</kbd> to the <kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> in <kbd>Rifle</kbd> at all times.
## Task scheduling
As a container for storing tasks, <kbd>[TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java)</kbd> can naturally add, delete and query tasks (without modification).
<br><br>
Each <kbd>[Task](../../src/main/java/org/rifle/scheduler/Task.java)</kbd> added to <kbd>[TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java)</kbd> is assigned a task ID.
<br><br>
The following is an example of adding a <kbd>[Task](../../src/main/java/org/rifle/scheduler/Task.java)</kbd> to <kbd>[TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java)</kbd>.
```java
import org.rifle.Rifle;
import org.rifle.module.Module;
import org.rifle.scheduler.Task;

public class YourModule extends Module {
    // Omit part of the code ...

    @Override
    public void onLoad() {

        /**
         * Method 1
         */
        Task task = new Task() {
            @Override
            public void run() {
                // Code to be executed
            }
        };
        // Only after addTask() will the Task be assigned a task ID.
        Rifle.getInstance().getScheduler().addTask(task);

        /**
         * Method 2
         */
        Rifle.getInstance().getScheduler().addTask(new Task() {
            @Override
            public void run() {
                // Code to be executed
            }
        });

        /**
         * Method 3
         * 
         * You can inherit the Task class, rewrite the run() method in it, and then refer to the above methods to add the task to TaskManager.
         */
        
    }
}
```
For more specific usage, you can read [the source code of TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java), which contains more detailed comments.
## Log output
The log output class in <kbd>Rifle</kbd> is set to <kbd>[MainLogger](../../src/main/java/org/rifle/console/logger/MainLogger.java)</kbd>.
<br>
The log output class of <kbd>[Module](../../src/main/java/org/rifle/module/Module.java)</kbd> is set to <kbd>[ModuleLogger](../../src/main/java/org/rifle/console/logger/ModuleLogger.java)</kbd>.
<br><br>
Example of log output of module:
```java
import org.rifle.module.Module;

public class YourModule extends Module {
    // Omit part of the code ...
    
    @Override
    public void onLoad() {
        getLogger().info("normaL");
        getLogger().warning("warning");
        getLogger().error("error");
        getLogger().println("no prefix");
    }
}
```
## Module management
Rifle uses <kbd>[ModuleManager](../../src/main/java/org/rifle/manager/ModuleManager.java)</kbd> as a container for storing modules.
<br>
Please read [the source code of ModuleManager](../../src/main/java/org/rifle/manager/ModuleManager.java) for specific operations.