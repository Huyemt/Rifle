package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "Exiting Rifle program", new String[]{"exit"});
    }

    @Override
    public void execute(Argument argument) {
        Rifle.getInstance().getLogger().println("\n");
        Rifle.getInstance().getConsole().shutdown();
        Rifle.getInstance().getLogger().println("\n" + TextFormat.FONT_GREEN + "Bye!");
    }
}
