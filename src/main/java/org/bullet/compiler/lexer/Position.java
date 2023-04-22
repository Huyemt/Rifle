package org.bullet.compiler.lexer;

/**
 * @author Huyemt
 */

public class Position {
    public final String source;
    public final String path;
    public int lineX = 0;
    public int x = -1;
    public int y = 1;
    public int index = -1;
    public Character currentChar = '\0';

    public Position(String source, String path) {
        this.source = source == null ? "" : source;
        this.path = path;

        next();
    }

    public void next() {
        index++;
        x++;
        currentChar = index < source.length() ? source.charAt(index) : '\0';
    }

    public void next(int times) {
        for (int i = 0; i < times; i++) {
            next();
        }
    }

    @Override
    public Position clone() {
        Position position = new Position(source, path);
        position.index = index;
        position.lineX = lineX;
        position.x = x;
        position.y = y;
        position.currentChar = currentChar;

        return position;
    }
}
