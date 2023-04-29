package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class BtEncURLFunction extends BtFunction {
    public BtEncURLFunction(BulletRuntime runtime) {
        super("urlE", runtime);
    }

    @Override
    public Object invokeFV(Object... args) throws BulletException {
        if (args.length < 1) {
            throw new BulletException("Missing parameter");
        }

        if (args.length == 1) {
            if (!(args[0] instanceof String)) {
                throw new BulletException("Only support URL encoded string");
            }

            return URLEncoder.encode(args[0].toString(), StandardCharsets.UTF_8);
        } else {
            BtArray array = new BtArray();

            for (Object v : args) {
                if (!(v instanceof String)) {
                    throw new BulletException("Only support URL encoded string");
                }

                array.vector.add(URLEncoder.encode(v.toString(), StandardCharsets.UTF_8));
            }

            return array;
        }
    }
}
