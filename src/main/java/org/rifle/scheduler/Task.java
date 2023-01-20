package org.rifle.scheduler;

import org.rifle.manager.TaskManager;

/**
 * @author Huyemt
 */

public abstract class Task {
    private TaskManager from;
    private Run4Task run4Task = null;

    public abstract void run();

    public final void init(TaskManager from) {
        this.from = from;
        run4Task = new Run4Task(this);
    }

    public final void start() {
        if (isRunning())
            return;

        run4Task.start();
    }

    public final boolean cancel() {
        run4Task.stop();
        return !isRunning();
    }

    public final String getTaskId() {
        return run4Task == null ? null : run4Task.getTaskId();
    }

    public final boolean isRunning() {
        return run4Task != null && run4Task.isAlive();
    }

    public final TaskManager getTaskManager() {
        return from;
    }
}
