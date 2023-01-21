package org.rifle.scheduler;

import org.rifle.Rifle;
import org.rifle.utils.TextFormat;
import org.rifle.utils.Utils;

/**
 * 将任务执行的放到线程之中运行，做到后台运行的效果
 *
 * Put the task execution into the thread to run, so as to achieve the effect of running in the background
 *
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
        try {
            task.run();
        } catch (Exception e) {
            Rifle.getInstance().getLogger().error("An error occurred in the task with ID `{}`: ".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + getTaskId() + TextFormat.RESET) + TextFormat.RESET + e.getMessage());
            task.cancel();
        }
    }
}
