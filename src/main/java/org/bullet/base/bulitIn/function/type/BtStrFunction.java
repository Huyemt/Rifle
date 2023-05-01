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

public class BtStrFunction extends BtBulitInFunction {
    public BtStrFunction(BulletRuntime runtime) {
        super("str", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");

        if (obj instanceof String) {
            return obj;
        }

        if (obj instanceof BtByte) {
            return (char) ((BtByte) obj).getValue();
        }

        if (obj instanceof BtByteString) {
            return ((BtByteString) obj).translate();
        }

        return obj.toString();
    }
}
