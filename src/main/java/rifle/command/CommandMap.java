package rifle.command;

import rifle.command.others.CommandArguments;

import java.util.*;

/**
 * @author Huyemt
 */

public class CommandMap {
    protected final Map<String, Command> maps = new HashMap<>();

    public CommandMap() {
        // ignore
    }

    public final boolean exists(String name) {
        return this.maps.containsKey(name);
    }

    public final Command get(String name) {
        return this.maps.getOrDefault(name, null);
    }

    public final Set<String> getAllCommandNames() {
        return new LinkedHashSet<>(this.maps.keySet());
    }

    public final boolean register(Command command) {
        if (exists(command.getName()))
            return false;

        this.maps.put(command.getName(), command);
        return true;
    }

    public final boolean unregister(String name) {
        if (!exists(name))
            return false;

        this.maps.remove(name);
        return true;
    }

    public final boolean execute(String name, String arguments) {
        if (name == null || name.length() == 0 || arguments == null)
            return false;

        if (!exists(name))
            return false;

        this.maps.get(name).execute(new CommandArguments(arguments));
        return true;
    }
}
