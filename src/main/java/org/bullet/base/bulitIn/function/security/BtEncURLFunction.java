package org.bullet.base.bulitIn.function.security;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.crypto4j.Crypto4J;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtEncURLFunction extends BtBulitInFunction {
    public BtEncURLFunction(BulletRuntime runtime) {
        super("urlE", runtime);
        args.put("content", null);
        args.put("mark", true);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object r = args.get("content");
        Object mark = args.get("mark");

        if (!(r instanceof String)) {
            throw new BulletException("Only support URL encoded string");
        }

        if (!(mark instanceof Boolean)) {
            throw new BulletException("Mark parameter type must be Boolean");
        }

        try {
            return Crypto4J.URL.encode((String) r, (boolean) mark);
        } catch (Exception e) {
            throw new BulletException(e.getMessage());
        }
    }
}
