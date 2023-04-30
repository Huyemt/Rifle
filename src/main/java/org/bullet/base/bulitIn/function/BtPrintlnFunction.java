package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.base.components.BtFunction;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtPrintlnFunction extends BtBulitInFunction {
    public BtPrintlnFunction(BulletRuntime runtime) {
        super("println", runtime);
        args.put("msg", "");
    }

    @Override
    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        StringBuilder builder = new StringBuilder();

        builder.append(args.get("msg"));

        if (runtime.logger == null)
            System.out.println(builder);
        else
            runtime.logger.info(builder.toString());


        return builder.toString();
    }
}
