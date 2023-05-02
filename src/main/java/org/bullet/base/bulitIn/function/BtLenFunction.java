package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.*;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtLenFunction extends BtBulitInFunction {
    public BtLenFunction(BulletRuntime runtime) {
        super("len", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");

        if (obj instanceof BtList) {
            return new BtNumber(((BtList) obj).size());
        }

        if (obj instanceof BtDictionary) {
            return new BtNumber(((BtDictionary) obj).size());
        }

        if (obj instanceof String) {
            return new BtNumber(((String) obj).length());
        }

        if (obj instanceof BtByteString) {
            return ((BtByteString) obj).size();
        }

        if (obj instanceof BtByte) {
            return 1;
        }

        throw new BulletException(String.format("Length of \"%s\" type is not supported", obj.getClass().getSimpleName()));
    }
}
