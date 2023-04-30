package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtArray;
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

        Object r = args.get("obj");

        if (r instanceof BtArray) {
            return new BigDecimal(((BtArray) r).vector.size());
        }

        if (r instanceof BtDictionary) {
            return new BigDecimal(((BtDictionary) r).vector.size());
        }

        if (r.getClass().isArray()) {
            return new BigDecimal(BtArray.parse((Object[]) r).vector.size());
        }

        if (r instanceof String) {
            return new BigDecimal(((String) r).length());
        }

        throw new BulletException(String.format("Length of \"%s\" type is not supported", r.getClass().getSimpleName()));
    }
}
