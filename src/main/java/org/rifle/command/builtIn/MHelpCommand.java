package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.module.IModule;
import org.rifle.utils.TextFormat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * 查看模块的指令集
 *
 * View the instruction set for the current module
 *
 * @author Huyemt
 */

public class MHelpCommand extends Command {
    public MHelpCommand() {
        super("mhelp", "View the instruction set for the current module", new String[]{
                "mhelp",
                "mhelp <command>..."
        });
    }

    @Override
    public void execute(Argument argument) {
        String[] cmds = argument.asOrderArgument().getArgs();

        IModule module = Rifle.getInstance().getConsole().getModule();

        if (cmds.length == 0) {
            printAll(new LinkedList<>(module == null ? Rifle.getInstance().getCommandManager().getAll().values() : module.getCommandManager().getAll().values()));
        } else {
            formatHelp(module == null ? Rifle.getInstance().getCommandManager().getAll() : module.getCommandManager().getAll(), cmds);
        }
    }

    private void printAll(LinkedList<Command> commandList) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Commands").append("(").append(TextFormat.FONT_RED).append(TextFormat.STYLE_BOLD).append(commandList.size()).append(TextFormat.RESET).append("): ");
        if (commandList.size() > 0) {
            stringBuilder.append("\n");
            int longest = 0;
            for (Command command1 : commandList) {
                if (command1.getName().length() > longest)
                    longest = command1.getName().length();
            }

            for (Command command : commandList) {
                int spaceLength = longest - command.getName().length();
                stringBuilder.append(TextFormat.FONT_BLUE).append(TextFormat.STYLE_BOLD).append(command.getName()).append(TextFormat.RESET).append(spaceLength <= 0 ? "" : " ".repeat(spaceLength)).append("  ->  ").append(TextFormat.FONT_GREEN).append(command.getDescription()).append("\n");
            }
        }
        Rifle.getInstance().getLogger().println(stringBuilder.toString());
    }

    private void formatHelp(Map<String, Command> all, String[] commandList) {
        ArrayList<String> done = new ArrayList<>();
        int success = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (String name : commandList) {
            if (done.contains(name))
                continue;
            done.add(name);

            if (!all.containsKey(name))
                continue;

            Command command = all.get(name);
            stringBuilder
                    .append(command.getName()).append(":\n")
                    .append(" ".repeat(4)).append(TextFormat.STYLE_BOLD).append(TextFormat.FONT_BLUE).append("Description").append(TextFormat.RESET).append(" -> ").append(TextFormat.FONT_GREEN).append(command.getDescription()).append(TextFormat.RESET).append("\n");
            if (command.getUsages().length > 0) {
                stringBuilder.append(" ".repeat(4)).append(TextFormat.STYLE_BOLD).append(TextFormat.FONT_BLUE).append("Usages").append(TextFormat.RESET).append(":").append("\n");
                for (String usage : command.getUsages())
                    stringBuilder.append(" ".repeat(10)).append(TextFormat.FONT_WHITE).append(" - `").append(TextFormat.FONT_YELLOW).append(TextFormat.STYLE_BOLD).append(usage).append(TextFormat.RESET).append("`\n");
            }
            stringBuilder.append(TextFormat.RESET);
            success++;
        }

        Rifle.getInstance().getLogger().println(success == 0 ? TextFormat.FONT_RED + "None of the commands you want to query exist." : stringBuilder.toString());
    }
}
