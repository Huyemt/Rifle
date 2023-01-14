package rifle.command.others;

import rifle.utils.Utils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Keying of command arguments
 * @author Huyemt
 */

public class KeyArguments extends Arguments {
    protected Map<String, LinkedList<String>> arguments;

    public KeyArguments(String arguments) {
        super(arguments);
        this.arguments = new HashMap<>();

        parse();
    }

    private void parse() {
        /*

        Parse arguments and put them into arguments-map

        1. Spilt origin_string to be string array by space
        2. Iterate over an array of strings and put them into arguments-map

         */

        if (this.origin.length() > 0 && this.origin.charAt(0) == '-') {
            if (this.origin.contains(" ")) {
                String now_key = null;
                for (String argument : this.origin.split(" ")) {
                    if (argument.charAt(0) == '-') {
                        now_key = Utils.extractKeyName(argument);
                        if (!existsKey(now_key))
                            this.arguments.put(now_key, new LinkedList<>());
                        continue;
                    }

                    LinkedList<String> list = getArgument(now_key);
                    list.add(argument);
                    this.arguments.put(now_key, list);
                }
            } else
                this.arguments.put(Utils.extractKeyName(this.origin), new LinkedList<>());
        }

    }

    public final boolean existsKey(String name) {
        return this.arguments.containsKey(name);
    }

    public final Set<String> getKeys() {
        return this.arguments.keySet();
    }

    public final LinkedList<String> getArgument(String name) {
        return (LinkedList<String>) this.arguments.getOrDefault(name, new LinkedList<>()).clone();
    }
}
