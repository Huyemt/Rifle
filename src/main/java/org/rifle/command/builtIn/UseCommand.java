package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class UseCommand extends Command {
    public UseCommand() {
        super("use", "Use a module that has been loaded by Rifle", new String[]{"use <moduleName>"});
    }

    @Override
    public void execute(Argument argument) {
        if (argument.getOrigin().length() > 0) {
            String moduleName = argument.asOrderArgument().getArgs()[0];
            if (!Rifle.getInstance().getConsole().setModule(moduleName)) {
                if (Rifle.getInstance().getModuleManager().exists(moduleName)) {
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "This module is set to disable user check:  " + TextFormat.STYLE_BOLD + TextFormat.FONT_BLUE + Rifle.getInstance().getModuleManager().get(moduleName).getModuleDescription().getName());
                } else {
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "This module has not been loaded by Rifle: " + TextFormat.STYLE_BOLD + TextFormat.FONT_BLUE + moduleName);
                }
            }
        }
    }
}
