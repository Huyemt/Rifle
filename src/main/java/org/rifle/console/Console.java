package org.rifle.console;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.rifle.Rifle;
import org.rifle.console.logger.MainLogger;
import org.rifle.module.IModule;

import java.io.IOException;

/**
 * Console 负责消息输出与命令监听<br><br>
 *
 * The Console class of Rifle is responsible for message output and command listening.
 *
 * @author Huyemt
 */

public class Console {
    private boolean running;
    private Terminal terminal;
    private ConsoleCommandReader commandReader;
    private MainLogger logger;
    private IModule module = null;

    public Console() {
        running = true;

        try {
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .jansi(true)
                    .build();
            this.logger = new MainLogger();
            this.commandReader = new ConsoleCommandReader(LineReaderBuilder.builder()
                    .terminal(this.terminal)
                    .option(LineReader.Option.HISTORY_IGNORE_SPACE, true)
                    .option(LineReader.Option.HISTORY_IGNORE_DUPS, true)
                    .option(LineReader.Option.HISTORY_BEEP, false)
                    .completer(new TabCompleter())
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 `Terminal` 实例<br><br>
     *
     * Get the instance of `Terminal`
     *
     * @return Terminal
     */
    public final Terminal getTerminal() {
        return this.terminal;
    }

    /**
     * 获取 `ConsoleCommandReader` 实例<br><br>
     *
     * Get the instance of `ConsoleCommandReader`
     *
     * @return ConsoleCommandReader
     */
    public final ConsoleCommandReader getCommandReader() {
        return commandReader;
    }

    /**
     * 查询 Rifle 是否在运行<br><br>
     *
     * Query whether the Rifle process is running.
     *
     * @return boolean
     */
    public final boolean isRunning() {
        return running;
    }

    /**
     * 获取 `MainLogger` 实例, 用于日志输出<br><br>
     *
     * Get the instance of `MainLogger` for log output.
     *
     * @return MainLogger
     */
    public final MainLogger getLogger() {
        return logger;
    }

    /**
     * 关闭 Rifle<br><br>
     *
     * Closing the Rifle process.
     */
    public final void shutdown() {
        Rifle.getInstance().getModuleManager().unloadModules();
        
        running = false;
    }

    /**
     * 获取当前选中的模块<br><br>
     *
     * Get the currently selected module.
     *
     * @return IModule
     */
    public final IModule getModule() {
        return module;
    }

    /**
     * 选用一个模块<br><br>
     *
     * Select a module
     *
     * @param name
     * @return boolean
     */
    public final boolean setModule(String name) {
        if (name == null)
            return false;

        if (name.equalsIgnoreCase("rifle")) {
            resetModule();
            return true;
        }

        if (!Rifle.getInstance().getModuleManager().exists(name))
            return false;

        IModule module = Rifle.getInstance().getModuleManager().get(name);

        if (!module.isUserCanSelect())
            return false;

        if (module.isSelected())
            return true;

        if (this.module != null)
            this.module.setSelected(false);
        this.module = module;
        this.module.setSelected(true);
        return true;
    }

    /**
     * 重置正在选用的模块 (退出当前模块)<br><br>
     *
     * Reset the module being selected (exit the current module)
     */
    public final void resetModule() {
        if (module != null)
            module.setSelected(false);
        module = null;
    }

    /**
     * 当前模块是否为主模块Rifle<br><br>
     *
     * Whether the current module is the main module Rifle?
     *
     * @return boolean
     */
    public final boolean isMain() {
        return module == null;
    }

    public final void clearScreen() {
        getLogger().println("\u001b[2J");
    }
}
