package org.bullet.vm.structure.components;

import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.structure.BtcType;
import org.bullet.vm.utils.BtcReader;

/**
 * @author Huyemt
 */

public class BtcVariable {
    public String name;
    public int startPC;
    public int endPC;

    public BtcVariable(BtcReader reader) throws VMException {
        name = reader.readString(BtcType.SHORT_STRING);
        startPC = reader.readInt();
        endPC = reader.readInt();
    }
}
