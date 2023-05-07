package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtNull;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;
import org.huyemt.crypto4j.digest.AES;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtEncAESFunction extends BtBulitInFunction {

    public BtEncAESFunction(BulletRuntime runtime) {
        super("aesE", runtime);

        args.put("content", null);
        args.put("key", null);
        args.put("mode", 1);
        args.put("padding", 1);
        args.put("iv", BulletRuntime.BTNULL);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object content = args.get("content");
        Object key = args.get("key");
        Object mode = args.get("mode");
        Object padding = args.get("padding");
        Object iv = args.get("iv");

        if ((!(content instanceof String || content instanceof BtNumber))) {
            throw new BulletException(String.format("Parameter type \"%s\" is not supported for AES encryption", content.getClass().getSimpleName()));
        }

        if (!(key instanceof String)) {
            throw new BulletException("AES key must be a string");
        }

        if (!(mode instanceof BtNumber)) {
            throw new BulletException("Encryption mode must be numeric");
        }

        if (!(padding instanceof BtNumber)) {
            throw new BulletException("Padding mode must be numeric");
        }

        if (!(iv instanceof String) && !(iv instanceof BtNull)) {
            throw new BulletException("Iv must be a string or null");
        }

        try {
            if (!(iv instanceof BtNull) && ((String) iv).length() > 0)
                return Crypto4J.AES.encrypt(content.toString(), key.toString(), getMode(((BtNumber) mode).toInteger()), getPadding(((BtNumber) padding).toInteger()), iv.toString());
            else
                return Crypto4J.AES.encrypt(content.toString(), key.toString(), getMode(((BtNumber) mode).toInteger()), getPadding(((BtNumber) padding).toInteger()));
        } catch (Exception e) {
            throw new BulletException(e.getMessage());
        }
    }

    private AES.Mode getMode(int n) throws BulletException {
        switch (n) {
            case 0:
                return AES.Mode.CBC;
            case 1:
                return AES.Mode.ECB;
            case 2:
                return AES.Mode.CTR;
            case 3:
                return AES.Mode.OFB;
            case 4:
                return AES.Mode.CFB;
            default:
                throw new BulletException("Encryption mode not supported");
        }
    }

    private AES.Padding getPadding(int n) throws BulletException {
        switch (n) {
            case 0:
                return AES.Padding.NO_PADDING;
            case 1:
                return AES.Padding.PKCS5_PADDING;
            case 2:
                return AES.Padding.ISO10126_PADDING;
            default:
                throw new BulletException("Encryption padding not supported");
        }
    }
}
