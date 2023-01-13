package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.command.others.KeyArguments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Huyemt
 */

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Get command help", "help");
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
                        Rifle.getInstance().getLogger().println("Missing parameter value.");
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

            if (!Rifle.getInstance().getCommandMap().exists(name))
                stringBuilder.append("this command does not exists");
            else {
                Command command = Rifle.getInstance().getCommandMap().get(name);
                stringBuilder.append("\n    Description: ").append(command.getDescription()).append("\n    Usage: ").append(command.getUsage());
            }
            if ((i + 1) < strings.length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String getAllCommands() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("You can use \"help -name <cmdName>...\" or \"help <cmdName>...\" to get command help.\n\n");
        Set<String> names = Rifle.getInstance().getCommandMap().getAllCommandNames();

        stringBuilder.append("Commands ({}):\n".replace("{}", String.valueOf(names.size())));
        int i = 0;
        for (String cmd : names) {
            i++;
            stringBuilder.append("  - ").append(cmd);
            if (i < names.size())
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
