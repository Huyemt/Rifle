package org.rifle.console.logger;

import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class ModuleLogger extends Logger {
    protected final String moduleName;

    public ModuleLogger(String name) {
        moduleName = name;
    }

    @Override
    public void info(String value) {
        println(TextFormat.FONT_GREEN.toString() + TextFormat.STYLE_BOLD + "[INFO]" + TextFormat.RESET + "(" + TextFormat.FONT_RED + TextFormat.STYLE_BOLD + moduleName + TextFormat.RESET + ")" + TextFormat.FONT_BLUE + TextFormat.STYLE_BOLD + " -> " + TextFormat.RESET + value);
    }

    @Override
    public void warning(String value) {
        println(TextFormat.FONT_YELLOW.toString() + TextFormat.STYLE_BOLD + "[WARNING]" + TextFormat.RESET + "(" + TextFormat.FONT_RED + TextFormat.STYLE_BOLD + moduleName + TextFormat.RESET + ")" + TextFormat.FONT_BLUE + TextFormat.STYLE_BOLD + " -> " + TextFormat.RESET + value);
    }

    @Override
    public void error(String value) {
        println(TextFormat.FONT_RED.toString() + TextFormat.STYLE_BOLD + "[ERROR]" + TextFormat.RESET + "(" + TextFormat.FONT_RED + TextFormat.STYLE_BOLD + moduleName + TextFormat.RESET + ")" + TextFormat.FONT_BLUE + TextFormat.STYLE_BOLD + " -> " + TextFormat.RESET + value);
    }
}
