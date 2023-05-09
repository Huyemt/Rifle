package org.bullet.vm.structures;


import java.nio.ByteBuffer;

/**
 * @author Huyemt
 */

public class BtcProgram {

    /**
     * 基本信息头
     * <br>
     * 每个 btc 文件都需要包含此信息头
     */
    public final BtcHeaders headers;

    /**
     * 字节码
     */
    public int[] codes;

    /**
     * 函数集
     */
    public BtcFunction[] functions;

    public BtcProgram(byte[] signature, byte[] version, byte[] birth) {
        headers = new BtcHeaders();

        headers.SIGNATURE = signature;
        headers.VERSION = version.length > 2 ? new byte[]{ version[0], version[1] } : version;
        headers.BIRTH = birth;

    }

    public void parse(ByteBuffer buffer) {

    }
}
