package org.bullet.vm.bytecode.struct;

import org.bullet.base.types.BtByte;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;
import org.bullet.vm.bytecode.BtcType;
import org.bullet.vm.utils.ByteReader;

/**
 * 二进制解析出来的程序模型
 * @author Huyemt
 */

public class BtcFunction {
    /**
     * 函数名称
     */
    private final String name;

    /**
     * 开头行数
     */
    private final int startLine;

    /**
     * 结尾行数
     */
    private final int endLine;

    /**
     * 不定长参数
     */
    private final boolean isVarParams;

    /**
     * 参数数量，最多有255个
     */
    private final byte paramCount;

    /**
     * 字节码指令
     */
    private final int[] codes;

    /**
     * 常量表
     */
    private final Object[] constants;

    /**
     * 子函数集
     */
    private final BtcFunction[] functions;

    private final ByteReader reader;


    public BtcFunction(ByteReader reader) throws BulletException {
        this.reader = reader;

        name = reader.getString(reader.getInt());
        startLine = reader.getInt();
        endLine = reader.getInt();
        isVarParams = reader.getBoolean();
        paramCount = reader.getByte();
        codes = getCodes();
        constants = getConstants();
        functions = getFunctions();
    }

    private int[] getCodes() {
        int[] codes = new int[reader.getInt()];

        for (int i = 0; i < codes.length; i++) codes[i] = reader.getInt();

        return codes;
    }

    private Object getConstant() throws BulletException {
        BtcType type = BtcType.typeof(reader.getByte());

        if (type == null) {
            throw new BulletException("type error");
        }

        switch (type) {
            case BYTE: {
                return new BtByte(reader.getByte());
            }

            case NULL:
                return BulletRuntime.BTNULL;

            case BOOL: {
                return reader.getBoolean();
            }

            case NUMBER: {
                return new BtNumber(reader.getString(reader.getInt()));
            }

            case STRING: {
                return reader.getString(reader.getInt());
            }
        }

        throw new BulletException("type error");
    }

    private Object[] getConstants() throws BulletException {
        Object[] result = new Object[reader.getInt()];

        for (int i = 0; i < result.length; i++) result[i] = getConstant();

        return result;
    }

    private BtcFunction[] getFunctions() throws BulletException {
        BtcFunction[] result = new BtcFunction[reader.getInt()];

        for (int i = 0; i < result.length; i++) result[i] = new BtcFunction(reader);

        return result;
    }

    private String parseName() {
        return String.format("name -> %s\n", name);
    }

    private String parseStartLine() {
        return String.format("startLine -> %d\n", startLine);
    }

    private String parseEndLine() {
        return String.format("endLine -> %d\n", endLine);
    }

    private String parseCodes(int n) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("codes(%d):\n", codes.length));

        for (int instruction : codes) {
            builder.append("\t".repeat(n)).append(String.format("%d\n", instruction));
        }

        return builder.toString();
    }

    private String parseConstants(int n) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("constants(%d):\n", constants.length));

        for (Object obj : constants) {
            builder.append("\t".repeat(n)).append(String.format("%s -> %s\n", BtcType.typeof(obj), obj.toString()));
        }

        return builder.toString();
    }

    private String parseFunctions() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("functions(%d):\n", functions.length));

        for (BtcFunction function : functions) {
            builder.append("\t").append(function.parseName()).append("\t").append(function.parseStartLine()).append("\t").append(function.parseEndLine()).append("\t").append(function.parseCodes(2)).append("\t").append(function.parseConstants(2)).append("\t").append(function.parseFunctions());
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(parseName()).append(parseStartLine()).append(parseEndLine()).append(parseCodes(1)).append(parseConstants(1)).append(parseFunctions());

        return builder.toString();
    }
}