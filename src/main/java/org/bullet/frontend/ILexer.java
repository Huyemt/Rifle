package org.bullet.frontend;

import org.bullet.frontend.tokens.Token;
import org.bullet.frontend.tokens.TokenKind;
import org.rifle.utils.Utils;

import java.io.File;

/**
 * @author Huyemt
 */

public abstract class ILexer {
    public Location location;
    public Token current;

    public ILexer(String source) {
        location = new Location(source);

        this.next(); // advance
    }

    public ILexer(File source) throws Exception {
        location = new Location(Utils.readFile(source));

        this.next(); // advance
    }

    public abstract void next();

    public abstract void makeToken(TokenKind kind);
    public abstract void makeToken(TokenKind kind, String value);

}
