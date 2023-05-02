package org.bullet.base.bulitIn.function.type;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.types.BtByte;
import org.bullet.base.types.BtByteString;
import org.bullet.base.types.BtList;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtNumFunction extends BtBulitInFunction {
    public BtNumFunction(BulletRuntime runtime) {
        super("num", runtime);
        args.put("obj", null);
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        Object obj = args.get("obj");

        if (obj instanceof BtNumber) {
            return obj;
        }

        if (obj instanceof BtByte) {
            return new BtNumber(((BtByte) obj).getValue());
        }

        if (obj instanceof BtByteString) {
            BtByteString byteString = (BtByteString) obj;
            BtList list = new BtList();

            for (int i = 0; i < byteString.size(); i++) {
                list.add(byteString.get(i).getValue());
            }

            return list;
        }

        if (obj instanceof String) {
            return new BtNumber((String) obj);
        }

        throw new BulletException(String.format("The type \"%s\" to numeric type is not supported", obj.getClass().getSimpleName()));
    }
}
