package org.rifle.command.arguments;

import org.rifle.command.CommmandParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author Huyemt
 */

public class KeyArgument extends ArgumentBase {
    private final Map<String, LinkedList<String>> arguments = new HashMap<>();

    public KeyArgument(String origin) {
        super(origin.trim().replace("  ", " "));

        parse();
    }

    private void parse() {
        /*
        origin_arg_string -> -path C:\Rifle -options noconsole hackermode -strong
        results -> map ( "path": ["C:\Rifle"], "options": ["noconsole", "hackermode"], "strong": [])
         */
        if (origin.length() > 0 && origin.charAt(0) == '-') {
            if (origin.contains(" ")) {
                String now_key = null;
                String[] args = origin.split(" ");

                for (String argument : args) {
                    if (argument.charAt(0) == '-') {
                        now_key = CommmandParser.extractName(argument);
                        if (!existsKey(now_key))
                            arguments.put(now_key, new LinkedList<>());
                    } else {
                        LinkedList<String> list = this.getArgument(now_key);
                        list.add(argument);
                        arguments.put(now_key, list);
                    }
                }
            } else
                arguments.put(CommmandParser.extractName(origin), new LinkedList<>());
        }

    }

    public final boolean existsKey(String name) {
        return this.arguments.containsKey(name);
    }

    public final Set<String> getKeys() {
        return this.arguments.keySet();
    }

    public final LinkedList<String> getArgument(String name) {
        return (LinkedList<String>) this.arguments.getOrDefault(name, new LinkedList()).clone();
    }
}
