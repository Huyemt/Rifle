package org.bullet.vm.structure;

import org.bullet.vm.structure.components.BtcFunction;
import org.bullet.vm.structure.components.BtcVariable;

/**
 * @author Huyemt
 */

public class BtcBody {
    /**
     * 字节码
     */
    public int[] codes;

    /**
     * 常量表
     */
    public Object[] constants;

    /**
     * 局部变量表
     */
    public BtcVariable[] variables;

    /**
     * 子函数集
     */
    public BtcFunction[] functions;

}
