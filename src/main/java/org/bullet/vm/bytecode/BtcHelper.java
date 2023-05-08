package org.bullet.vm.bytecode;

import org.bullet.exceptions.vm.VMBtcCorruptedException;
import org.bullet.exceptions.vm.VMException;
import org.bullet.exceptions.vm.VMInCompatibleException;
import org.bullet.vm.BtcVM;
import org.bullet.vm.structures.BtcProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author Huyemt
 */

public class BtcHelper {

    public static BtcProgram parseBtcFile(byte[] dump) throws VMException {
        ByteBuffer buffer = ByteBuffer.wrap(dump).order(ByteOrder.LITTLE_ENDIAN);
        BtcProgram program = new BtcProgram();

        program.headers.SIGNATURE = getBytes(buffer, 4);
        program.headers.VERSION = getBytes(buffer, 2);
        program.headers.BIRTH_YEAR = getBytes(buffer, 2);

        if (!Arrays.equals(program.headers.SIGNATURE, BtcVM.SIGNATURE) || !Arrays.equals(program.headers.BIRTH_YEAR, BtcVM.BIRTH_YEAR)) {
            throw new VMBtcCorruptedException("it is not a bullet bytecode file");
        }

        if (BtcVM.VERSION[0] < program.headers.VERSION[0] || BtcVM.VERSION[1] < program.headers.VERSION[1]) {
            throw new VMInCompatibleException(String.format("Bullet Bytecode File is not incompatible the virual machine (version %d.%d.%d)", (int) BtcVM.VERSION[0], (int) BtcVM.VERSION[1], (int) BtcVM.VERSION[2]));
        }

        program.read(buffer);

        return program;
    }

    private static byte[] getBytes(ByteBuffer buffer, int count) {
        byte[] a = new byte[count];
        buffer.get(a);
        return a;
    }
}
