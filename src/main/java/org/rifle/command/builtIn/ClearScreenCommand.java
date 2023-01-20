package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;

/**
 * @author Huyemt
 */

public class ClearScreenCommand extends Command {
    public ClearScreenCommand() {
        super("cls", "Clear the console screen", new String[]{"cls"});
    }

    @Override
    public void execute(Argument argument) {
        Rifle.getInstance().getConsole().clearScreen();
    }
}
