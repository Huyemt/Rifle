package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;
import org.huyemt.crypto4j.digest.RSA;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtDecRSAFunction extends BtBulitInFunction {
    public BtDecRSAFunction(BulletRuntime runtime) {
        super("rsaD", runtime);
        args.put("content", null);
        args.put("key", null);
        args.put("padding", 1);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object content = args.get("content");
        Object key = args.get("key");
        Object padding = args.get("padding");

        if ((!(content instanceof String))) {
            throw new BulletException(String.format("Parameter type \"%s\" is not supported for RSA decryption", content.getClass().getSimpleName()));
        }

        if (!(key instanceof String)) {
            throw new BulletException("RSA privateKey must be a string");
        }

        if (!(padding instanceof BtNumber)) {
            throw new BulletException("Padding mode must be numeric");
        }

        RSA.DecryptConfig config = new RSA.DecryptConfig();
        config.PADDING = getPadding((int) ((BtNumber) padding).toLong());

        try {
            return Crypto4J.RSA.decrypt((String) content, (String) key, config);
        } catch (Exception e) {
            throw new BulletException(e.getMessage());
        }
    }

    private RSA.Padding getPadding(int n) throws BulletException {
        switch (n) {
            case 0:
                return RSA.Padding.NO_PADDING;
            case 1:
                return RSA.Padding.PKCS1_PADDING;
            case 2:
                return RSA.Padding.PKCS1_OAEP_PADDING;
            default:
                throw new BulletException("Decryption padding not supported");
        }
    }
}
