package org.bullet.exceptions.common;

import org.bullet.exceptions.BulletException;

/**
 * @author Huyemt
 */

public class UnderfineException extends BulletException {

    public enum UnderfineType {
        VARIABLE("variable"),
        FUNCTION("function"),
        PROVIDE_ATTRIBUTE("provide_attribute");

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
        super(String.format("%s \"%s\" is not define", type.value, message));
        this.type = type;
    }

    public final UnderfineType getType() {
        return type;
    }
}
