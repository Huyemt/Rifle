package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.CommmandParser;
import org.rifle.command.arguments.Argument;
import org.rifle.scheduler.Task;
import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class TaskCommand extends Command {
    public TaskCommand() {
        super("task", "Run an existing command as a task in the background (multithreading)", new String[]{"task <command>"});
    }

    @Override
    public void execute(Argument argument) {
        if (argument.getOrigin().length() > 0) {
            String[] cmd = CommmandParser.splitCommand(argument.getOrigin());
            Task task;
            if (Rifle.getInstance().getCommandManager().exists(cmd[0])) {
                Command command = Rifle.getInstance().getCommandManager().getCommand(cmd[0]);
                if (!command.isCanBeTask()) {
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "This command has been set to disable running in task mode.");
                    return;
                }
                task = new Task() {
                    @Override
                    public void run() {
                        Rifle.getInstance().getLogger().println(TextFormat.FONT_GREEN + "Successfully created a command task with ID `{}`: ".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + getTaskId() + TextFormat.RESET + TextFormat.FONT_GREEN) + TextFormat.RESET + argument.getOrigin().trim());
                        command.execute(new Argument(cmd[1].trim()));
                    }
                };
                if (!Rifle.getInstance().getScheduler().addTask(task))
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "This command cannot be run as a task in the background for unknown reasons.");
            } else {
                if (!Rifle.getInstance().getConsole().isMain()) {
                    if (Rifle.getInstance().getConsole().getModule().getCommandManager().exists(cmd[0])) {
                        Command command = Rifle.getInstance().getConsole().getModule().getCommandManager().getCommand(cmd[0]);
                        if (!command.isCanBeTask()) {
                            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "This command has been set to disable running in task mode.");
                            return;
                        }
                        task = new Task() {
                            @Override
                            public void run() {
                                Rifle.getInstance().getLogger().println(TextFormat.FONT_GREEN + "Successfully created a command task with ID `{}`: ".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + getTaskId() + TextFormat.RESET + TextFormat.FONT_GREEN) + TextFormat.RESET + argument.getOrigin().trim());
                                command.execute(new Argument(cmd[1].trim()));
                            }
                        };
                        if (!Rifle.getInstance().getScheduler().addTask(task))
                            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "This command cannot be run as a task in the background for unknown reasons.");
                    } else
                        Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The command `{}` does not exist in the Rifle and the Module ".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + cmd[0] + TextFormat.RESET + TextFormat.FONT_RED) + "`" + Rifle.getInstance().getConsole().getModule().getModuleDescription().getName() + "`");
                    return;
                }
            }
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The command `{}` does not exist in the Rifle.".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + cmd[0] + TextFormat.RESET + TextFormat.FONT_RED));
        } else
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "You haven't entered an existing command, so you can't start a new task.");
    }
}
