package org.bullet;

import org.bullet.compiler.ast.nodes.AssignNode;
import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
import org.rifle.utils.Utils;

import java.io.IOException;

/**
 * @author Huyemt
 */

public class Shell {
    public static void main(String[] args) {
        try {
            String script = Utils.readFile("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\docs\\chinese\\bullet\\test.bt");
            Interpreter interpreter = new Interpreter(script);
            System.out.println(interpreter.eval());
        } catch (ParsingException e) {
            Reporter.report(e.position, e.getMessage());
        } catch (RuntimeException e) {
            Reporter.report(e.position, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
