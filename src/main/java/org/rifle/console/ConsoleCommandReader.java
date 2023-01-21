package org.rifle.console;

import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;
import org.rifle.Rifle;
import org.rifle.command.CommmandParser;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class ConsoleCommandReader extends Thread {
    private final LineReader commandReader;

    public ConsoleCommandReader(LineReader lineReader) {
        this.commandReader = lineReader;
        setName("Rifle-CommandReader");
    }

    /**
     * 在 `Console` 的存活期内, 一直监听用户输入的命令
     *
     * During the lifetime of the `Console`, it has been listening to the commands input by users.
     */
    @Override
    public final void run() {
        String line;
        String[] cmd;
        Argument argument;

        while (Rifle.getInstance().getConsole().isRunning()) {
            try {
                if ((line = commandReader.readLine(TextFormat.STYLE_UNDERLINE + "rifle" + TextFormat.RESET + (Rifle.getInstance().getConsole().isMain() ? "" : " (" + TextFormat.FONT_RED + TextFormat.STYLE_BOLD + Rifle.getInstance().getConsole().getModule().getModuleDescription().getName() + TextFormat.RESET + ")") + " > ")) != null) {
                    cmd = CommmandParser.splitCommand(line.trim());
                    argument = new Argument(cmd[1].trim());
                    if (!Rifle.getInstance().getCommandManager().execute(cmd[0], argument)) {
                        if (Rifle.getInstance().getConsole().isMain())
                            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The command `{}` does not exist in the Rifle.".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + cmd[0] + TextFormat.RESET + TextFormat.FONT_RED));
                        else if (!Rifle.getInstance().getConsole().getModule().getCommandManager().execute(cmd[0], argument))
                            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The command `{}` does not exist in the Rifle and the Module ".replace("{}", TextFormat.FONT_BLUE.toString() + TextFormat.STYLE_BOLD + cmd[0] + TextFormat.RESET + TextFormat.FONT_RED) + "`" + Rifle.getInstance().getConsole().getModule().getModuleDescription().getName() + "`");
                    }
                }
            } catch (UserInterruptException e) {
                if (Rifle.getInstance().getConsole().isMain())
                    Rifle.getInstance().getCommandManager().execute("exit");
                else
                    Rifle.getInstance().getCommandManager().execute("quit");
            }
        }
    }

    /**
     * 隐藏输入光标
     *
     * Hide input cursor.
     */
    public synchronized final void stash() {
        /*
         清除输出在日志内容之前的光标内容
         Clear the cursor content output before the log content.
         */
        commandReader.getTerminal().writer().print("\u001b[1G\u001b[K");
    }

    /**
     * 显示输入光标
     *
     * Display input cursor.
     */
    public synchronized final void unstash() {
        /*
        为了防止多线程输出影响输入格式，在日志输出完之后，将光标以及内容打印出来，"\u001b[1A" 将光标上移一行。原因是 `printAbove()` 是 `println()` 实现的
        In order to prevent the multi-thread output from affecting the input format, after the log output, print the cursor and the contents, and "\u001b[1A" move the cursor up one line. The reason is that `printAbove()` is implemented by `println()`.
         */
        commandReader.printAbove("\u001b[1A");
    }

    /**
     * 获取 JLine 的输入行监听器
     *
     * Gets JLine's input line listener.
     *
     * @return LineReader
     */
    public final LineReader getLineReader() {
        return commandReader;
    }
}
