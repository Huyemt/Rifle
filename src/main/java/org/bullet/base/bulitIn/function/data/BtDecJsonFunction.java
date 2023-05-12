package org.bullet.base.bulitIn.function.data;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtType;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.json4j.Json4J;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author Huyemt
 */

public class BtDecJsonFunction extends BtBulitInFunction {
    public BtDecJsonFunction(BulletRuntime runtime) {
        super("jsonD", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");

        if (obj instanceof String) {
            String objS = (String) obj;
            String m = objS.trim().replace("\n", "");

            if (m.startsWith("[")) {
                LinkedList<Object> r = Json4J.parse(objS, LinkedList.class);
                return BtType.toBType(r);
            } else if (m.startsWith("{")) {
                LinkedHashMap<String, Object> r = Json4J.parse(objS, LinkedHashMap.class);
                return BtType.toBType(r);
            }

            throw new BulletException("Abnormal json structure in string");
        }

        throw new BulletException("Only string parsing into json structure is supported");
    }
}
