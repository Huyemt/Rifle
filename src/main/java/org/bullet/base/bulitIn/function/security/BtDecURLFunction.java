package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtDecURLFunction extends BtBulitInFunction {
    public BtDecURLFunction(BulletRuntime runtime) {
        super("urlD", runtime);
        args.put("content", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("content");

        if (!(r instanceof String)) {
            throw new BulletException("Only support URL decoded string");
        }

        return URLDecoder.decode(r.toString(), StandardCharsets.UTF_8);
    }
}
