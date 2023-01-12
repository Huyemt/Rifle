package rifle;

import rifle.command.CommandMap;
import rifle.command.main.ExitCommand;
import rifle.command.main.HelpCommand;
import rifle.command.main.TestCommand;
import rifle.utils.Logger;
import rifle.utils.MainLogger;
import rifle.utils.Utils;
import java.util.Scanner;

/**
 * @author Huyemt
 */

public class Rifle {
    private final String version = "1.0.0";

    // Rifle instance
    private static Rifle instance;
    // running state (listen command)
    public volatile boolean running = false;

    private CommandMap commandMap;

    private Logger logger;

    public static final String RIFLE_NAME = "    ____     _    ____   __      \n   / __ \\   (_)  / __/  / /  ___ \n  / /_/ /  / /  / /_   / /  / _ \\\n / _, _/  / /  / __/  / /  /  __/\n/_/ |_|  /_/  /_/    /_/   \\___/ \n                                 \n";

    public Rifle() {
        instance = this;

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

        initMainCommands();

        getLogger().print(RIFLE_NAME);
        getLogger().print("Author: Huyemt (楠生)");
        getLogger().print("Welcome to Rifle v".concat(version).concat("!"));
        getLogger().print("");
    }

    private void run() {
        Scanner scanner;
        String[] cmd;
        while (running) {
            Thread.onSpinWait();
            System.out.print("> ");
            scanner = new Scanner(System.in);

            if (scanner.hasNext()) {
                cmd = Utils.spiltCommand(scanner.nextLine());
                if (cmd.length == 0)
                    continue;

                if (!getCommandMap().execute(cmd[0], cmd[1])) {
                    getLogger().print("command `{name}` does not exists.".replace("{name}", cmd[0]));
                }
            }
        }
    }

    private void close() {

    }

    private void initMainCommands() {
        getCommandMap().register(new ExitCommand());
        getCommandMap().register(new HelpCommand());
        getCommandMap().register(new TestCommand());
    }

    // get Rifle instance
    public static Rifle getInstance() {
        return instance;
    }

    public final CommandMap getCommandMap() {
        return commandMap;
    }

    public final Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        new Rifle();
    }
}
