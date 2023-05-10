package org.bullet.vm.structures;

import org.bullet.HexUtil;
import org.bullet.exceptions.vm.VMBtcCorruptedException;
import org.bullet.exceptions.vm.VMException;
import org.bullet.exceptions.vm.VMInCompatibleException;
import org.bullet.vm.BtcVM;
import org.bullet.vm.utils.BtcReader;
import org.bullet.vm.utils.ReaderCode;

import java.io.File;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class BtcProgram {

    public static BtcProgram load(File file) throws IOException, VMException {
        BtcReader reader = new BtcReader(file);

        short[] version;

        ReaderCode code = BtcVM.checkHeaders(reader.readBytes( 6), version = HexUtil.toShorts(reader.readBytes(4)), reader.readBytes(4));

        if (code == ReaderCode.SUCCESS) {
            BtcProgram program = new BtcProgram(version);
            program.common.codes = reader.readCodes();
            program.common.constants = reader.readConstants();
            program.common.functions = reader.readFunctions();
            return program;
        }

        if (code == ReaderCode.VERSION) {
            throw new VMInCompatibleException(file, version);
        }

        throw new VMBtcCorruptedException(file);
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
    public BtcCommon common = new BtcCommon();

    public BtcProgram(short[] version) {
        this.version = version.length > 2 ? new short[]{ version[0], version[1] } : version;
    }
}
