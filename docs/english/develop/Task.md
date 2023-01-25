## [Return to the main development manual](../start.md)
***
## Example
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

    // Omit part of the code...


    @Override
    public void onLoad() {
        Task task = new SimpleTask();
        Rifle.getInstance().getScheduler().addTask(task);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ignore
        }
        
        
        
        // Cancel the task
        
        /**
         * Method 1
         */
        task.cancel();

        /**
         * Method 2
         */
        Rifle.getInstance().getScheduler().cancelTask(task.getTaskId());
    }
}

class SimpleTask extends Task {
    @Override
    public void run() {
        
        // No log is generated when the task is complete
        this.whenDiePrint = false;
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
## Idea
1. A custom task must extend the <kbd>[Task](../../../src/main/java/org/rifle/scheduler/Task.java)</kbd> class and override the `run()` method.
2. A task can only be assigned a task ID by using the `addTask()` method of <kbd>[TaskManager](../../../src/main/java/org/rifle/manager/TaskManager.java)</kbd>.