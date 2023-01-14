package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.module.ModuleBase;
import java.util.Collection;


/**
 * @author Huyemt
 */

public class ModuleListCommand extends Command {
    public ModuleListCommand() {
        super("ml", "Lists the modules that have been loaded by Rifle", "ml");
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        Collection<ModuleBase> modules = Rifle.getInstance().getModuleManager().getModules().values();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Modules ({})".replace("{}", String.valueOf(modules.size()))).append(": ");

        if (modules.size() > 0) {
            int i = 0;
            for (ModuleBase moduleBase : modules) {
                stringBuilder.append(moduleBase.getModuleDescription().getFullname());
                if ((i + 1) < modules.size())
                    stringBuilder.append(",");
            }
        }
        Rifle.getInstance().getLogger().println(stringBuilder.toString());
    }
}
