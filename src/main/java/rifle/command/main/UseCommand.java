package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.module.ModuleBase;
import rifle.utils.TextFormat;

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
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Please quit or suspend the current module first.");
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
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "No module named `{}' was found.".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + module + TextFormat.STYLE_RESET + TextFormat.FONT_RED));
        } else
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Please select a module that has been loaded by Rifle.");
    }
}
