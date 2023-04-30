package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtPrintFunction extends BtBulitInFunction {
    public BtPrintFunction(BulletRuntime runtime) {
        super("print", runtime);
        args.put("msg", "");
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        StringBuilder builder = new StringBuilder();

        builder.append(args.get("msg"));

        if (runtime.logger == null)
            System.out.print(builder);
        else
            runtime.logger.println(builder.toString());


        return builder.toString();
    }
}
