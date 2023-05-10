package org.bullet.vm.structures;

import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.utils.BtcReader;

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
     * 通用结构
     * <br>
     * 包含：字节码，常量池，子函数集
     */
    public BtcCommon common = new BtcCommon();

    public BtcFunction(BtcReader reader) throws VMException {
        startLine = reader.readInt();
        endLine = reader.readInt();
        parameterCount = reader.buffer.get();

        // common.codes = reader.readCodes();
        common.constants = reader.readConstants();
        // common.functions = reader.readFunctions();
    }
}
