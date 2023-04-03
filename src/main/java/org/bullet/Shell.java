package org.bullet;

import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class Shell {
    public static void main(String[] args) {
        try {
            String script = "a = 0; 19284719824 / ( 1289417984 * a );";
            Interpreter interpreter = new Interpreter(script);
            System.out.println(interpreter.eval());
        } catch (ParsingException e) {
            Reporter.report(e.position, e.getMessage());
        } catch (RuntimeException e) {
            Reporter.report(e.position, e.getMessage());
        }
    }
}
