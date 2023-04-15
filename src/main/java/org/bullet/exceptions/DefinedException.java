package org.bullet.exceptions;

/**
 * @author Huyemt
 */

public class DefinedException extends BulletException {
    public enum DerfinedType {
        VARIABLE("variable"),
        FUNCTION("function"),
        PROVIDE_ATTRIBUTE("provide_attribute"),
        PROVIDE_INTERFACE("provide_interface");

        public final String value;

        DerfinedType(String value) {
            this.value = value;
        }

        public final String getValue() {
            return value;
        }
    }

    public final DerfinedType type;

    public DefinedException(DerfinedType type, String message) {
        super(String.format("The %s \"%s\" has already been declared", type.value, message));
        this.type = type;
    }

    public final DerfinedType getType() {
        return type;
    }
}
