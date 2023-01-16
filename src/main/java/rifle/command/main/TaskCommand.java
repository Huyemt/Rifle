package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.module.ModuleBase;
import rifle.task.Task;
import rifle.utils.TextFormat;
import rifle.utils.Utils;

/**
 * @author Huyemt
 */

public class TaskCommand extends Command {
    public TaskCommand() {
        super("task", "Suspend a command as a task", new String[]{"task <command>"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (commandArguments.getOrigin().length() > 0) {
            String[] cmd = Utils.spiltCommand(commandArguments.getOrigin());
            if (cmd[0].length() == 0 || cmd[0].startsWith("\n")) {
                return;
            }

            if (Rifle.getInstance().getCommandMap().exists(cmd[0])) {
                if (!Rifle.getInstance().getCommandMap().get(cmd[0]).isCanAsTask()) {
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "You cannot suspend the command as a task.");
                    return;
                }

                Task task = new Task() {
                    @Override
                    public void run() {
                        Rifle.getInstance().getCommandMap().execute(cmd[0], cmd[1]);
                    }
                };
                Rifle.getInstance().getTaskMap().addTask(task);
                Rifle.getInstance().getLogger().println(TextFormat.FONT_GREEN + "Successfully added task \"{}\"".replace("{}", TextFormat.FONT_RED + task.getTaskID() + TextFormat.FONT_GREEN) + TextFormat.FONT_BLUE + " -> " + TextFormat.FONT_WHITE + commandArguments.getOrigin());
            } else {
                if (!Rifle.getInstance().getConsoleThread().isMain()) {
                    ModuleBase module = Rifle.getInstance().getConsoleThread().getModule();
                    if (!Rifle.getInstance().getModuleManager().existsModule(module.getModuleDescription().getName())) {
                        Rifle.getInstance().getLogger().error(TextFormat.FONT_RED + "Module \"{}\" was closed. Automatic exit this module.".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + module.getModuleDescription().getName() + TextFormat.STYLE_RESET + TextFormat.FONT_RED));
                        Rifle.getInstance().getConsoleThread().setMain();
                        return;
                    }

                    if (module.getCommandMap().exists(cmd[0])) {
                        if (!module.getCommandMap().get(cmd[0]).isCanAsTask()) {
                            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "You cannot suspend the command as a task.");
                            return;
                        }

                        Task task = new Task() {
                            @Override
                            public void run() {
                                module.getCommandMap().execute(cmd[0], cmd[1]);
                            }
                        };
                        Rifle.getInstance().getTaskMap().addTask(task);
                        Rifle.getInstance().getLogger().println(TextFormat.FONT_GREEN + "Successfully added task \"{}\"".replace("{}", TextFormat.FONT_RED + task.getTaskID() + TextFormat.FONT_GREEN) + TextFormat.FONT_BLUE + " -> " + TextFormat.FONT_WHITE + commandArguments.getOrigin());
                        return;
                    }
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "command `{name}` does not exists in Rifle and Module \"{module}\".".replace("{name}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + cmd[0] + TextFormat.STYLE_RESET + TextFormat.FONT_RED).replace("{module}", module.getModuleDescription().getName()));
                    return;
                }
                Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "command `{name}` does not exists in Rifle.".replace("{name}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + cmd[0] + TextFormat.STYLE_RESET + TextFormat.FONT_RED));
            }
        } else
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Please enter the command.");
    }
}
