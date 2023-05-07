package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

/**
 * @author Huyemt
 */

public class BtPrintFunction extends BtBulitInFunction {
    public BtPrintFunction(BulletRuntime runtime) {
        super("print", runtime);
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
            System.out.print(builder);
        else
            runtime.logger.println(builder.toString());

        return builder.toString();
    }
}
