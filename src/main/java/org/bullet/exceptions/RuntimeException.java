package org.bullet.exceptions;

import org.bullet.compiler.lexer.Position;

/**
 * @author Huyemt
 */

public class RuntimeException extends BulletException {

    public final Position position;

    public RuntimeException(Position position, String message) {
        super(message);

        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
