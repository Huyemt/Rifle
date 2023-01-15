package rifle.task;

import rifle.Rifle;
import rifle.utils.TextFormat;

import java.util.*;

/**
 * @author Huyemt
 */

public class TaskMap {
    private final HashMap<String, Task> taskHashMap = new HashMap<>();
    private final Thread check;

    public TaskMap() {
        check = new Thread(() -> {
            while (Rifle.getInstance().getConsoleThread().isAlive()) {
                if (taskHashMap.size() > 0) {
                    synchronized (taskHashMap) {
                        Collection<Task> collection = taskHashMap.values();
                        for (Task task : collection) {
                            if (!task.isAlive())
                                removeTask(task.getTaskID());
                        }
                    }
                }
            }
        });
    }

    public final void startCheck() {
        if (check.isAlive())
            return;
        check.start();
    }

    public final synchronized void addTask(Task task) {
        if (existsTask(task.getTaskID()))
            return;
        task.start();
        taskHashMap.put(task.getTaskID(), task);
    }

    public final synchronized void killTask(String tid) {
        if (!existsTask(tid))
            return;

        Task task = taskHashMap.get(tid);
        if (!task.isAlive())
            task.stop();
    }

    public final synchronized void removeTask(String tid) {
        if (!existsTask(tid))
            return;

        Task task = taskHashMap.get(tid);
        if (task.isAlive())
            return;
        taskHashMap.remove(task.getTaskID());
        Rifle.getInstance().getLogger().info(TextFormat.FONT_GREEN + "Task \"" + TextFormat.STYLE_BOLD + TextFormat.FONT_RED + task.getTaskID() + TextFormat.STYLE_RESET + TextFormat.FONT_GREEN + "\" was stopped.");
    }

    public final boolean existsTask(String tid) {
        return taskHashMap.containsKey(tid);
    }

    public final String randomTID() {
        StringBuilder r = new StringBuilder();
        Random random = new Random();

        while (r.length() < 6) {
            int ri = random.nextInt(10);
            if (ri == 0 && r.length() == 0)
                continue;
            r.append(ri);
        }

        return existsTask(r.toString()) ? randomTID() : r.toString();
    }

    public final Map<String, Task> getAllTasks() {
        return (HashMap<String, Task>) this.taskHashMap.clone();
    }
}
