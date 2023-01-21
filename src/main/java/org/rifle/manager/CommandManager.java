package org.rifle.manager;

import org.rifle.command.Command;
import org.rifle.command.CommmandParser;
import org.rifle.command.arguments.Argument;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {

    }

    /**
     * 通过名字判断该命令是否已经被注册
     *
     * Judging whether the command has been registered by name.
     *
     * @param name
     * @return boolean
     */
    public final boolean exists(String name) {
        return commands.containsKey(name);
    }

    /**
     * 通过名字获取一个命令实例
     *
     * Get a command instance by name.
     *
     * @param name
     * @return Command
     */
    public final Command getCommand(String name) {
        return commands.getOrDefault(name, null);
    }

    /**
     * 注册一个命令
     *
     * Register a command
     *
     * @param command
     */
    public synchronized final void register(Command command) {
        if (exists(command.getName()))
            return;

        commands.put(command.getName(), command);
    }

    /**
     * 注销一个命令
     *
     * Unregister a command
     *
     * @param name
     */
    public synchronized final void unregister(String name) {
        commands.remove(name);
    }

    public final boolean execute(String commandLine) {
        if (commandLine == null || commandLine.length() == 0)
            return true;

        String[] spiltC = CommmandParser.splitCommand(commandLine);
        return execute(spiltC[0], new Argument(spiltC[1]));
    }

    /**
     * 执行一个命令
     *
     * Execute a command
     *
     * @param name
     * @param argument
     * @return boolean
     */
    public final boolean execute(String name, Argument argument) {
        if (name == null || name.length() == 0)
            return true;

        if (!exists(name))
            return false;

        getCommand(name).execute(argument);
        return true;
    }

    /**
     * 获取所有已经被注册的命令
     *
     * Get all registered commands.
     *
     * @return Map
     */
    public final Map<String, Command> getAll() {
        return commands;
    }
}
