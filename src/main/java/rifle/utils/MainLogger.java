package rifle.utils;

/**
 * @author Huyemt
 */

public class MainLogger extends Logger {

    @Override
    public void info(String message) {
        System.out.println("[INFO] -> ".concat(message));
    }

    @Override
    public void warning(String message) {
        System.out.println("[WARNING] -> ".concat(message));
    }

    @Override
    public void error(String message) {
        System.out.println("[ERROR] -> ".concat(message));
    }
}
