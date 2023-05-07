package org.bullet.base.bulitIn.function.type;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByteString;
import org.bullet.base.types.BtNull;
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
        args.put("encoding", "utf-8");
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("obj");
        Object encoding = args.get("encoding");

        if (!(encoding instanceof String || encoding instanceof BtNull)) {
            throw new BulletException("Encodings must be a string");
        }

        encoding = encoding instanceof BtNull ? "utf-8" : encoding;

        try {

            if (r instanceof String) {
                return new BtByteString(((String) r).getBytes((String) encoding));
            }

            if (r instanceof BtByteString) {
                return r;
            }

            if (r instanceof BtNumber) {
                return new BtByteString(r.toString().getBytes((String) encoding));
            }

        } catch (Exception e) {
            throw new BulletException(e.getMessage());
        }

        throw new BulletException(String.format("The type \"%s\"to byte type is not supported", r.getClass().getSimpleName()));
    }
}
