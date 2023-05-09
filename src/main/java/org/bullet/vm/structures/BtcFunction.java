package org.bullet.vm.structures;

/**
 * @author Huyemt
 */

public class BtcFunction {

    /**
     * 起始行
     */
    public int startLine;

    /**
     * 终结行
     */
    public int endLine;

    /**
     * 参数数量
     */
    public byte parameterCount;

    /**
     * 字节码
     */
    public int[] codes;

    /**
     * 函数集
     */
    public BtcFunction[] functions;

    public BtcFunction() {

    }
}
