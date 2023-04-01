package org.bullet.frontend;

/**
 * @author Huyemt
 */

public class Diag {

    private Diag() {

    }

    public static String diagException(Location location, String content) {
        StringBuilder builder = new StringBuilder();

        int start = location.index;
        int i = location.index;

        while (location.source.charAt(i) != '\n') {
            i++;
        }

        builder.append(location.source, start, i - start).append("\n");
        builder.append(" ".repeat(location.x - 1)).append("^");
        builder.append(String.format(" at line %d, column %d:  %s", location.y, location.x, content)).append("\n");

        return builder.toString();
    }
}
