package org.bullet.exceptions;

import org.bullet.compiler.lexer.Position;

/**
 * @author Huyemt
 */

public class ParsingException extends BulletException {
    public final Position position;

    public ParsingException(Position position, String message) {
        super(message);

        this.position = position;
    }
}
