package org.bullet.exceptions;

import org.bullet.compiler.lexer.Position;

/**
 * @author Huyemt
 */

public class UnderfineException extends RuntimeException {

    public enum UnderfineType {
        VARIABLE("Variable");

        public final String value;

        UnderfineType(String value) {
            this.value = value;
        }

        public final String getValue() {
            return value;
        }
    }

    public final UnderfineType type;

    public UnderfineException(Position position, UnderfineType type, String message) {
        super(position, String.format("Underfine %s: \"%s\"", type.value, message));
        this.type = type;
    }

    public final UnderfineType getType() {
        return type;
    }
}
