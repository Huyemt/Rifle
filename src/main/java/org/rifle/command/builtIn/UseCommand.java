package org.rifle.command.builtIn;

import org.jline.reader.Candidate;
import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.module.IModule;
import org.rifle.utils.TextFormat;

import java.util.LinkedList;
import java.util.List;


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

    @Override
    public String[] complete(String reference, String[] args) {
        if (args.length <= 1) {
            return args.length == 0 ? getModules() : ((!Rifle.getInstance().getModuleManager().exists(reference) && !reference.equalsIgnoreCase("rifle")) ? getModules(reference) : new String[0]);
        }

        return new String[0];
    }

    private String[] getModules() {
        LinkedList<String> r = new LinkedList<>();

        for (IModule n : Rifle.getInstance().getModuleManager().getModules()) {
            if (!n.isUserCanSelect()) {
                continue;
            }

            r.add(n.getModuleDescription().getName());
        }

        r.add("Rifle");

        return r.toArray(String[]::new);
    }

    private String[] getModules(String name) {
        LinkedList<String> r = new LinkedList<>();

        for (IModule n : Rifle.getInstance().getModuleManager().getModules()) {
            if (!n.isUserCanSelect()) {
                continue;
            }

            if (n.getModuleDescription().getName().length() < name.length()) {
                continue;
            }

            if (n.getModuleDescription().getName().toLowerCase().substring(0, name.length()).equals(name.toLowerCase())) {
                r.add(n.getModuleDescription().getName());
            }
        }

        String main = "Rifle";

        if (main.length() >= name.length() && main.toLowerCase().substring(0, name.length()).equals(name.toLowerCase())) {
            r.add(main);
        }

        return r.toArray(String[]::new);
    }
}
