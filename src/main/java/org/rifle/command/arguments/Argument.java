package org.rifle.command.arguments;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Huyemt
 */

public class Argument {
    private final String origin;
    private final OrderArgument orderArgument;
    private final KeyArgument keyArgument;

    public Argument(String origin) {
        this.origin = origin;
        orderArgument = as(OrderArgument.class);
        keyArgument = as(KeyArgument.class);
    }

    /**
     * 转换为顺序参数
     *
     * Convert to sequence parameter
     *
     * @return OrderArgument
     */
    public final OrderArgument asOrderArgument() {
        return orderArgument;
    }

    /**
     * 转换为关键字参数
     *
     * Convert to keyword parameter
     *
     * @return KeyArgument
     */
    public final KeyArgument asKeyArgument() {
        return keyArgument;
    }

    /**
     * 转换为其他类型的Argument (您可以自定义属于专属的 `Argument`)
     *
     * Convert to another type of Argument (You can customize your own `Argument`)
     *
     * @param type
     * @return T
     * @param <T>
     */
    public final <T> T as(Class<? extends ArgumentBase> type) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);

            return (T) constructor.newInstance(origin);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取原来的参数字符串
     *
     * Get the original parameter string
     *
     * @return String
     */
    public final String getOrigin() {
        return origin;
    }
}
