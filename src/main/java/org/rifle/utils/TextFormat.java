package org.rifle.utils;

/**
 * Color output based on "JANSI"
 *
 * @author Huyemt
 */

public enum TextFormat {
    FONT_BLACK(30),
    FONT_RED(31),
    FONT_GREEN(32),
    FONT_YELLOW(33),
    FONT_BLUE(34),
    FONT_PURPLE(35),
    FONT_DARK_GREEN(36),
    FONT_WHITE(37),

    BACKGROUND_BLACK(40),
    BACKGROUND_RED(41),
    BACKGROUND_GREEN(42),
    BACKGROUND_YELLOW(43),
    BACKGROUND_BLUE(44),
    BACKGROUND_PURPLE(45),
    BACKGROUND_DARK_GREEN(46),
    BACKGROUND_WHITE(47),

    RESET(0),
    STYLE_BOLD(1),
    STYLE_THIN(2),
    STYLE_ITALIC(3),
    STYLE_UNDERLINE(4),
    STYLE_BLINK_SLOW(5),
    STYLE_BLINK_FAST(6);


    private final int code;
    TextFormat(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("\u001B[%dm", code);
    }


}
