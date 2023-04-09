package org.bullet.exceptions;

/**
 * @author Huyemt
 */

public class UnderfineException extends BulletException {

    public enum UnderfineType {
        VARIABLE("variable"),
        FUNCTION("function");

        public final String value;

        UnderfineType(String value) {
            this.value = value;
        }

        public final String getValue() {
            return value;
        }
    }

    public final UnderfineType type;

    public UnderfineException(UnderfineType type, String message) {
        super(String.format("Undefine %s \"%s\"", type.value, message));
        this.type = type;
    }

    public final UnderfineType getType() {
        return type;
    }
}
