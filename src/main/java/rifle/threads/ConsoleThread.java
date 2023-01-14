package rifle.threads;

import rifle.Rifle;
import rifle.module.ModuleBase;
import rifle.utils.Utils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Huyemt
 */

public class ConsoleThread extends Thread {
    private final InputStream inputStream;
    private volatile boolean running;
    private ModuleBase module;

    public ConsoleThread(InputStream inputStream) {
        setDaemon(true);
        this.inputStream = inputStream;
        module = null;
        running = true;
    }

    @Override
    public void run() {
        Scanner scanner;
        while (running) {
            Rifle.getInstance().getLogger().print(isMain() ? "Rifle> " : "Rifle[{}]> ".replace("{}", module.getModuleDescription().getName()));
            scanner = new Scanner(inputStream);

            if (scanner.hasNext()) {
                String[] cmd = Utils.spiltCommand(scanner.nextLine());
                if (cmd.length == 0)
                    continue;

                if (!Rifle.getInstance().getCommandMap().execute(cmd[0], cmd[1])) {
                    if (!isMain()) {
                        if (!Rifle.getInstance().getModuleManager().existsModule(module.getModuleDescription().getName())) {
                            Rifle.getInstance().getLogger().error("Module \"{}\" was closed. Automatic exit this module.");
                            setMain();
                            continue;
                        }

                        if (!module.getCommandMap().execute(cmd[0], cmd[1]))
                            Rifle.getInstance().getLogger().println("command `{name}` does not exists in Rifle and Module \"{module}\".".replace("{name}", cmd[0]).replace("{module}", module.getModuleDescription().getName()));
                        continue;
                    }
                    Rifle.getInstance().getLogger().println("command `{name}` does not exists in Rifle.".replace("{name}", cmd[0]));
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

    public final boolean isMain() {
        return module == null;
    }

    public final ModuleBase getModule() {
        return module;
    }

    public final synchronized boolean setModule(ModuleBase moduleBase) {
        if (moduleBase.isSelected())
            return false;

        if (moduleBase.isSuspended())
            return false;

        module = moduleBase;
        return true;
    }

    public final synchronized boolean setMain() {
        if (isMain())
            return false;

        module = null;
        return true;
    }
}
