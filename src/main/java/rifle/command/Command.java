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

    public Command(String name, String description, String[] usages) {
        this.name = name;
        this.description = description;
        this.usages = usages;
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
}
