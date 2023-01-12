package rifle.utils;

/**
 * @author Huyemt
 */

public abstract class Logger {

    public abstract void info(String message);
    public abstract void warning(String message);

    public final void print(String message) {
        System.out.println(message);
    }
}
