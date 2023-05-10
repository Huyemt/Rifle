package org.bullet.vm;

import org.bullet.HexUtil;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.structures.BtcProgram;
import org.bullet.vm.structures.BtcType;
import org.bullet.vm.utils.BtcWriter;
import org.bullet.vm.utils.ReaderCode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Huyemt
 */

public class BtcVM {

    /**
     * 魔法数 (签名)
     * <br>
     * 验证文件是否为Bullet字节码文件
     */
    public static final byte[] signature = new byte[]{ 'B', 'u', 'l', 'l', 'e', 't' };

    /**
     * 虚拟机版本
     * <br>
     * 在加载字节码文件的时候，虚拟机会比较自己的版本前两位与字节码版本前两位
     */
    public static final short[] version = new short[]{ 0, 1, 0 };

    /**
     * Bullet诞生时间
     * <br>
     * 这是个固定值，2023
     */
    public static final byte[] birth = { '2', '0', '2', '3' };

    public static ReaderCode checkHeaders(byte[] signature, short[] version, byte[] birth) {
        if (!Arrays.equals(signature, BtcVM.signature) || !Arrays.equals(birth, BtcVM.birth)) return ReaderCode.FAIL;
        if (BtcVM.version[0] < version[0] || BtcVM.version[1] < version[1]) return ReaderCode.VERSION;

        return ReaderCode.SUCCESS;
    }

    public BtcVM() {

    }

    public static void main(String[] args) throws IOException, VMException {
        File file = new File("E:\\AMyCode\\Projects\\Java\\Rifle\\Rifle\\a.btc");
        BtcProgram program;
        program = BtcProgram.load(BtcWriter.generateFile(file, new BtcProgram(version)));
        System.out.println(((BtNumber) program.common.constants[0]).add(new BtNumber("114514")));
    }
}
