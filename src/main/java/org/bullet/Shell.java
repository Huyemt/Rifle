package org.bullet;

import org.bullet.exceptions.*;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.BulletRuntime;

import java.io.File;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class Shell {
    public static void main(String[] args) {
        try {
            CompiledBullet compiled = new CompiledBullet(new File("E:\\AMyCode\\Tool\\Rifle\\modules\\test.bt"), new BulletRuntime());
//            compiled.eval();
//            compiled.invokeInterface("onSelected");
            CompiledBullet compiledBullet = new CompiledBullet("println(\"\\u6597\\u7834\\u82cd\\u7a79\\u6c38\\u8fdc\\u7684\\u795e\")", new BulletRuntime());
            compiledBullet.eval();
        } catch (ParsingException e) {
            System.out.println(Reporter.report(e.getClass().getName(), e.position, e.getMessage()));
        } catch (RuntimeException e) {
            System.out.println(Reporter.report(e.getClass().getName(), e.position, e.getMessage()));
        } catch (IOException | BulletException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
