package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtList;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtMd5Function extends BtBulitInFunction {

    public BtMd5Function(BulletRuntime runtime) {
        super("md5", runtime);
        args.put("content", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("content");

        if (!(r instanceof String) && !(r instanceof BtList) && !(r instanceof BigDecimal)) {
            throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
        }

        if (r instanceof BtList) {
            return md5Arr((BtList) r);
        }

        return Crypto4J.MD5.encrypt(r.toString());
    }

    private BtList md5Arr(BtList array) throws BulletException {
        BtList r = new BtList();

        for (Object v : array.values()) {
            if (!(v instanceof String) && !(v instanceof BtList) && !(v instanceof BigDecimal)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (v instanceof BtList) {
                r.add(md5Arr((BtList) v));
                continue;
            }

            r.add(Crypto4J.MD5.encrypt(v.toString()));
        }

        return r;
    }
}
