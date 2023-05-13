package org.bullet.base.bulitIn.function.type;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtTypeofFunction extends BtBulitInFunction {
    public BtTypeofFunction(BulletRuntime runtime) {
        super("typeof", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        return args.get("obj").getClass().getSimpleName();
    }
}
