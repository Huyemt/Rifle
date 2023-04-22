package org.bullet;

import org.bullet.base.types.BtArray;
import org.bullet.base.types.BtDictionary;
import org.bullet.exceptions.*;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.common.ParsingException;
import org.bullet.interpreter.BulletRuntime;

import java.io.File;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class Shell {
    public static void main(String[] args) {
        try {
//            CompiledBullet compiled = new CompiledBullet(new File("E:\\AMyCode\\Tool\\Rifle\\modules\\test.bt"), new BulletRuntime());
//            compiled.eval();
//            compiled.invokeInterface("onSelected");

            CompiledBullet compiledBullet = new CompiledBullet(new File("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\docs\\chinese\\bullet\\test.bt"), new BulletRuntime());
            compiledBullet.eval();

//            Integer[] a = new Integer[]{1, 2, 3, 4, 5};
//            Object aa = new Object[]{a, "你好"};

            //compiledBullet.invokeFunction("testFunction", aa);
        } catch (ParsingException e) {
            System.out.println(Reporter.report(e.getClass().getName(), e.position, e.getMessage()));
        } catch (RuntimeException e) {
            System.out.println(Reporter.report(e.getClass().getName(), e.position, e.getMessage()));
        } catch (IOException | BulletException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
