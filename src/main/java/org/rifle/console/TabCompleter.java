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
            addR(list, r);
            return;
        }

        if (args.length == 1 && Arrays.stream(r).noneMatch(s -> s.equals(args[0].toLowerCase()))) {
            addR(list, getCommands(args[0], r));
            return;
        }

        // existing this name of commands
        if (Arrays.stream(r).anyMatch(s -> s.equals(args[0].toLowerCase()))) {
            String[] ags = buffer.contains(" ") ? buffer.substring(buffer.indexOf(' ') + 1).split(" ") : new String[]{};
            if (Rifle.getInstance().getConsole().isMain()) {
                addR(list, Rifle.getInstance().getCommandManager().getCommand(args[0]).complete(ags.length == 0 ? null : ags[ags.length-1], ags));
            } else {

                addR(list, Rifle.getInstance().getCommandManager().exists(args[0]) ? Rifle.getInstance().getCommandManager().getCommand(args[0]).complete(ags.length == 0 ? null : ags[ags.length-1], ags) : Rifle.getInstance().getConsole().getModule().getCommandManager().getCommand(args[0]).complete(ags.length == 0 ? null : ags[ags.length-1], ags));
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
}
