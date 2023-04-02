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
        builder.append(position.source, start, i).append("\n");
        builder.append(" ".repeat(position.x - (Character.isDigit(position.currentChar == 0 ? position.source.charAt(position.index - 1) : position.currentChar) ? 1 : 0))).append("^");

        builder.append(String.format(" at line %d, column %d:  %s", position.y, position.x, builder1)).append("\n");

        System.out.println(builder);
    }


}
