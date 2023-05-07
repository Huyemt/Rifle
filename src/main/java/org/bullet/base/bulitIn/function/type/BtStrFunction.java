package org.bullet.base.bulitIn.function.type;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByte;
import org.bullet.base.types.BtByteString;
import org.bullet.base.types.BtNull;
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
        args.put("encoding", "utf-8");
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");
        Object encoding = args.get("encoding");

        if (obj instanceof String) {
            return obj;
        }

        if (!(encoding instanceof String || encoding instanceof BtNull)) {
            throw new BulletException("Encodings must be a string");
        }

        encoding = encoding instanceof BtNull ? "utf-8" : encoding;

        try {

            if (obj instanceof BtByte) {
                return new String(new byte[]{((BtByte) obj).getValue()}, (String) encoding);
            }

            if (obj instanceof BtByteString) {
                return ((BtByteString) obj).translate((String) encoding);
            }

            return obj.toString();
        } catch (Exception e) {
            throw new BulletException(e.getMessage());
        }
    }
}
