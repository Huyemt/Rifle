package org.bullet;

import org.bullet.compiler.lexer.Position;

/**
 * @author Huyemt
 */

public class Reporter {
    public static void report(Position position, String ... messages) {
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();

        for (String msg : messages) {
            builder1.append(msg);
        }

        int start = position.lineX;
        int i = position.index;

        while (i < position.source.length() && position.source.charAt(i) != '\n') {
            i++;
        }

        int i1 = position.index - 1;

        char character = position.currentChar == 0 ? (i1 < position.source.length() ? position.source.charAt(i1) : '\0') : position.currentChar;

        builder.append(position.source, start, i <= position.source.length() ? i : i - 1).append("\n");
        if (position.y == 1) {
            builder.append(" ".repeat(position.x - (Character.isDigit(character) ? 1 : Character.isLetter(character) ? 0 : 1))).append("^");
        } else {
            builder.append(" ".repeat(position.x - 3)).append("^");
        }

        builder.append(String.format(" at line %d, column %d:  %s", position.y, position.x, builder1)).append("\n");

        System.out.println(builder);
    }


}
