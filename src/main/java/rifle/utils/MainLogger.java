package rifle.utils;


/**
 * @author Huyemt
 */

public class MainLogger extends Logger {

    @Override
    public void info(String message) {
        System.out.println(TextFormat.STYLE_BOLD.toString() + TextFormat.FONT_GREEN + "[INFO]" + TextFormat.STYLE_RESET + TextFormat.FONT_BLUE + " -> " + TextFormat.STYLE_RESET + message + TextFormat.STYLE_RESET);
    }

    @Override
    public void warning(String message) {
        System.out.println(TextFormat.STYLE_BOLD.toString() + TextFormat.FONT_YELLOW + "[WARNING]" + TextFormat.STYLE_RESET + TextFormat.FONT_BLUE + " -> " + TextFormat.STYLE_RESET + message + TextFormat.STYLE_RESET);
    }

    @Override
    public void error(String message) {
        System.out.println(TextFormat.STYLE_BOLD.toString() + TextFormat.FONT_RED + "[ERROR]" + TextFormat.STYLE_RESET + TextFormat.FONT_BLUE + " -> " + TextFormat.STYLE_RESET + message + TextFormat.STYLE_RESET);
    }
}
