package org.rifle.console;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.rifle.Rifle;
import org.rifle.module.IModule;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Huyemt
 */

public class TabCompleter implements Completer {
    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        list.clear();
        String buffer = parsedLine.line();
        String[] args = buffer.split("\\s+");
        String[] r = getCommands();

        if (args.length == 0) {
            // If the user does not enter anything, then add all the commands available in the current module environment
            // 如果用户没有输入任何东西，那么就将当前模块环境下可以使用的所有命令全部推送
            addR(list, r);
        } else if (args.length == 1) {
            if (Arrays.stream(r).noneMatch(s -> s.equals(args[0].toLowerCase()))) {
                list.clear();
                r = getCommands(args[0], r);
                addR(list, r);
                return;
            }

            if (args[0].equalsIgnoreCase("use")) {
                list.clear();
                addR(list, getModules());
            }
        }
    }

    private void addR(List<Candidate> list, String[] options) {
        Arrays.stream(options).forEach(it -> list.add(new Candidate(it)));
    }

    private String[] getCommands() {
        LinkedList<String> commands = new LinkedList<>(Rifle.getInstance().getCommandManager().getAll().keySet());

        if (!Rifle.getInstance().getConsole().isMain()) {
            String[] cc = Rifle.getInstance().getConsole().getModule().getCommandManager().getAll().keySet().toArray(String[]::new);
            for (String a : cc) {
                if (commands.contains(a)) {
                    continue;
                }

                commands.add(a);
            }
        }

        return commands.toArray(String[]::new);
    }

    private String[] getCommands(String name, String[] f) {
        LinkedList<String> r = new LinkedList<>();

        for (String cmd : f) {
            if (cmd.length() < name.length()) {
                continue;
            }

            if (cmd.substring(0, name.length()).equals(name.toLowerCase())) {
                r.add(cmd);
            }
        }

        return r.toArray(String[]::new);
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

        if (main.length() >= name.length() && main.substring(0, name.length()).equals(name.toLowerCase())) {
            r.add(main);
        }

        return r.toArray(String[]::new);
    }
}
