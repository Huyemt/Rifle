package org.rifle;

/**
 * @author Huyemt
 */

public class Main {
    public static void main(String[] args) {
        try {
            new Rifle();
        } catch (Exception e) {
            Rifle.getInstance().getConsole().shutdown();
        }
    }
}
