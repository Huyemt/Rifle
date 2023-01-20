package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;

/**
 * @author Huyemt
 */

public class QuitCommand extends Command {
    public QuitCommand() {
        super("quit", "Exit the current module", new String[]{"quit"});
    }

    @Override
    public void execute(Argument argument) {
        if (Rifle.getInstance().getConsole().isMain())
            return;

        Rifle.getInstance().getConsole().resetModule();
    }
}
