package rifle;

import org.fusesource.jansi.AnsiConsole;
import rifle.command.CommandMap;
import rifle.command.main.*;
import rifle.module.ModuleManager;
import rifle.task.TaskMap;
import rifle.threads.ConsoleThread;
import rifle.utils.Logger;
import rifle.utils.MainLogger;
import rifle.utils.TextFormat;

import java.io.File;

/**
 * @author Huyemt
 */

public class Rifle {
    public static final String VERSION = "1.0.0";
    private final String USER_SYSTEM;
    public static final String RIFLE_NAME = "    ____     _    ____   __      \n   / __ \\   (_)  / __/  / /  ___ \n  / /_/ /  / /  / /_   / /  / _ \\\n / _, _/  / /  / __/  / /  /  __/\n/_/ |_|  /_/  /_/    /_/   \\___/ \n                                 \n";
    private final String RIFLE_PATH;

    // Rifle instance
    private static Rifle instance;

    private Logger logger;

    private CommandMap commandMap;

    private ModuleManager manager;
    private ConsoleThread consoleThread;
    private TaskMap taskMap;

    public Rifle() {
        AnsiConsole.systemInstall();
        clearScreen();
        instance = this;
        RIFLE_PATH = System.getProperty("user.dir").concat(File.separator);
        USER_SYSTEM = System.getProperty("os.name").toLowerCase();

        // loading process
        this.load();

        // running process
        this.run();
    }

    private void load() {
        taskMap = new TaskMap();
        logger = new MainLogger();
        commandMap = new CommandMap();
        manager = new ModuleManager();

        initDataFiles();
        initMainCommands();

        getLogger().println(TextFormat.FONT_RED + TextFormat.STYLE_BOLD.toString() + RIFLE_NAME);
        getLogger().println(TextFormat.FONT_GREEN + "Author: " + TextFormat.STYLE_RESET + TextFormat.STYLE_BOLD + "Huyemt (楠生)");
        getLogger().println("Welcome to " + TextFormat.FONT_RED + TextFormat.STYLE_BOLD + "Rifle" + TextFormat.STYLE_THIN + TextFormat.FONT_WHITE + " v" + VERSION + "!");
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
        getCommandMap().register(new UseCommand());
        getCommandMap().register(new QuitCommand());
        getCommandMap().register(new ClearCommand());
        getCommandMap().register(new TaskCommand());
        getCommandMap().register(new TaskListCommand());
        getCommandMap().register(new TasKillCommand());
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

    public final String getUserSystem() {
        return USER_SYSTEM;
    }

    public final String getRiflePath() {
        return RIFLE_PATH;
    }

    public final Logger getLogger() {
        return logger;
    }

    public final TaskMap getTaskMap() {
        return taskMap;
    }

    public final void clearScreen() {
        System.out.println("\u001B[2J");
    }

    public final ConsoleThread getConsoleThread() {
        return consoleThread;
    }

    public static void main(String[] args) {
        new Rifle();
    }
}
