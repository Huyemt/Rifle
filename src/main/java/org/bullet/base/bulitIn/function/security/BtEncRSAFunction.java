package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtDictionary;
import org.bullet.base.types.BtNull;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;
import org.huyemt.crypto4j.digest.RSA;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtEncRSAFunction extends BtBulitInFunction {
    public BtEncRSAFunction(BulletRuntime runtime) {
        super("rsaE", runtime);
        args.put("content", null);
        args.put("key", BulletRuntime.BTNULL);
        args.put("padding", 1);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object content = args.get("content");
        Object key = args.get("key");
        Object padding = args.get("padding");

        if ((!(content instanceof String || content instanceof BtNumber))) {
            throw new BulletException(String.format("Parameter type \"%s\" is not supported for RSA encryption", content.getClass().getSimpleName()));
        }

        if (!(key instanceof String) && !(key instanceof BtNull)) {
            throw new BulletException("RSA publicKey must be a string or null");
        }

        if (!(padding instanceof BtNumber)) {
            throw new BulletException("Padding mode must be numeric");
        }

        try {
            if (key instanceof BtNull || ((String) key).length() == 0) {
                RSA.Result result = Crypto4J.RSA.randomEncrypt(content.toString());
                BtDictionary dictionary = new BtDictionary();
                dictionary.add("result", result.result);
                dictionary.add("publicKey", result.publicKey);
                dictionary.add("privateKey", result.privateKey);
                dictionary.add("padding", 1);

                return dictionary;
            } else {
                RSA.EncryptConfig config = new RSA.EncryptConfig();
                config.PADDING = getPadding(((BtNumber) padding).toInteger());

                return Crypto4J.RSA.encrypt(content.toString(), key.toString(), config);
            }
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
                throw new BulletException("Encryption padding not supported");
        }
    }
}
