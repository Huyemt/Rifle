package org.bullet.base.bulitIn.function;

import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtArray;
import org.bullet.base.types.BtDictionary;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class BtLenFunction extends BtFunction {
    public BtLenFunction(BulletRuntime runtime) {
        super("len", runtime);
    }

    @Override
    public final Object invokeFV(Object... args) throws BulletException {
        BigDecimal big = new BigDecimal(0L);
        Object r;

        for (Object arg : args) {
            r = arg;

            if (r instanceof BtArray) {
                big = big.add(new BigDecimal(((BtArray) r).vector.size()));
                continue;
            }

            if (r instanceof BtDictionary) {
                big = big.add(new BigDecimal(((BtDictionary) r).vector.size()));
                continue;
            }

            if (r.getClass().isArray()) {
                big = big.add(new BigDecimal(BtArray.parse((Object[]) r).vector.size()));
                continue;
            }

            if (r instanceof String) {
                big = big.add(new BigDecimal(((String) r).length()));
                continue;
            }

            throw new BulletException(String.format("Length of \"%s\" type is not supported", big.getClass().getName()));
        }

        return big;
    }
}
