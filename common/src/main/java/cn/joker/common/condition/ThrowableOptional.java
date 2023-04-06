package cn.joker.common.condition;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * @author jintaoZou
 * @date 2022/9/29-13:56
 */
public class ThrowableOptional<T> {


    private static final ThrowableOptional<?> EMPTY = new ThrowableOptional<>(null);


    private final T value;
    private Throwable throwable;
    private AtomicBoolean isExceptionOcs;


    private ThrowableOptional(T value) {
        this.value = value;
    }


    public static <T> ThrowableOptional<T> ofWithThrowable(Supplier<T> supplier) {
        try {
            return new ThrowableOptional(supplier.get());
        } catch (Throwable throwable) {
            return new ThrowableOptional(throwable);
        }
    }

    public static <T> ThrowableOptional<T> ofWithThrowable(T value) {
        try {
            return new ThrowableOptional(value);
        } catch (Throwable throwable) {
            ThrowableOptional optional = new ThrowableOptional(throwable);
            optional.isExceptionOcs.compareAndSet(true, false);
            return optional;
        }
    }

    public Throwable getThrows() {
        return this.throwable;
    }

    public T get() {
        return this.value;
    }

    public T orElse(T value) {
        T current = this.get();
        return current != null ? current : value;
    }

}
