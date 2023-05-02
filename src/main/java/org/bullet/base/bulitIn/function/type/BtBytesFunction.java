package org.bullet.base.bulitIn.function.type;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByteString;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtBytesFunction extends BtBulitInFunction {
    public BtBytesFunction(BulletRuntime runtime) {
        super("bytes", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("obj");

        if (r instanceof String) {
            return new BtByteString(r.toString());
        }

        if (r instanceof BtByteString) {
            return r;
        }

        if (r instanceof BtNumber) {
            return new BtByteString(r.toString());
        }

        throw new BulletException(String.format("The type \"%s\"to byte type is not supported", r.getClass().getSimpleName()));
    }
}
