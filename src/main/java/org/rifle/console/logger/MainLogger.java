package org.rifle.console.logger;

import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class MainLogger extends Logger {
    @Override
    public final void info(Object value) {
        println(TextFormat.FONT_GREEN.toString() + TextFormat.STYLE_BOLD + "[INFO]" + TextFormat.FONT_BLUE + " -> " + TextFormat.RESET + value);
    }

    @Override
    public final void warning(Object value) {
        println(TextFormat.FONT_YELLOW.toString() + TextFormat.STYLE_BOLD + "[WARNING]" + TextFormat.FONT_BLUE + " -> " + TextFormat.RESET + value);
    }

    @Override
    public final void error(Object value) {
        println(TextFormat.FONT_RED.toString() + TextFormat.STYLE_BOLD + "[ERROR]" + TextFormat.FONT_BLUE + " -> " + TextFormat.RESET + value);
    }
}
