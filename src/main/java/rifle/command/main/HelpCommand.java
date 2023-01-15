package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.command.others.KeyArguments;
import rifle.utils.TextFormat;

import java.util.*;

/**
 * @author Huyemt
 */

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Get command help", new String[]{"help"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (commandArguments.getOrigin().length() == 0)
            Rifle.getInstance().getLogger().println(getAllCommands());
        else {
            if (commandArguments.getOrigin().startsWith("-")) {
                KeyArguments arguments = commandArguments.toKeyMode();
                if (arguments.existsKey("n") || arguments.existsKey("name")) {
                    LinkedList<String> cmdNames = arguments.existsKey("n") ? arguments.getArgument("n") : arguments.getArgument("name");

                    if (cmdNames.size() > 0)
                        Rifle.getInstance().getLogger().println(formatHelps(cmdNames.toArray(new String[0])));
                    else
                        Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Missing parameter value.");
                } else
                    Rifle.getInstance().getLogger().println(getAllCommands());
            } else {
                String[] cmds = commandArguments.toOrderMode().getArguments();

                if (cmds.length > 0)
                    Rifle.getInstance().getLogger().println(formatHelps(cmds));
                else
                    Rifle.getInstance().getLogger().println(getAllCommands());
            }
        }
    }

    private String formatHelps(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> already = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            String name = strings[i];
            if (already.contains(name))
                continue;
            already.add(name);

            stringBuilder.append(name).append(": ");

            if (Rifle.getInstance().getConsoleThread().isMain()) {
                if (!Rifle.getInstance().getCommandMap().exists(name))
                    stringBuilder.append(TextFormat.FONT_RED).append("this command does not exists in Rifle.").append(TextFormat.STYLE_RESET);
                else {
                    Command command = Rifle.getInstance().getCommandMap().get(name);
                    stringBuilder.append("\n    Description: ").append(command.getDescription()).append("\n    Usage: ").append(formatUsages(command.getUsages()));
                }
            } else {
                if (!Rifle.getInstance().getConsoleThread().getModule().getCommandMap().exists(name))
                    stringBuilder.append(TextFormat.FONT_RED).append("this command does not exists in Rifle and Module \"{}\"".replace("{}", Rifle.getInstance().getConsoleThread().getModule().getModuleDescription().getName())).append(TextFormat.STYLE_RESET);
                else {
                    Command command = Rifle.getInstance().getConsoleThread().getModule().getCommandMap().get(name);
                    stringBuilder.append("\n    Description: ").append(command.getDescription()).append("\n    Usage: ").append(formatUsages(command.getUsages()));
                }
            }
            if ((i + 1) < strings.length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String formatUsages(String[] usages) {
        if (usages == null || usages.length == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (int i = 0; i < usages.length; i++) {
            stringBuilder.append(" ".repeat(8)).append(TextFormat.FONT_WHITE).append("- ").append(TextFormat.STYLE_RESET).append(usages[i]);
            if ((i + 1) < usages.length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String getAllCommands() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(TextFormat.FONT_GREEN).append("You can use \"help -name <cmdName>...\" or \"help <cmdName>...\" to get command help.\n\n").append(TextFormat.STYLE_RESET);
        Set<String> names = new LinkedHashSet<>(Rifle.getInstance().getCommandMap().getAllCommandNames());

        if (!Rifle.getInstance().getConsoleThread().isMain())
            names.addAll(Rifle.getInstance().getConsoleThread().getModule().getCommandMap().getAllCommandNames());

        stringBuilder.append("Commands ({}):\n".replace("{}", TextFormat.FONT_RED + String.valueOf(names.size()) + TextFormat.STYLE_RESET));
        int i = 0;
        for (String cmd : names) {
            i++;
            stringBuilder.append(TextFormat.FONT_WHITE).append("  - ").append(TextFormat.STYLE_RESET).append(cmd);
            if (i < names.size())
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
