package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;

/**
 * @author Huyemt
 */

public class ClearCommand extends Command {
    public ClearCommand() {
        super("cls", "Clear screen", new String[]{"cls"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        Rifle.getInstance().clearScreen();
    }
}
