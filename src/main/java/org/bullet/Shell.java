package org.bullet;

import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Interpreter;

/**
 * @author Huyemt
 */

public class Shell {
    public static void main(String[] args) {
        try {
            String script = "198461826411243 * 1234";
            Interpreter interpreter = new Interpreter(script);
            System.out.println(interpreter.eval());
        } catch (ParsingException e) {
            Reporter.report(e.position, e.getMessage());
        } catch (RuntimeException e) {
            Reporter.report(e.position, e.getMessage());
        }
    }
}
