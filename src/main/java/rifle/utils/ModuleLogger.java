package rifle.utils;

/**
 * @author Huyemt
 */

public class ModuleLogger extends Logger {
    private final String moduleName;

    public ModuleLogger(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    @Override
    public void info(String message) {
        System.out.println(TextFormat.STYLE_BOLD.toString() + TextFormat.FONT_GREEN + "[INFO]" + TextFormat.STYLE_RESET + TextFormat.FONT_WHITE + "[" + this.moduleName + "]" + TextFormat.FONT_BLUE + " -> " + TextFormat.STYLE_RESET + message + TextFormat.STYLE_RESET);
    }

    @Override
    public void warning(String message) {
        System.out.println(TextFormat.STYLE_BOLD.toString() + TextFormat.FONT_YELLOW + "[WARNING]" + TextFormat.STYLE_RESET + TextFormat.FONT_WHITE + "[" + this.moduleName + "]" + TextFormat.FONT_BLUE + " -> " + TextFormat.STYLE_RESET + message + TextFormat.STYLE_RESET);
    }

    @Override
    public void error(String message) {
        System.out.println(TextFormat.STYLE_BOLD.toString() + TextFormat.FONT_RED + "[ERROR]" + TextFormat.STYLE_RESET + TextFormat.FONT_WHITE + "[" + this.moduleName + "]" + TextFormat.FONT_BLUE + " -> " + TextFormat.STYLE_RESET + message + TextFormat.STYLE_RESET);
    }
}
