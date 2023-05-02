package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtList;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtDecBase64Function extends BtBulitInFunction {
    public BtDecBase64Function(BulletRuntime runtime) {
        super("base64D", runtime);
        args.put("content", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("content");

        if (!(r instanceof String) && !(r instanceof BtList) && !(r instanceof BtNumber)) {
            throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
        }

        if (r instanceof BtList) {
            return encryptArr((BtList) r);
        }

        return Crypto4J.Base64.decrypt(r.toString());
    }

    private BtList encryptArr(BtList array) throws BulletException {
        BtList r = new BtList();

        for (Object v : array.values()) {
            if (!(v instanceof String) && !(v instanceof BtList) && !(v instanceof BtNumber)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (v instanceof BtList) {
                r.add(encryptArr((BtList) v));
                continue;
            }

            r.add(Crypto4J.Base64.decrypt(v.toString()));
        }

        return r;
    }
}
