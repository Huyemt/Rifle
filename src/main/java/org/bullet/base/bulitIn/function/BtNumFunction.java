package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtNumFunction extends BtBulitInFunction {
    public BtNumFunction(BulletRuntime runtime) {
        super("num", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        return new BigDecimal(args.get("obj").toString());
    }
}
