package org.bullet.vm;

import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.bytecode.BtcHelper;
import org.bullet.vm.structures.BtcProgram;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class BtcVM {

    /**
     * 魔法数 (签名)
     * <br>
     * 验证文件是否为Bullet字节码文件
     */
    public static final byte[] SIGNATURE = new byte[]{ (byte) 0xBB, (byte) 0xCF, (byte) 0xBA, (byte) 0xBE };

    /**
     * 虚拟机版本
     * <br>
     * 一般虚拟机版本
     */
    public static final byte[] VERSION = new byte[]{ 0, 1, 0 };

    /**
     * Bullet诞生时间
     * <br>
     * 这是个固定值，2023
     */
    public static final byte[] BIRTH_YEAR = new byte[]{ 0x20, 0x23 };

    public BtcVM() {

    }

    public static void main(String[] args) throws IOException, VMException {
        File file = new File("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\a.btc");
        FileInputStream inputStream = new FileInputStream(file);
        BtcProgram program = BtcHelper.parseBtcFile(inputStream.readAllBytes());
    }
}
