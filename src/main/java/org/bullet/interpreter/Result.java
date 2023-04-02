package org.bullet.interpreter;

/**
 * @author Huyemt
 */

public class Result<T> {
    public T value;

    public Result() {

    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
