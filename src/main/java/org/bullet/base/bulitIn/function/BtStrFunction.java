package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtStrFunction extends BtBulitInFunction {
    public BtStrFunction(BulletRuntime runtime) {
        super("str", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        return args.get("obj").toString();
    }
}
