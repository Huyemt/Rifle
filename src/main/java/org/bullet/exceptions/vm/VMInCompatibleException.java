package org.bullet.exceptions.vm;

import org.bullet.vm.BtcVM;

import java.io.File;

/**
 * @author Huyemt
 */

public class VMInCompatibleException extends VMException {

    public VMInCompatibleException(File file, short[] version) {
        super(String.format("Your virtual machine version(v%d.%d.%d) is lower than the bytecode version(v%d.%d.X) with path \"%s\"", BtcVM.version[0], BtcVM.version[1], BtcVM.version[2], version[0], version[1], file.getAbsolutePath()));
    }

}
