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

public class BtSha512Function extends BtBulitInFunction {
    public BtSha512Function(BulletRuntime runtime) {
        super("sha512", runtime);
        args.put("content", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("content");

        if (!(r instanceof String) && !(r instanceof BtList) && !(r instanceof BigDecimal)) {
            throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
        }

        if (r instanceof BtList) {
            return encryptArr((BtList) r);
        }

        return Crypto4J.SHA512.encrypt(r.toString());
    }

    private BtList encryptArr(BtList array) throws BulletException {
        BtList r = new BtList();

        for (Object v : array.values()) {
            if (!(v instanceof String) && !(v instanceof BtList) && !(v instanceof BigDecimal)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (v instanceof BtList) {
                r.add(encryptArr((BtList) v));
                continue;
            }

            r.add(Crypto4J.SHA512.encrypt(v.toString()));
        }

        return r;
    }
}
