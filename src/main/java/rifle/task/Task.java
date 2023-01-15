package rifle.task;

import rifle.Rifle;

/**
 * @author Huyemt
 */

public class Task extends Thread {
    private final String tid;

    public Task() {
        tid = Rifle.getInstance().getTaskMap().randomTID();
    }

    public final String getTaskID() {
        return tid;
    }

    @Override
    public void run() {

    }

    @Override
    public synchronized final void start() {
        super.start();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        Rifle.getInstance().getTaskMap().removeTask(tid);
    }
}
