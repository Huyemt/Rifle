package org.bullet.base.bulitIn.function.type;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByte;
import org.bullet.base.types.BtByteString;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtIsByteFunction extends BtBulitInFunction {
    public BtIsByteFunction(BulletRuntime runtime) {
        super("isbyte", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");
        return obj instanceof BtByte || obj instanceof BtByteString;
    }
}
