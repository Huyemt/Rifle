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
                            Rifle.getInstance().getLogger().println("The task with ID `" + TextFormat.STYLE_BOLD + TextFormat.FONT_BLUE + task.getTaskId() + TextFormat.RESET + "` has finished its work.");
                        }
                    }
                }
            }
            for (Task task : tasks.values())
                task.cancel();
        }).start();
    }

    /**
     * 通过任务id查询任务是否存在
     *
     * Query whether a task exists by task id
     *
     * @param tid
     * @return boolean
     */
    public final boolean exists(String tid) {
        return tasks.containsKey(tid);
    }

    /**
     * 添加一条任务
     *
     * Add a task
     *
     * @param task
     * @return boolean
     */
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

    /**
     * 取消一个任务
     *
     * Cancel a task
     *
     * @param tid
     * @return
     */
    public synchronized final boolean cancelTask(String tid) {
        if (!exists(tid))
            return false;

        tasks.get(tid).cancel();
        return !exists(tid);
    }

    public synchronized final void remove(String tid) {
        tasks.remove(tid);
    }

    public final String[] getTaskIds() {
        return tasks.keySet().toArray(String[]::new);
    }
}
