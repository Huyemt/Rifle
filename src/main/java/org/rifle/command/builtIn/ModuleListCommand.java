package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.module.IModule;
import org.rifle.utils.TextFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * @author Huyemt
 */

public class ModuleListCommand extends Command {
    public ModuleListCommand() {
        super("ml", "Query the module that has been loaded by Rifle", new String[]{"ml", "ml <moduleName>..."});
    }

    @Override
    public void execute(Argument argument) {
        if (argument.getOrigin().length() > 0)
            formatHelp(argument.asOrderArgument().getArgs());
        else
            printAll();
    }

    @Override
    public String[] complete(String reference, String[] args) {
        return args.length == 0 ? getModules() : getModules(reference);
    }

    private void printAll() {
        StringBuilder stringBuilder = new StringBuilder();
        IModule[] modules = Rifle.getInstance().getModuleManager().getModules();

        stringBuilder
                .append("Modules").append("(").append(TextFormat.FONT_RED).append(TextFormat.STYLE_BOLD).append(modules.length).append(TextFormat.RESET).append("): ");
        if (modules.length > 0) {
            for (int i = 0; i < modules.length; i++) {
                stringBuilder.append(TextFormat.FONT_GREEN).append(TextFormat.STYLE_BOLD).append(modules[i].getModuleDescription().getName()).append(" ").append("v").append(modules[i].getModuleDescription().getVersion());
                if ((i + 1) < modules.length)
                    stringBuilder.append(TextFormat.RESET).append(", ");
            }
        }
        Rifle.getInstance().getLogger().println(stringBuilder.toString());
    }

    private void formatHelp(String[] moduleNames) {
        ArrayList<String> done = new ArrayList<>();
        int success = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (String name : moduleNames) {
            if (done.contains(name))
                continue;
            done.add(name);

            if (!Rifle.getInstance().getModuleManager().exists(name))
                continue;

            IModule module = Rifle.getInstance().getModuleManager().get(name);
            String authors = Arrays.toString(module.getModuleDescription().getAuthors());
            stringBuilder
                    .append(module.getModuleDescription().getName()).append(":\n")
                    .append(" ".repeat(4)).append(TextFormat.STYLE_BOLD).append(TextFormat.FONT_BLUE).append("Version").append(TextFormat.RESET).append(" -> ").append(TextFormat.FONT_RED).append(TextFormat.STYLE_BOLD).append(module.getModuleDescription().getVersion()).append(TextFormat.RESET).append("\n")
                    .append(" ".repeat(4)).append(TextFormat.STYLE_BOLD).append(TextFormat.FONT_BLUE).append("Authors").append(TextFormat.RESET).append(" -> ").append(authors, 1, authors.length() - 1).append(TextFormat.RESET).append("\n")
                    .append(" ".repeat(4)).append(TextFormat.STYLE_BOLD).append(TextFormat.FONT_BLUE).append("Description").append(TextFormat.RESET).append(" -> ");
            if (!module.getModuleDescription().getDescription().equalsIgnoreCase("~"))
                stringBuilder.append(TextFormat.FONT_GREEN).append(module.getModuleDescription().getDescription());
            else
                stringBuilder.append("null");
            stringBuilder.append(TextFormat.RESET).append("\n")
                    .append(" ".repeat(4)).append(TextFormat.STYLE_BOLD).append(TextFormat.FONT_BLUE).append("Website").append(TextFormat.RESET).append(" -> ");
            if (module.getModuleDescription().getWebsite().startsWith("http://") || module.getModuleDescription().getWebsite().startsWith("https://"))
                stringBuilder.append(TextFormat.STYLE_UNDERLINE).append(module.getModuleDescription().getWebsite());
            else {
                if (module.getModuleDescription().getWebsite().equalsIgnoreCase("~"))
                    stringBuilder.append("null");
                else
                    stringBuilder.append(module.getModuleDescription().getWebsite());
            }
            stringBuilder.append(TextFormat.RESET).append("\n");
            stringBuilder.append(TextFormat.RESET);
            success++;
        }

        Rifle.getInstance().getLogger().println(success == 0 ? TextFormat.FONT_RED + "None of the modules you want to query exist." : stringBuilder.toString());
    }

    private String[] getModules() {
        LinkedList<String> r = new LinkedList<>();

        for (IModule n : Rifle.getInstance().getModuleManager().getModules()) {
            r.add(n.getModuleDescription().getName());
        }

        return r.toArray(String[]::new);
    }

    private String[] getModules(String name) {
        LinkedList<String> r = new LinkedList<>();

        for (IModule n : Rifle.getInstance().getModuleManager().getModules()) {

            if (n.getModuleDescription().getName().length() < name.length()) {
                continue;
            }

            if (n.getModuleDescription().getName().toLowerCase().substring(0, name.length()).equals(name.toLowerCase())) {
                r.add(n.getModuleDescription().getName());
            }
        }

        return r.toArray(String[]::new);
    }
}
