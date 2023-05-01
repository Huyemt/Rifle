package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

/**
 * @author Huyemt
 */

public class BtPrintlnFunction extends BtBulitInFunction {
    public BtPrintlnFunction(BulletRuntime runtime) {
        super("println", runtime);
        isVarParam = true;
    }

    @Override
    public Object eval(Object[] args) throws BulletException {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (Object v : args) {
            builder.append(v.toString());
            i++;

            if (i < args.length) {
                builder.append("\t");
            }
        }

        if (runtime.logger == null)
            System.out.println(builder);
        else
            runtime.logger.info(builder.toString());

        return builder.toString();
    }
}
