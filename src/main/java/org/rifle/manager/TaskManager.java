package org.rifle.manager;

import org.rifle.Rifle;
import org.rifle.scheduler.Task;
import org.rifle.utils.TextFormat;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Huyemt
 */

public class TaskManager {
    private final ConcurrentHashMap<String, Task> tasks;

    public TaskManager() {
        tasks = new ConcurrentHashMap<>();
        new Thread(() -> {
            while (Rifle.getInstance().getConsole().isRunning()) {
                synchronized (tasks) {
                    for (Task task : tasks.values()) {
                        if (!task.isRunning()) {
                            remove(task.getTaskId());
                            Rifle.getInstance().getLogger().info("The task with ID `" + TextFormat.STYLE_BOLD + TextFormat.FONT_BLUE + task.getTaskId() + TextFormat.RESET + "` has finished its work.");
                        }
                    }
                }
            }
            for (Task task : tasks.values())
                task.cancel();
        }).start();
    }

    public final boolean exists(String tid) {
        return tasks.containsKey(tid);
    }

    public synchronized final boolean addTask(Task task) {
        if (task == null || task.isRunning())
            return false;

        if (task.getTaskId() != null && exists(task.getTaskId()))
            return false;

        task.init(this);
        task.start();
        tasks.put(task.getTaskId(), task);
        return task.isRunning();
    }

    public synchronized final boolean cancelTask(String tid) {
        if (!exists(tid))
            return false;

        tasks.get(tid).cancel();
        return true;
    }

    public synchronized final void remove(String tid) {
        tasks.remove(tid);
    }

    public final String[] getTaskIds() {
        return tasks.keySet().toArray(String[]::new);
    }
}
