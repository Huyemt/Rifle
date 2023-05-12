package org.bullet.vm.structure.components;

import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.structure.BtcBody;
import org.bullet.vm.structure.BtcProgram;
import org.bullet.vm.structure.BtcType;
import org.bullet.vm.utils.BtcReader;

/**
 * @author Huyemt
 */

public class BtcFunction {

    public String name;

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
    public BtcBody body = new BtcBody();

    public BtcFunction(BtcReader reader) throws VMException {
        name = reader.readString(BtcProgram.SHORT_STRING);
        startLine = reader.readInt();
        endLine = reader.readInt();
        parameterCount = reader.buffer.get();

        body.codes = reader.readCodes();
        body.constants = reader.readConstants();
        body.variables = reader.readVariables();
        body.functions = reader.readFunctions();
    }
}
