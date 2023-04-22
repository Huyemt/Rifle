package org.bullet.base.components;

import org.bullet.base.types.BtArray;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.math.BigDecimal;

/**
 * Bullet的函数
 * <br><br>
 * Function of Bullet
 *
 * @author Huyemt
 */

public abstract class BtFunction {
    public final String funcName;
    protected final BulletRuntime runtime;

    public BtFunction(String funcName, BulletRuntime runtime) {
        this.funcName = funcName;
        this.runtime = runtime;
    }

    /**
     * 函数调用
     * <br><br>
     * Call the function
     *
     * @param args
     * @throws BulletException
     */
    public void invoke(Object ... args) throws BulletException {
        this.invokeFV(args);
    }

    /**
     * 函数调用并获取返回值
     * <br><br>
     * Call the function and get the return value
     *
     * @param args
     * @return Object
     * @throws BulletException
     */
    public abstract Object invokeFV(Object ... args) throws BulletException;

    public final String getName() {
        return funcName;
    }

    protected final BtArray translateArray(Object[] arr) {
        BtArray array = new BtArray();

        for (Object v : arr) {
            if (v instanceof Double || v instanceof Integer || v instanceof Float) {
                array.vector.add(new BigDecimal(v.toString()));
                continue;
            }

            if (v.getClass().isArray()) {
                array.vector.add(translateArray((Object[]) v));
                continue;
            }

            array.vector.add(v);
        }

        return array;
    }

    @Override
    public String toString() {
        return "Function:".concat(funcName);
    }
}
