package org.rifle.scheduler;

import org.rifle.utils.Utils;

/**
 * @author Huyemt
 */

public class Run4Task extends Thread {
    private final Task task;
    private String tid;


    public Run4Task(Task task) {
        this.task = task;

        tid = Utils.randomID(6, false);
        while (task.getTaskManager().exists(tid))
            tid = Utils.randomID(6, false);
    }

    public final String getTaskId() {
        return tid;
    }

    @Override
    public final void run() {
        task.run();
    }
}
