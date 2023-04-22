package org.bullet.base.types;


import java.util.ArrayList;

/**
 * Bullet的 Number 类型
 * <br><br>
 *
 * @author Huyemt
 */

public class BtNumber {
    public final ArrayList<Integer> left;
    public final ArrayList<Integer> right;
    public boolean positive;

    public BtNumber() {
        left = new ArrayList<>();
        right = new ArrayList<>();
        positive = true;
    }
}
