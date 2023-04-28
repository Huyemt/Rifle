package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class BtDecBase64Function extends BtFunction {
    public BtDecBase64Function(BulletRuntime runtime) {
        super("base64D", runtime);
    }

    @Override
    public Object invokeFV(Object... args) throws BulletException {
        if (args.length == 0) {
            throw new BulletException(String.format("Parameter missing for the function \"%s\"", funcName));
        }

        if (args.length == 1) {
            if (!(args[0] instanceof String) && !(args[0] instanceof BtArray) && !(args[0] instanceof BigDecimal)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (args[0] instanceof BtArray) {
                return decryptArr((BtArray) args[0]);
            }

            return Crypto4J.Base64.decrypt(args[0].toString());
        }

        BtArray array = new BtArray();
        for (Object obj : args) {
            if (!(obj instanceof String) && !(obj instanceof BtArray) && !(obj instanceof BigDecimal)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (obj instanceof BtArray) {
                array.vector.add(decryptArr((BtArray) obj));
                continue;
            }

            array.vector.add(Crypto4J.Base64.decrypt(obj.toString()));
        }

        return array;
    }

    private BtArray decryptArr(BtArray array) throws BulletException {
        BtArray r = new BtArray();

        for (Object v : array.vector) {
            if (!(v instanceof String) && !(v instanceof BtArray) && !(v instanceof BigDecimal)) {
                throw new BulletException(String.format("Only strings, numbers and arrays are supported for the function \"%s\"", funcName));
            }

            if (v instanceof BtArray) {
                r.vector.add(decryptArr((BtArray) v));
                continue;
            }

            r.vector.add(Crypto4J.Base64.decrypt(v.toString()));
        }

        return r;
    }
}
