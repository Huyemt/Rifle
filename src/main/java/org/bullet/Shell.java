package org.bullet;

import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Interpreter;

import java.io.File;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class Shell {
    public static void main(String[] args) {
        try {
            Interpreter interpreter = new Interpreter(new File("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\docs\\chinese\\bullet\\test.bt"));
            interpreter.eval();
        } catch (ParsingException e) {
            Reporter.report(e.position, e.getMessage());
        } catch (RuntimeException e) {
            Reporter.report(e.position, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
