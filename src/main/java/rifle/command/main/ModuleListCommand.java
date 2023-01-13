package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;


/**
 * @author Huyemt
 */

public class ModuleListCommand extends Command {
    public ModuleListCommand() {
        super("ml", "Lists the modules that have been loaded by Rifle", "ml");
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        String[] moduleNames = Rifle.getInstance().getModuleManager().getModules().keySet().toArray(new String[0]);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Modules ({})".replace("{}", String.valueOf(moduleNames.length))).append(": ");

        if (moduleNames.length > 0) {
            stringBuilder.append("\n");
            for (int i = 0; i < moduleNames.length; i++) {
                stringBuilder.append("    - ").append(moduleNames[i]);

                if ((i + 1) < moduleNames.length)
                    stringBuilder.append("\n");
            }
        }
        Rifle.getInstance().getLogger().println(stringBuilder.toString());
    }
}
