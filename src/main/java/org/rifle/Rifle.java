package org.rifle;

import org.rifle.command.builtIn.*;
import org.rifle.manager.CommandMap;
import org.rifle.console.Console;
import org.rifle.console.logger.MainLogger;
import org.rifle.manager.ModuleManager;
import org.rifle.manager.TaskManager;
import org.rifle.scheduler.Task;
import org.rifle.utils.DataFolder;
import org.rifle.utils.TextFormat;

import java.io.File;

/**
 * @author Huyemt
 */

public class Rifle {
    private final Console console;
    private final CommandMap commandMap;
    private final ModuleManager moduleManager;
    private final TaskManager scheduler;
    private final DataFolder dataFolder;
    private static Rifle instance;
    private final String version = "1.0.0";

    public Rifle() {
        instance = this;
        dataFolder = new DataFolder(System.getProperty("user.dir"));
        console = new Console();
        scheduler = new TaskManager();
        commandMap = new CommandMap();
        moduleManager = new ModuleManager();

        console.clearScreen();

        getLogger().println(
                TextFormat.FONT_RED +
                "    ____     _    ____   __\n" +
                "   / __ \\   (_)  / __/  / /  ___\n" +
                "  / /_/ /  / /  / /_   / /  / _ \\\n" +
                " / _, _/  / /  / __/  / /  /  __/\n" +
                "/_/ |_|  /_/  /_/    /_/   \\___/\n\n\n" +
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
        }
    }

    /**
     * 获取 `CommandMap` 实例
     * `CommandMap`是存放`Command`的操作容器
     *
     * Get the instance of `CommandMap`
     * The `CommandMap` is an operation container for storing `Command`
     *
     * @return CommandMap
     */
    public CommandMap getCommandMap() {
        return commandMap;
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
    public DataFolder getDataFolder() {
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
        File modules = new File(getDataFolder() + "modules");
        if (modules.exists()) {
            if (modules.isFile()) {
                modules.delete();
                modules.mkdirs();
            }
        } else
            modules.mkdirs();

        getCommandMap().register(new HelpCommand());
        getCommandMap().register(new ClearScreenCommand());
        getCommandMap().register(new ExitCommand());
        getCommandMap().register(new UseCommand());
        getCommandMap().register(new QuitCommand());
        getCommandMap().register(new ModuleListCommand());
        getCommandMap().register(new TaskCommand());
        getCommandMap().register(new TaskListCommand());
        getCommandMap().register(new TaskKillerCommand());
    }
}