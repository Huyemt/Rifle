package org.bullet;

import org.bullet.base.components.BtFunction;
import org.bullet.base.components.BtInterface;
import org.bullet.base.components.BtVariable;
import org.bullet.exceptions.*;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.common.FileCorruptingExceiption;
import org.bullet.exceptions.common.ParsingException;
import org.bullet.exceptions.common.UnderfineException;
import org.bullet.interpreter.BulletRuntime;
import org.bullet.interpreter.Interpreter;

import java.io.File;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class CompiledBullet {
    private final Interpreter interpreter;
    public final BulletRuntime runtime;
    private Object result;
    private boolean evaled;

    public CompiledBullet(String source, BulletRuntime runtime) throws ParsingException {
        this.runtime = runtime;
        interpreter = new Interpreter(source, this.runtime);
        result = null;
        evaled = false;
    }

    public CompiledBullet(File file, BulletRuntime runtime) throws ParsingException, IOException, RuntimeException, FileCorruptingExceiption {
        this.runtime = runtime;
        interpreter = new Interpreter(file, this.runtime);
        result = null;
        evaled = false;
    }

    public final Object eval() throws RuntimeException {
        if (evaled) {
            return result;
        }

        evaled = true;

        return (result = interpreter.eval());
    }

    public final boolean existsInterface(String name) {
        return runtime.existsInterface(name);
    }

    public final BtInterface findInterface(String name) throws UnderfineException {
        return runtime.findInterface(name);
    }

    public final void invokeInterface(String name) throws RuntimeException, UnderfineException {
        runtime.findInterface(name).invoke();
    }

    public final boolean existsFunction(String name) {
        return runtime.existsFunction(name);
    }

    public final BtFunction findFunction(String name) throws UnderfineException {
        return runtime.findFunction(name);
    }

    public final Object invokeFunction(String name, Object ... args) throws BulletException {
        return findFunction(name).invokeFV(args);
    }

    public final boolean existsVariable(String name) {
        return runtime.existsVariable(name);
    }

    public final BtVariable findVariable(String name) throws UnderfineException {
        return runtime.findVariable(name);
    }

    public final boolean existsAttribute(String name) {
        return runtime.existsAttribute(name);
    }

    public final Object findAttribute(String name) throws UnderfineException {
        return runtime.findAttribute(name);
    }
}
