## 终端实现
<kbd>Rifle</kbd>采用<kbd>[JLine3](https://github.com/jline/jline3)</kbd>实现终端。
<br>
在<kbd>Rifle</kbd>做好所有准备工作之后，命令监听线程开始工作。
## 命令执行
<kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>作为存放<kbd>[Command](../../src/main/java/org/rifle/command/Command.java)</kbd>的容器，自然也可以对命令进行调用。
<br><br>
我们为每个<kbd>[Module](../../src/main/java/org/rifle/module/Module.java)</kbd>都分配了一个<kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>。但并这不代表用户在选中任何模块时都可以执行<kbd>[Module](../../src/main/java/org/rifle/module/Module.java)</kbd>拥有的<kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>的命令。<span style="color:red">只有在用户选中当前模块时，才能使用该模块的命令。</span>
<br><br>
若您想让您自定义的模块全局化，您可以通过下面的方法将<kbd>[Command](../../src/main/java/org/rifle/command/Command.java)</kbd>注册到<kbd>Rifle</kbd>的<kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>之中：
```
Rifle.getInstance().getCommandManager().register();
```
<br><br>
普通的模块在每时每刻都不应该将<kbd>[Command](../../src/main/java/org/rifle/command/Command.java)</kbd>注册到<kbd>Rifle</kbd>中的<kbd>[CommandManager](../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>。
## 任务调度
<kbd>[TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java)</kbd>作为存放<kbd>[Task](../../src/main/java/org/rifle/scheduler/Task.java)</kbd>的容器，自然也可以对任务进行增删查（没有改）功能。 
<br><br>
每个被添加到<kbd>[TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java)</kbd>的<kbd>[Task](../../src/main/java/org/rifle/scheduler/Task.java)</kbd>都会分配一个任务ID。
<br><br>
下面是添加一条<kbd>[Task](../../src/main/java/org/rifle/scheduler/Task.java)</kbd>到<kbd>[TaskManager](../../src/main/java/org/rifle/manager/TaskManager.java)</kbd>的例子
```java
import org.rifle.Rifle;
import org.rifle.module.Module;
import org.rifle.scheduler.Task;

public class YourModule extends Module {
    // 省略部分代码...

    @Override
    public void onLoad() {

        /**
         * 写法一
         */
        Task task = new Task() {
            @Override
            public void run() {
                // 需要执行的代码
            }
        };
        // 只有在 addTask() 之后，Task才会被分配任务ID
        Rifle.getInstance().getScheduler().addTask(task);

        /**
         * 写法二
         */
        Rifle.getInstance().getScheduler().addTask(new Task() {
            @Override
            public void run() {
                // 需要执行的代码
            }
        });

        /**
         * 写法三
         * 
         * 您可以继承 Task 类，改写里面的 run() 方法，再参考上面的写法将任务添加到 TaskManager
         */
        
    }
}
```
更加具体的使用方法，您可以阅读[TaskManager源代码](../../src/main/java/org/rifle/manager/TaskManager.java)，里面符较为详细的注释。
## 日志输出
<kbd>Rifle</kbd>中的日志输出类被设定为<kbd>[MainLogger](../../src/main/java/org/rifle/console/logger/MainLogger.java)</kbd>。
<br>
而<kbd>[Module](../../src/main/java/org/rifle/module/Module.java)</kbd>的日志输出类被设定为<kbd>[ModuleLogger](../../src/main/java/org/rifle/console/logger/ModuleLogger.java)</kbd>。
<br><br>
模块的日志输出例子:

```java
import org.rifle.module.Module;

public class YourModule extends Module {
    // 省略部分代码...
    
    @Override
    public void onLoad() {
        getLogger().info("正常输出");
        getLogger().warning("警告输出");
        getLogger().error("致命错误输出");
        getLogger().println("无前缀输出");
    }
}
```
## 模块管理
<kbd>Rifle</kbd>将<kbd>[ModuleManager](../../src/main/java/org/rifle/manager/ModuleManager.java)</kbd>作为存放<kbd>[Module](../../src/main/java/org/rifle/module/Module.java)</kbd>的容器。
<br>
具体操作请阅读[ModuleManager源代码](../../src/main/java/org/rifle/manager/ModuleManager.java)。