package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtArray;
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

        if (!(r instanceof String) && !(r instanceof BtArray) && !(r instanceof BigDecimal)) {
            throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
        }

        if (r instanceof BtArray) {
            return encryptArr((BtArray) r);
        }

        return Crypto4J.SHA512.encrypt(r.toString());
    }

    private BtArray encryptArr(BtArray array) throws BulletException {
        BtArray r = new BtArray();

        for (Object v : array.vector) {
            if (!(v instanceof String) && !(v instanceof BtArray) && !(v instanceof BigDecimal)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (v instanceof BtArray) {
                r.vector.add(encryptArr((BtArray) v));
                continue;
            }

            r.vector.add(Crypto4J.SHA512.encrypt(v.toString()));
        }

        return r;
    }
}
