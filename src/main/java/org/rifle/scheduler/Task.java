package org.rifle.scheduler;

import org.rifle.manager.TaskManager;

/**
 * @author Huyemt
 */

public abstract class Task {
    private TaskManager from;
    private Run4Task run4Task = null;
    public boolean whenDiePrint = false;

    /**
     * 任务运行，我们需要把要做的工作实现到这个方法之中
     *
     * Task running, we need to realize the work to be done in this method
     */
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

    /**
     * 取消这个任务
     *
     * Cancel this task
     *
     * @return boolean
     */
    public final void cancel() {
        run4Task.stop();
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
