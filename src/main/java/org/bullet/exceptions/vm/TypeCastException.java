package org.bullet.exceptions.vm;

import org.bullet.vm.structure.BtcType;

/**
 * @author Huyemt
 */

public class TypeCastException extends VMException {

    public TypeCastException(Object dest, BtcType type) {
        super(String.format("%s cannot be converted to the type \"%s\"", dest.getClass().getName(), type.typeName));
    }

}
