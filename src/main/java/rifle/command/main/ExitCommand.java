package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;

/**
 * @author Huyemt
 */

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "Exit the Rifle process", new String[]{"exit"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        Rifle.getInstance().getConsoleThread().stopIt();
    }
}
