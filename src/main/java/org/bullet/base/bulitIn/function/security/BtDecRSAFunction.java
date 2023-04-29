package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;
import org.huyemt.crypto4j.digest.RSA;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class BtDecRSAFunction extends BtFunction {
    public BtDecRSAFunction(BulletRuntime runtime) {
        super("rsaD", runtime);
    }

    @Override
    public Object invokeFV(Object... args) throws BulletException {
        if (args.length > 3) {
            throw new BulletException("Parameter is out of the specified range");
        }

        if (args.length < 2) {
            throw new BulletException("Missing parameter");
        }

        if (!(args[0] instanceof String || args[0] instanceof BigDecimal)) {
            throw new BulletException("The decrypted content must be a string or a number");
        }

        String content = args[0].toString();

        if (args.length == 2) {
            // key
            if (args[1] instanceof String) {
                return Crypto4J.RSA.decrypt(content, args[1].toString());
            }
        } else {
            // key
            if (args[1] instanceof String && args[2] instanceof BigDecimal) {
                RSA.DecryptConfig config = new RSA.DecryptConfig();
                config.PADDING = getPadding(((BigDecimal) args[2]).intValueExact());
                return Crypto4J.RSA.decrypt(content, args[1].toString(), config);
            }

            // padding
            if (args[1] instanceof BigDecimal && args[2] instanceof String) {
                RSA.DecryptConfig config = new RSA.DecryptConfig();
                config.PADDING = getPadding(((BigDecimal) args[1]).intValueExact());
                return Crypto4J.RSA.decrypt(content, args[2].toString(), config);
            }
        }

        throw new BulletException("Unknown behavior");
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
