package org.rifle.console.logger;

import org.rifle.Rifle;
import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public abstract class Logger {
    /**
     * 正常的日志输出<br><br>
     *
     * Normal log output
     *
     * @param value
     */
    public abstract void info(Object value);

    /**
     * 警告的日志输出<br><br>
     *
     * Log output of warnings
     *
     * @param value
     */
    public abstract void warning(Object value);

    /**
     * 致命错误的日志输出<br><br>
     *
     * Log output of errors
     *
     * @param value
     */
    public abstract void error(Object value);

    public synchronized final void println(Object value) {
        Rifle.getInstance().getConsole().getCommandReader().stash();
        Rifle.getInstance().getConsole().getTerminal().writer().println(String.valueOf(value) + TextFormat.RESET);
        Rifle.getInstance().getConsole().getCommandReader().unstash();
        Rifle.getInstance().getConsole().getTerminal().writer().flush();
    }

    public synchronized final void print(Object value) {
        Rifle.getInstance().getConsole().getCommandReader().stash();
        Rifle.getInstance().getConsole().getTerminal().writer().print(String.valueOf(value) + TextFormat.RESET);
        Rifle.getInstance().getConsole().getCommandReader().unstash();
        Rifle.getInstance().getConsole().getTerminal().writer().flush();
    }
}
