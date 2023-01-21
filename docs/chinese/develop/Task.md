## [返回主开发文档](../start.md)
***
## 例子
```java
import org.rifle.Rifle;
import org.rifle.module.Module;
import org.rifle.scheduler.Task;

public class YourModule extends Module {
    private static YourModule instance;

    public YourModule() {
        instance = this;
    }

    public static YourModule getInstance() {
        return instance;
    }

    // 省略部分代码...


    @Override
    public void onLoad() {
        Task task = new SimpleTask();
        Rifle.getInstance().getScheduler().addTask(task);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ignore
        }
        
        
        
        // 取消任务
        
        /**
         * 方法一
         */
        task.cancel();

        /**
         * 方法二
         */
        Rifle.getInstance().getScheduler().cancelTask(task.getTaskId());
    }
}

class SimpleTask extends Task {
    @Override
    public void run() {
        while (true) {
            YourModule.getInstance().getLogger().info("Threading...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
```
## 理念
1. 一个自定义任务必须继承<kbd>[Task](../../../src/main/java/org/rifle/scheduler/Task.java)</kbd>类，重写 `run()` 方法。
2. 一个<kbd>[Task](../../../src/main/java/org/rifle/scheduler/Task.java)</kbd>只有使用<kbd>[TaskManager](../../../src/main/java/org/rifle/manager/TaskManager.java)</kbd>的`addTask()`方法才会被分配任务ID。