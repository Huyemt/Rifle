package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.module.ModuleBase;

/**
 * @author Huyemt
 */

public class QuitCommand extends Command {
    public QuitCommand() {
        super("quit", "Quit the module of loaded by Rifle", new String[]{"quit", "quit <moduleName>"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (commandArguments.getOrigin().length() == 0) {
            if (!Rifle.getInstance().getConsoleThread().isMain()) {
                ModuleBase moduleBase = Rifle.getInstance().getConsoleThread().getModule();
                if (moduleBase == null) {
                    Rifle.getInstance().getConsoleThread().setMain();
                    return;
                }
                moduleBase.onQuit();
                moduleBase.setSelected(false);
                moduleBase.setSuspended(false);
                Rifle.getInstance().getConsoleThread().setMain();
            }
        }
    }
}
