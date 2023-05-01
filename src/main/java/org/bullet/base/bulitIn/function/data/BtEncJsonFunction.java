package org.bullet.base.bulitIn.function.data;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtDictionary;
import org.bullet.base.types.BtList;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.huyemt.json4j.Json4J;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtEncJsonFunction extends BtBulitInFunction {
    public BtEncJsonFunction(BulletRuntime runtime) {
        super("jsonE", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");

        if (obj instanceof BtDictionary) {
            return Json4J.toJson(((BtDictionary) obj).toMap());
        }

        if (obj instanceof BtList) {
            return Json4J.toJson(((BtList) obj).toArray());
        }

        throw new BulletException("Only dictionaries and lists can be converted into json strings");
    }
}
