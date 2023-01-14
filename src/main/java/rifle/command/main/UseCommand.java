package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.module.ModuleBase;

/**
 * @author Huyemt
 */

public class UseCommand extends Command {
    public UseCommand() {
        super("use", "Use the specified module", new String[]{"use <moduleName>"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (!Rifle.getInstance().getConsoleThread().isMain()) {
            Rifle.getInstance().getLogger().println("Please quit or suspend the current module first.");
            return;
        }
        if (commandArguments.getOrigin().length() > 0) {
            String module = commandArguments.toOrderMode().getArguments()[0];
            if (Rifle.getInstance().getModuleManager().existsModule(module)) {
                ModuleBase moduleBase = Rifle.getInstance().getModuleManager().getModule(module);
                Rifle.getInstance().getConsoleThread().setModule(moduleBase);
                moduleBase.onSelected();
                moduleBase.setSelected(true);
                return;
            }
            Rifle.getInstance().getLogger().println("No module named `{}' was found.".replace("{}", module));
        } else
            Rifle.getInstance().getLogger().println("Please select a module that has been loaded by Rifle.");
    }
}
