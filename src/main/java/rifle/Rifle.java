package rifle;

import rifle.command.CommandMap;
import rifle.command.main.ExitCommand;
import rifle.command.main.HelpCommand;
import rifle.command.main.TestCommand;
import rifle.module.ModuleManager;
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
    // running state (listen command)
    public volatile boolean running = false;

    private Logger logger;

    private CommandMap commandMap;

    private ModuleManager manager;

    public Rifle() {
        instance = this;
        RIFLE_PATH = System.getProperty("user.dir").concat(File.separator);

        // set running state to true
        this.running = true;

        // loading process
        this.load();

        // running process
        this.run();

        // closing process
        this.close();
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
        Scanner scanner;
        String[] cmd;
        while (running) {
            Thread.onSpinWait();
            getLogger().print("Rifle> ");
            scanner = new Scanner(System.in);

            if (scanner.hasNext()) {
                cmd = Utils.spiltCommand(scanner.nextLine());
                if (cmd.length == 0)
                    continue;

                if (!getCommandMap().execute(cmd[0], cmd[1])) {
                    getLogger().println("command `{name}` does not exists.".replace("{name}", cmd[0]));
                }
            }
        }
    }

    private void close() {

        // unregister commands
        String[] commands = getCommandMap().getAllCommandNames().toArray(new String[0]);
        for (String commmand : commands) {
            getCommandMap().unregister(commmand);
        }


    }

    private void initMainCommands() {
        getCommandMap().register(new ExitCommand());
        getCommandMap().register(new HelpCommand());
        getCommandMap().register(new TestCommand());
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

    public final void stop() {
        running = false;
    }

    public static void main(String[] args) {
        new Rifle();
    }
}
