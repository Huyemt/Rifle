package org.bullet.vm.structure;

import org.bullet.HexUtil;
import org.bullet.exceptions.vm.BtcCorruptedException;
import org.bullet.exceptions.vm.VMException;
import org.bullet.exceptions.vm.InCompatibleException;
import org.bullet.vm.BtcVM;
import org.bullet.vm.utils.BtcReader;
import org.bullet.vm.utils.ReaderCode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Huyemt
 */

public class BtcProgram {

    public static final int NULL = 0x00;
    public static final int BOOLEAN = 0x01;
    public static final int BYTE = 0x02;
    public static final int NUMBER =  0x03;
    public static final int SHORT_STRING =  0x04;
    public static final int LONG_STRING = 0x14;
    public static final int LIST = 0x05;
    public static final int DICTIONARY = 0x06;

    /**
     * 校验字节码是否为Bullet字节码
     * @param signature
     * @param version
     * @param birth
     * @return ReaderCode
     */
    public static ReaderCode checkHeaders(byte[] signature, short[] version, byte[] birth) {
        if (!Arrays.equals(signature, BtcVM.signature) || !Arrays.equals(birth, BtcVM.birth)) return ReaderCode.FAIL;
        if (BtcVM.version[0] < version[0] || BtcVM.version[1] < version[1]) return ReaderCode.VERSION;

        return ReaderCode.SUCCESS;
    }

    /**
     * 加载字节码文件
     * @param file
     * @return BtcProgram
     * @throws IOException
     * @throws VMException
     */
    public static BtcProgram load(File file) throws IOException, VMException {
        BtcReader reader = new BtcReader(file);

        short[] version;

        ReaderCode code = checkHeaders(reader.readBytes( 6), version = HexUtil.toShorts(reader.readBytes(4)), reader.readBytes(4));

        if (code == ReaderCode.SUCCESS) {
            BtcProgram program = new BtcProgram(version);
            program.body.codes = reader.readCodes();
            program.body.constants = reader.readConstants();
            program.body.variables = reader.readVariables();
            program.body.functions = reader.readFunctions();
            return program;
        }

        if (code == ReaderCode.VERSION) {
            throw new InCompatibleException(file, version);
        }

        throw new BtcCorruptedException(file);
    }

    /**
     * 基本信息头
     * <br>
     * 每个 btc 文件都需要包含此信息头
     */
    public short[] version;

    /**
     * 通用结构
     * <br>
     * 包含：字节码，常量池
     */
    public BtcBody body = new BtcBody();

    public BtcProgram(short[] version) {
        this.version = version.length > 2 ? new short[]{ version[0], version[1] } : version;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
