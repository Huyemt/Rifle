package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Huyemt
 */

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "View command help", new String[]{"help", "help <commandName>...", "help -cmd <name>..."});
    }

    @Override
    public void execute(Argument argument) {
        Map<String, Command> c = Rifle.getInstance().getCommandManager().getAll();
        if (!Rifle.getInstance().getConsole().isMain())
            c = Stream.concat(c.entrySet().stream(), Rifle.getInstance().getConsole().getModule().getCommandManager().getAll().entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (argument.getOrigin().length() > 0) {
            if (argument.getOrigin().startsWith("-")) {
                if (argument.asKeyArgument().existsKey("cmd"))
                    formatHelp(c, argument.asKeyArgument().getArgument("cmd").toArray(String[]::new));
                else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(TextFormat.FONT_YELLOW).append("\nYou can use the help command in the following format:\n");
                    for (String usage : getUsages())
                        stringBuilder.append(" ".repeat(3)).append(TextFormat.FONT_WHITE).append(" - ").append(TextFormat.FONT_YELLOW).append("`").append(TextFormat.STYLE_BOLD).append(usage).append(TextFormat.RESET).append(TextFormat.FONT_YELLOW).append("`").append("\n");
                    Rifle.getInstance().getLogger().println(stringBuilder.toString());
                    printAll(new LinkedList<>(c.values()));
                }
            } else
                formatHelp(c, argument.asOrderArgument().getArgs());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TextFormat.FONT_YELLOW).append("\nYou can use the help command in the following format:\n");
            for (String usage : getUsages())
                stringBuilder.append(" ".repeat(3)).append(TextFormat.FONT_WHITE).append(" - ").append(TextFormat.FONT_YELLOW).append("`").append(TextFormat.STYLE_BOLD).append(usage).append(TextFormat.RESET).append(TextFormat.FONT_YELLOW).append("`").append("\n");
            Rifle.getInstance().getLogger().println(stringBuilder.toString());
            printAll(new LinkedList<>(c.values()));
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
