package org.bullet.exceptions.common;

import org.bullet.compiler.lexer.Position;
import org.bullet.exceptions.BulletException;

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
