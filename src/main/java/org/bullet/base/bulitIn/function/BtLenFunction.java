package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByte;
import org.bullet.base.types.BtByteString;
import org.bullet.base.types.BtList;
import org.bullet.base.types.BtDictionary;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.math.BigDecimal;
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
            return new BigDecimal(((BtList) obj).size());
        }

        if (obj instanceof BtDictionary) {
            return new BigDecimal(((BtDictionary) obj).size());
        }

        if (obj instanceof String) {
            return new BigDecimal(((String) obj).length());
        }

        if (obj instanceof BtByteString) {
            return ((BtByteString) obj).size();
        }

        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValueExact();
        }

        if (obj instanceof BtByte) {
            return 1;
        }

        throw new BulletException(String.format("Length of \"%s\" type is not supported", obj.getClass().getSimpleName()));
    }
}
