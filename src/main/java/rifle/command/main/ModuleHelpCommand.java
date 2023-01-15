package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.command.others.KeyArguments;
import rifle.module.ModuleBase;
import rifle.utils.TextFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Huyemt
 */

public class ModuleHelpCommand extends Command {
    public ModuleHelpCommand() {
        super("mhelp", "List the information of the module", new String[]{"mhelp <moduleName>...", "mhelp -name <moduleName>..."});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (commandArguments.getOrigin().length() == 0) {
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The format of `mhelp` you used is incorrect. Please refer to its usage:" + formatUsages());
            return;
        }
        if (commandArguments.getOrigin().startsWith("-")) {
            KeyArguments arguments = commandArguments.toKeyMode();
            if (arguments.existsKey("n") || arguments.existsKey("name")) {
                LinkedList<String> moduleNames = arguments.existsKey("n") ? arguments.getArgument("n") : arguments.getArgument("name");

                if (moduleNames.size() > 0)
                    Rifle.getInstance().getLogger().println(formatHelps(moduleNames.toArray(new String[0])));
                else
                    Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Missing parameter value.");
            } else
                Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The format of `mhelp` you used is incorrect. Please refer to its usage:" + formatUsages());
        } else {
            String[] moduleNames = commandArguments.toOrderMode().getArguments();

            if (moduleNames.length > 0)
                Rifle.getInstance().getLogger().println(formatHelps(moduleNames));
            else
                Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The format of `mhelp` you used is incorrect. Please refer to its usage:" + formatUsages());
        }
    }

    private String formatHelps(String[] moduleNames) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> already = new ArrayList<>();

        for (int i = 0; i < moduleNames.length; i++) {

            String name = moduleNames[i];
            if (already.contains(name))
                continue;
            already.add(name);

            stringBuilder.append(name).append(": ");

            if (!Rifle.getInstance().getModuleManager().existsModule(name))
                stringBuilder.append(TextFormat.FONT_RED).append("this module does not exists in Rifle.").append(TextFormat.STYLE_RESET);
            else {
                ModuleBase moduleBase = Rifle.getInstance().getModuleManager().getModule(name);
                stringBuilder.append("\n    Version: ").append(moduleBase.getModuleDescription().getVersion()).append("\n    Authors: ").append(Arrays.toString(moduleBase.getModuleDescription().getAuthors().toArray(new String[0]))).append("\n    Description: ").append(moduleBase.getModuleDescription().getDescription()).append("\n    Commands: ").append(String.valueOf(moduleBase.getCommandMap().getAllCommandNames()));
            }
            if ((i + 1) < moduleNames.length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String formatUsages() {
        if (getUsages() == null || getUsages().length == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (int i = 0; i < getUsages().length; i++) {
            stringBuilder.append(" ".repeat(4)).append(TextFormat.FONT_WHITE).append("- ").append(TextFormat.STYLE_RESET).append(getUsages()[i]);
            if ((i + 1) < getUsages().length)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
