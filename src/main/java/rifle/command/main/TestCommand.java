package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.command.others.KeyArguments;
import rifle.command.others.OrderArguments;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Huyemt
 */

public class TestCommand extends Command {
    public TestCommand() {
        super("test", "Testing the Rifle command-system", "test <key> <value> || test <value>...");
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (commandArguments.getOrigin().length() == 0)
            // no arguments
            Rifle.getInstance().getLogger().print("Full arguments -> null" + "\nArguments order mode data" + orderToString(commandArguments.toOrderMode()) + "\nArguments key mode data" + keyValueToString(commandArguments.toKeyMode()));
        else {
            if (commandArguments.getOrigin().startsWith("-"))
                Rifle.getInstance().getLogger().print("Full arguments -> " + commandArguments.getOrigin() + "\nArguments key mode data" + keyValueToString(commandArguments.toKeyMode()));
            else
                Rifle.getInstance().getLogger().print("Full arguments -> " + commandArguments.getOrigin() + "\nArguments order mode data" + orderToString(commandArguments.toOrderMode()));
        }
    }

    private String keyValueToString(KeyArguments arguments) {
        if (arguments.getOrigin().length() == 0)
            return " -> []";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":\n");

        String[] keys = arguments.getKeys().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            LinkedList<String> args = arguments.getArgument(key);
            stringBuilder.append("  ").append(key).append(" -> ").append(Arrays.toString(args.toArray(new String[0])));

            if ((i + 1) < keys.length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String orderToString(OrderArguments orderArguments) {
        if (orderArguments.getOrigin().length() == 0)
            return " -> []";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":\n");

        String[] args = orderArguments.getArguments();

        for (int i = 0; i < args.length; i++) {
            stringBuilder.append("  - ").append(args[i]);

            if ((i + 1) < args.length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
