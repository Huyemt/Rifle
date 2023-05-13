package org.bullet.vm;

import org.bullet.HexUtil;
import org.bullet.exceptions.BulletException;
import org.bullet.vm.bytecode.BtcType;
import org.bullet.vm.bytecode.struct.BtcFunction;
import org.bullet.vm.utils.ByteReader;

import java.util.Arrays;

/**
 * @author Huyemt
 */

public class BtcVM {
    /**
     * 魔数
     */
    public static final byte[] MAGIC = new byte[]{ 0x42, 0x75, (byte) 0x6C, (byte) 0x6C, 0x65, 0x74 };

    /**
     * 虚拟机版本
     */
    public static final byte[] VERSION = new byte[]{ 0x00, 0x01, 0x00 }; // version x.x.x

    /**
     * Bullet诞生年份
     */
    public static final byte[] YEAR = new byte[]{ 0x07, (byte)0xE7 }; // 2023

    public static BtcFunction load(byte[] bytes) throws BulletException {
        ByteReader reader = new ByteReader(bytes);

        if (!Arrays.equals(reader.getBytes(6), MAGIC)) {
            throw new BulletException("not a btc file");
        }

        // 跳过版本
        reader.getBytes(3);

        if (!Arrays.equals(reader.getBytes(2), YEAR)) {
            throw new BulletException("not a btc file");
        }

        return new BtcFunction(reader);
    }

    public static void main(String[] args) throws BulletException {
        byte[] a = new byte[]{
                // magic
                0x42, 0x75, (byte) 0x6c, (byte) 0x6c, 0x65, 0x74,

                // version
                0x00, 0x01, 0x00,

                // year
                0x07, (byte)0xE7,

                // name
                0x00, 0x00, 0x00, 0x04, 'm', 'a', 'i', 'n',

                // startLine
                0x00, 0x00, 0x00, 0x00,

                // endLine
                0x00, 0x00, 0x00, 0x03,

                // isVarParams
                0x00,

                // paramCount
                0x00,

                // codes
                0x00, 0x00, 0x00, 0x02,

                // code_1
                0x00, 0x0A, 0x0B, 0x0C,

                // code_2
                (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0xFF,

                // constants
                0x00, 0x00, 0x00, 0x02,

                // constant_1
                BtcType.STRING.getCode(), 0x00, 0x00, 0x00, 0x06, 0x42, 0x75, (byte) 0x6C, (byte) 0x6C, 0x65, 0x74,

                // constant_2
                BtcType.NUMBER.getCode(), 0x00, 0x00, 0x00, 0x0C, '1', '1', '4', '5', '1', '4', '.', '1', '0', '0', '8', '6',

                // functions
                0x00, 0x00, 0x00, 0x01,

                // function_1
                // name
                0x00, 0x00, 0x00, 0x04, 't', 'e', 's', 't',

                // startLine
                0x00, 0x00, 0x00, 0x04,

                // endLine
                0x00, 0x00, 0x00, 0x10,

                // isVarParams
                0x00,

                // paramCount
                0x00,

                // codes
                0x00, 0x00, 0x00, 0x00,

                // constants
                0x00, 0x00, 0x00, 0x01,

                // constant_1
                BtcType.STRING.getCode(), 0x00, 0x00, 0x00, 0x03, 'h', 'o', 'w',

                // functions
                0x00, 0x00, 0x00, 0x00,
        };

        BtcFunction function = load(a);

        System.out.println(function);
    }
}
