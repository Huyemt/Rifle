package org.bullet;

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
            CompiledBullet compiledBullet = new CompiledBullet(new File("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\docs\\chinese\\bullet\\test.bt"), new BulletRuntime());
            compiledBullet.eval();

            compiledBullet.invokeFunction("onLoad");
        } catch (ParsingException e) {
            System.out.println(Reporter.report(e.getClass().getName(), e.position, e.getMessage()));
        } catch (RuntimeException e) {
            System.out.println(Reporter.report(e.getClass().getName(), e.position, e.getMessage()));
        } catch (IOException | BulletException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
