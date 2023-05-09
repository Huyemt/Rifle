package org.bullet.vm;

import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.structures.BtcProgram;
import org.bullet.vm.utils.BtcReader;

import java.io.File;
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
    public static final byte[] SIGNATURE = new byte[]{ 'B', 'u', 'l', 'l', 'e', 't' };

    /**
     * 虚拟机版本
     * <br>
     * 在加载字节码文件的时候，虚拟机会比较自己的版本前两位与字节码版本前两位
     */
    public static final byte[] VERSION = new byte[]{ 0x00, 0x01, 0x00 };

    /**
     * Bullet诞生时间
     * <br>
     * 这是个固定值，2023
     */
    public static final byte[] BIRTH = { '2', '0', '2', '3' };

    public BtcVM() {

    }

    public static void main(String[] args) throws IOException, VMException {
        File file = new File("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\a.btc");
        BtcProgram program;

//        program = new BtcProgram(SIGNATURE, VERSION, BIRTH);
//        BtcWriter.generateFile(file, program);
        program = BtcReader.loadBtc(file);
        System.out.println(new String(program.headers.SIGNATURE));
        System.out.println(new String(program.headers.BIRTH));
    }
}
