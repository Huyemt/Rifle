package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.interpreter.BulletRuntime;

/**
 * @author Huyemt
 */

public class BtStrFunction extends BtFunction {
    public BtStrFunction(BulletRuntime runtime) {
        super("str", runtime);
    }

    @Override
    public Object invokeFV(Object... args) {
        Object result = null;

        if (args.length == 1) {
            result = args[0].toString();
        } else if (args.length > 1) {
            BtArray array = new BtArray();

            for (Object arg : args) array.vector.add(arg.toString());

            result = array;
        }

        return result;
    }
}
