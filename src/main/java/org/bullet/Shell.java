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
            String script = "124832148 / (22 - 22)";
            Interpreter interpreter = new Interpreter(script);
            System.out.println(interpreter.eval());
        } catch (ParsingException e) {
            Reporter.report(e.position, "ParsingException", e.getMessage());
        } catch (RuntimeException e) {
            Reporter.report(e.position, e.getMessage());
        }
    }
}
