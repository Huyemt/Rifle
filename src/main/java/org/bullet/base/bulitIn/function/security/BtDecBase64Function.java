package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByteString;
import org.bullet.base.types.BtList;
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

        if (!(r instanceof String) && !(r instanceof BtList) && !(r instanceof BtByteString)) {
            throw new BulletException(String.format("Only strings, lists and byteStrings are supported for the function \"%s\"", funcName));
        }

        if (r instanceof BtList) {
            return doArr((BtList) r);
        }

        if (r instanceof BtByteString) {
            return new BtByteString(Crypto4J.Base64.decode(((BtByteString) r).toByteArray()));
        }

        return Crypto4J.Base64.decrypt(r.toString());
    }

    private BtList doArr(BtList array) throws BulletException {
        BtList r = new BtList();

        for (Object v : array.values()) {
            if (!(v instanceof String) && !(v instanceof BtList) && !(v instanceof BtByteString)) {
                throw new BulletException(String.format("Only strings, lists and byteStrings are supported for the function \"%s\"", funcName));
            }

            if (v instanceof BtList) {
                r.add(doArr((BtList) v));
                continue;
            }

            if (v instanceof BtByteString) {
                r.add(new BtByteString(Crypto4J.Base64.decode(((BtByteString) v).toByteArray())));
                continue;
            }

            r.add(Crypto4J.Base64.decrypt(v.toString()));
        }

        return r;
    }
}
