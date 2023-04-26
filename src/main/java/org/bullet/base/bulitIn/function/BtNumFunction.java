package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.interpreter.BulletRuntime;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class BtNumFunction extends BtFunction {
    public BtNumFunction(BulletRuntime runtime) {
        super("num", runtime);
    }

    @Override
    public Object invokeFV(Object... args) {
        Object result = null;

        if (args.length == 1) {
            result = new BigDecimal(args[0].toString());
        } else if (args.length > 1) {
            BtArray array = new BtArray();

            for (Object arg : args) array.vector.add(new BigDecimal(arg.toString()));

            result = array;
        }

        return result;
    }
}
