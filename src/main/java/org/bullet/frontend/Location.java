package org.bullet.frontend;

/**
 * @author Huyemt
 */

public class Location {
    protected final String source;
    protected int index;
    protected int x;
    protected int y;
    protected int lineX;
    protected Character current;

    public Location(String source) {
        this.index = -1;
        this.x = 0;
        this.y = 1;
        this.lineX = 0;
        this.current = '\0';
        this.source = source;

        this.next(); //advance
    }

    public void next() {
        this.index++;
        this.x++;

        this.current = this.index < this.source.length() ? this.source.charAt(this.index) : '\0';
    }
}
