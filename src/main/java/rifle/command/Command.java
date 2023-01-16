package rifle.command;

import rifle.command.others.CommandArguments;

/**
 * @author Huyemt
 */

public abstract class Command {
    // command name
    protected final String name;
    // command description
    protected final String description;
    // command usage
    protected final String[] usages;
    protected boolean canAsTask;

    public Command(String name, String description, String[] usages, boolean canAsTask) {
        this.name = name;
        this.description = description;
        this.usages = usages;
        this.canAsTask = canAsTask;
    }

    public Command(String name, String description, String[] usages) {
        this(name, description, usages, false);
    }

    /**
     * Execute command
     * @param commandArguments
     */
    public abstract void execute(CommandArguments commandArguments);

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final String[] getUsages() {
        return usages;
    }

    public final boolean isCanAsTask() {
        return canAsTask;
    }
}
