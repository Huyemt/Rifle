package rifle;

import rifle.command.CommandMap;
import rifle.command.main.*;
import rifle.module.ModuleManager;
import rifle.threads.ConsoleThread;
import rifle.utils.Logger;
import rifle.utils.MainLogger;
import rifle.utils.Utils;

import java.io.File;
import java.util.Scanner;

/**
 * @author Huyemt
 */

public class Rifle {
    public static final String VERSION = "1.0.0";
    public static final String RIFLE_NAME = "    ____     _    ____   __      \n   / __ \\   (_)  / __/  / /  ___ \n  / /_/ /  / /  / /_   / /  / _ \\\n / _, _/  / /  / __/  / /  /  __/\n/_/ |_|  /_/  /_/    /_/   \\___/ \n                                 \n";
    public static String RIFLE_PATH = null;

    // Rifle instance
    private static Rifle instance;

    private Logger logger;

    private CommandMap commandMap;

    private ModuleManager manager;
    private ConsoleThread consoleThread;

    public Rifle() {
        instance = this;
        RIFLE_PATH = System.getProperty("user.dir").concat(File.separator);

        // loading process
        this.load();

        // running process
        this.run();
    }

    private void load() {
        logger = new MainLogger();
        commandMap = new CommandMap();
        manager = new ModuleManager();

        initDataFiles();
        initMainCommands();

        getLogger().println(RIFLE_NAME);
        getLogger().println("Author: Huyemt (楠生)");
        getLogger().println("Welcome to Rifle v".concat(VERSION).concat("!"));
        getLogger().println("");
        manager.loadModules();
        getLogger().println("");

    }

    private void run() {
        consoleThread = new ConsoleThread(System.in);
        consoleThread.start();
        try {
            consoleThread.join();
        } catch (InterruptedException e) {
            getLogger().error(e.getMessage());
            consoleThread.stopIt();
        }
    }

    private void initMainCommands() {
        getCommandMap().register(new ExitCommand());
        getCommandMap().register(new HelpCommand());
        getCommandMap().register(new TestCommand());
        getCommandMap().register(new ModuleListCommand());
        getCommandMap().register(new ModuleHelpCommand());
    }

    private void initDataFiles() {
        File modules = new File(RIFLE_PATH + "modules");
        if (!modules.exists())
            modules.mkdirs();
    }

    // get Rifle instance
    public static Rifle getInstance() {
        return instance;
    }

    public final CommandMap getCommandMap() {
        return commandMap;
    }

    public final ModuleManager getModuleManager() {
        return manager;
    }

    public final Logger getLogger() {
        return logger;
    }

    public final ConsoleThread getConsoleThread() {
        return consoleThread;
    }

    public static void main(String[] args) {
        new Rifle();
    }
}
