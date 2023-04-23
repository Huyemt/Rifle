package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtFunction;
import org.bullet.interpreter.BulletRuntime;

/**
 * @author Huyemt
 */

public class BtPrintFunction extends BtFunction {
    public BtPrintFunction(BulletRuntime runtime) {
        super("print", runtime);
    }

    @Override
    public final Object invokeFV(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]);

            if (i + 1 < args.length)
                builder.append("\t");
        }

        if (runtime.logger == null)
            System.out.print(builder);
        else
            runtime.logger.println(builder.toString());

        return builder.toString();
    }
}
