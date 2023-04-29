package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class BtDecURLFunction extends BtFunction {
    public BtDecURLFunction(BulletRuntime runtime) {
        super("urlD", runtime);
    }

    @Override
    public Object invokeFV(Object... args) throws BulletException {
        if (args.length < 1) {
            throw new BulletException("Missing parameter");
        }

        if (args.length == 1) {
            if (!(args[0] instanceof String)) {
                throw new BulletException("Only support URL decoded string");
            }

            return URLDecoder.decode(args[0].toString(), StandardCharsets.UTF_8);
        } else {
            BtArray array = new BtArray();

            for (Object v : args) {
                if (!(v instanceof String)) {
                    throw new BulletException("Only support URL encoded string");
                }

                array.vector.add(URLDecoder.decode(v.toString(), StandardCharsets.UTF_8));
            }

            return array;
        }
    }
}
