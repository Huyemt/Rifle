package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;
import org.huyemt.crypto4j.digest.AES;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class BtEncAESFunction extends BtFunction {

    public BtEncAESFunction(BulletRuntime runtime) {
        super("aesE", runtime);
    }

    @Override
    public Object invokeFV(Object... args) throws BulletException {
        if (args.length > 5) {
            throw new BulletException("Parameter is out of the specified range");
        }

        if (args.length < 2) {
            throw new BulletException("Missing parameter");
        }

        String content;
        String key;

        if (!(args[0] instanceof String || args[0] instanceof BigDecimal)) {
            throw new BulletException("The encrypted content must be a string or a number");
        }

        if (!(args[1] instanceof String)) {
            throw new BulletException("The encrypted content must be a string");
        }

        content = args[0].toString();
        key = args[1].toString();

        if (args.length == 2) {
            return Crypto4J.AES.encrypt(content, key);
        } else if (args.length == 3) {
            // Iv
            if (args[2] instanceof String) {
                return Crypto4J.AES.encrypt(content, key, AES.Mode.ECB, AES.Padding.PKCS5_PADDING, args[2].toString());
            }

            // Mode
            if (args[2] instanceof BigDecimal) {
                return Crypto4J.AES.encrypt(content, key, getMode(((BigDecimal) args[2]).intValueExact()));
            }

            throw new BulletException("Unknown behavior");
        } else if (args.length == 4) {
            // Iv and Mode
            if (args[2] instanceof String && args[3] instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal) args[3];
                AES.Mode mode = getMode(decimal.intValueExact());

                return Crypto4J.AES.encrypt(content, key, mode, AES.Padding.PKCS5_PADDING, args[2].toString());
            }

            // Mode and Iv || Mode and Padding
            if (args[2] instanceof BigDecimal) {
                AES.Mode mode = getMode(((BigDecimal) args[2]).intValueExact());

                // Iv
                if (args[3] instanceof String) {
                    return Crypto4J.AES.encrypt(content, key, mode, AES.Padding.PKCS5_PADDING, args[3].toString());
                } else if (args[3] instanceof BigDecimal) {
                    // Padding
                    return Crypto4J.AES.encrypt(content, key, mode, getPadding(((BigDecimal) args[3]).intValueExact()));
                }

            }

            throw new BulletException("Unknown behavior");
        } else {

            // Iv Mode Padding
            if (args[2] instanceof String && args[3] instanceof BigDecimal && args[4] instanceof BigDecimal) {
                return Crypto4J.AES.encrypt(content, key, getMode(((BigDecimal) args[3]).intValueExact()), getPadding(((BigDecimal) args[4]).intValueExact()), args[2].toString());
            }

            if (args[2] instanceof BigDecimal) {
                AES.Mode mode = getMode(((BigDecimal) args[2]).intValueExact());

                // Iv
                if (args[3] instanceof String) {
                    String iv = args[3].toString();

                    // Padding
                    if (args[4] instanceof BigDecimal) {
                        return Crypto4J.AES.encrypt(content, key, mode, getPadding(((BigDecimal) args[4]).intValueExact()), iv);
                    }

                } else if (args[3] instanceof BigDecimal) {
                    // Padding
                    AES.Padding padding = getPadding(((BigDecimal) args[3]).intValueExact());

                    // Iv
                    if (args[4] instanceof String) {
                        return Crypto4J.AES.encrypt(content, key, mode, padding, args[4].toString());
                    }
                }

            }

            throw new BulletException("Unknown behavior");
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
