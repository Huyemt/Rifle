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
        System.out.println("[INFO][{name}] ".replace("{name}", this.moduleName).concat(message));
    }

    @Override
    public void warning(String message) {
        System.out.println("[WARNING][{name}] ".replace("{name}", this.moduleName).concat(message));
    }
}
