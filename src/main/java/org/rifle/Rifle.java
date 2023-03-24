package org.rifle;

import org.rifle.command.builtIn.*;
import org.rifle.manager.CommandManager;
import org.rifle.console.Console;
import org.rifle.console.logger.MainLogger;
import org.rifle.manager.ModuleManager;
import org.rifle.manager.TaskManager;
import org.rifle.utils.RifleDataFolder;
import org.rifle.utils.TextFormat;


/**
 * @author Huyemt
 */

public class Rifle {
    private final Console console;
    private final CommandManager commandManager;
    private final ModuleManager moduleManager;
    private final TaskManager scheduler;
    private final RifleDataFolder dataFolder;
    private static Rifle instance;
    private final String version = "1.0.3";
    private final String github = "http://github.com/Huyemt/Rifle";

    public Rifle() {
        System.setProperty("nashorn.args", "--no-deprecation-warning");

        instance = this;
        dataFolder = new RifleDataFolder();
        commandManager = new CommandManager();
        console = new Console();
        moduleManager = new ModuleManager();
        scheduler = new TaskManager();

        console.clearScreen();

        getLogger().println(
                TextFormat.FONT_RED +
                "    ____     _    ____   __\n" +
                "   / __ \\   (_)  / __/  / /  ___\n" +
                "  / /_/ /  / /  / /_   / /  / _ \\\n" +
                " / _, _/  / /  / __/  / /  /  __/\n" +
                "/_/ |_|  /_/  /_/    /_/   \\___/\n\n\n\n\n" +
                TextFormat.FONT_YELLOW + "Github" + TextFormat.RESET + " -> " + github + "\n" +
                TextFormat.FONT_YELLOW + "Author" + TextFormat.RESET + " -> " + TextFormat.FONT_BLUE + TextFormat.STYLE_BOLD + "Huyemt (楠生)\n" + TextFormat.RESET +
                TextFormat.FONT_YELLOW + "Version" + TextFormat.RESET + " -> " + TextFormat.FONT_BLUE + TextFormat.STYLE_BOLD + version + "\n"
        );

        onLoad();

        getLogger().println(TextFormat.FONT_GREEN + "\nWelcome!\n\n");

        try {
            /*
            开启命令监听
            Turn on command listening.
             */
            getConsole().getCommandReader().start();
            getConsole().getCommandReader().join();
        } catch (InterruptedException e) {
            getConsole().shutdown();
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     * 获取 `CommandManager` 实例
     * `CommandManager`是存放`Command`的操作容器
     *
     * Get the instance of `CommandManager`
     * The `CommandManager` is an operation container for storing `Command`
     *
     * @return CommandManager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * 获取 `Console` 实例
     *
     * Get the instance of `Console`
     *
     * @return Console
     */
    public final Console getConsole() {
        return console;
    }

    /**
     * 获取 `MainLogger` 实例
     *
     * Get the instance of `MainLogger`
     *
     * @return MainLogger
     */
    public final MainLogger getLogger() {
        return console.getLogger();
    }

    /**
     * 获取 `ModuleManager` 实例
     * `ModuleManager`是存放`Module`的操作容器
     *
     * Get the instance of `ModuleManager`
     * The `ModuleManager` is an operation container for storing `Module`
     *
     * @return ModuleManager
     */
    public final ModuleManager getModuleManager() {
        return moduleManager;
    }

    /**
     * 获取 `DataFolder` 实例
     *
     * Get the instance of `DataFolder`
     *
     * @return DataFolder
     */
    public RifleDataFolder getDataFolder() {
        return dataFolder;
    }

    /**
     * 获取 `TaskManager` 实例
     *
     * Get the instance of `TaskManager`
     *
     * @return TaskManager
     */
    public TaskManager getScheduler() {
        return scheduler;
    }

    public final String getVersion() {
        return version;
    }

    public final String getGithub() {
        return github;
    }

    /**
     * 获取 `Rifle` 实例
     *
     * Get the instance of `Rifle`
     *
     * @return Rifle
     */
    public static Rifle getInstance() {
        return instance;
    }

    private void onLoad() {
        init();
        getModuleManager().loadModules();
    }

    private void init() {
        getCommandManager().register(new HelpCommand());
        getCommandManager().register(new ClearScreenCommand());
        getCommandManager().register(new ExitCommand());
        getCommandManager().register(new UseCommand());
        getCommandManager().register(new QuitCommand());
        getCommandManager().register(new ModuleListCommand());
        getCommandManager().register(new MHelpCommand());
        getCommandManager().register(new TaskCommand());
        getCommandManager().register(new TaskListCommand());
        getCommandManager().register(new TaskKillerCommand());
    }
}