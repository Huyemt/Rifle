package org.bullet;

import org.bullet.backend.Interpreter;
import org.bullet.frontend.results.Result;

/**
 * Bullet 运行时<br><br>
 *
 * Bullet's Runtime
 *
 * @author Huyemt
 */

public class Runtime {

    public Runtime() {

    }

    public static void main(String[] args) {
        String test = "219837548921739587 + 124114124 * 7981724"; // 220828193604009363
        Interpreter interpreter = new Interpreter(test);
        Result<?> result = interpreter.evalIt();
        if (result != null) {
            System.out.println(result);
        }
    }
}
