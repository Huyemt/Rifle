package org.rifle.command;

import org.jline.reader.Candidate;
import org.rifle.command.arguments.Argument;

import java.util.List;

/**
 * @author Huyemt
 */

public abstract class Command {
    private final String name;
    private final String description;
    private final String[] usages;
    private final boolean canBeTask;


    public Command(String name, String description, String[] usages, boolean canBeTask) {
        this.name = name;
        this.description = description;
        this.usages = usages;
        this.canBeTask = canBeTask;
    }

    public Command(String name, String description, String[] usages) {
        this(name, description, usages, false);
    }

    /**
     * 执行该命令
     *
     * Executing this command
     */
    public abstract void execute(Argument argument);


    public String[] complete(String reference, String[] args) {
        return new String[0];
    }

    /**
     * 获取指令的名称
     *
     * Get the name of the command.
     *
     * @return String
     */
    public final String getName() {
        return name;
    }

    /**
     * 获取指令的用法
     *
     * Get the usage of the command.
     *
     * @return String[]
     */
    public final String[] getUsages() {
        return usages;
    }

    /**
     * 获取指令的说明
     *
     * Get the description of the command.
     *
     * @return String
     */
    public final String getDescription() {
        return description;
    }

    /**
     * 是否可被当作任务挂起执行
     *
     * Gets whether this command can be suspended as a task.
     *
     * @return boolean
     */
    public final boolean isCanBeTask() {
        return canBeTask;
    }
}
