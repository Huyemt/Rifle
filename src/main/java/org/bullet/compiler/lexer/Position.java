package org.bullet.compiler.lexer;

/**
 * @author Huyemt
 */

public class Position {
    public final String source;
    public int lineX = 0;
    public int x = -1;
    public int y = 1;
    public int index = -1;
    public Character currentChar = '\0';

    public Position(String source) {
        this.source = source == null ? "" : source;

        next();
    }

    public void next() {
        index++;
        currentChar = index < source.length() ? source.charAt(index) : '\0';

        if (currentChar.equals('\n')) {
            y++;
            x = 0;
            lineX = index + 1;
        } else {
            x++;
        }
    }

    @Override
    public Position clone() {
        Position position = new Position(source);
        position.index = index;
        position.lineX = lineX;
        position.x = x;
        position.y = y;
        position.currentChar = currentChar;

        return position;
    }
}
