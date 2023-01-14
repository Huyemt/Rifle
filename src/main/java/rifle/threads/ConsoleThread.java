package rifle.threads;

import rifle.Rifle;
import rifle.utils.Utils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Huyemt
 */

public class ConsoleThread extends Thread {
    private final InputStream inputStream;
    private volatile boolean running = false;

    public ConsoleThread(InputStream inputStream) {
        setDaemon(true);
        this.inputStream = inputStream;
        running = true;
    }

    @Override
    public void run() {
        Scanner scanner;
        while (running) {
            Rifle.getInstance().getLogger().print("Rifle> ");
            scanner = new Scanner(inputStream);

            if (scanner.hasNext()) {
                String[] cmd = Utils.spiltCommand(scanner.nextLine());
                if (cmd.length == 0)
                    continue;

                if (!Rifle.getInstance().getCommandMap().execute(cmd[0], cmd[1])) {
                    Rifle.getInstance().getLogger().println("command `{name}` does not exists.".replace("{name}", cmd[0]));
                }
            }
        }
    }

    public void stopIt() {
        close();
        running = false;
    }

    private void close() {

        // unregister commands
        String[] commands = Rifle.getInstance().getCommandMap().getAllCommandNames().toArray(new String[0]);
        for (String commmand : commands) {
            Rifle.getInstance().getCommandMap().unregister(commmand);
        }

        // closing all modules
        Rifle.getInstance().getModuleManager().closeModules();
    }
}
