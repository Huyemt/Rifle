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
    public BtcHeaders headers;

    public BtcProgram() {
        headers = new BtcHeaders();
    }

    public void read(ByteBuffer buffer) {

    }
}
