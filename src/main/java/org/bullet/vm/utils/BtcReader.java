package org.bullet.vm.utils;

import org.bullet.exceptions.vm.VMBtcCorruptedException;
import org.bullet.exceptions.vm.VMException;
import org.bullet.exceptions.vm.VMInCompatibleException;
import org.bullet.vm.BtcVM;
import org.bullet.vm.structures.BtcProgram;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author Huyemt
 */

public class BtcReader {
    public static BtcProgram loadBtc(File btcF) throws IOException, VMException {
        try (FileInputStream inputStream = new FileInputStream(btcF)) {
            ByteBuffer buffer = ByteBuffer.wrap(inputStream.readAllBytes()).order(ByteOrder.LITTLE_ENDIAN);
            inputStream.close();

            byte[] signature = readBytes(buffer, 6);
            byte[] version = readBytes(buffer, 2);
            byte[] birth = readBytes(buffer, 4);

            ReaderCode code = checkHeaders(signature, version, birth);

            if (code == ReaderCode.SUCCESS) {
                BtcProgram program = new BtcProgram(signature, version, birth);
                program.parse(buffer);
                return program;
            }

            if (code == ReaderCode.VERSION) {
                throw new VMInCompatibleException(btcF, version);
            }

            throw new VMBtcCorruptedException(btcF);
        }
    }

    public static ReaderCode checkHeaders(byte[] signature, byte[] version, byte[] birth) {
        if (!Arrays.equals(signature, BtcVM.SIGNATURE) || !Arrays.equals(birth, BtcVM.BIRTH)) return ReaderCode.FAIL;
        if (BtcVM.VERSION[0] < version[0] || BtcVM.VERSION[1] < version[1]) return ReaderCode.VERSION;

        return ReaderCode.SUCCESS;
    }

    private static byte[] readBytes(ByteBuffer buffer, int count) {
        byte[] r = new byte[count];

        buffer.get(r);

        return r;
    }
}
