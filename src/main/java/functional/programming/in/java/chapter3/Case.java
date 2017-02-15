package functional.programming.in.java.chapter3;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result2<T>>> {
    public static final Supplier<Boolean> TRUE = () -> true;

    private static class DefaultCase<T> extends Case<T> {
        private DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result2<T>> result2Supplier) {
            super(booleanSupplier, result2Supplier);
        }
    }

    private Case(Supplier<Boolean> booleanSupplier, Supplier<Result2<T>> result2Supplier) {
        super(booleanSupplier, result2Supplier);
    }

    public static <T> Case<T> case_(Supplier<Boolean> condition, Supplier<Result2<T>> value) {
        return new Case<>(condition, value);
    }

    public static <T> DefaultCase<T> case_(Supplier<Result2<T>> value) {
        return new DefaultCase<>(TRUE, value);
    }

    @SafeVarargs
    public static <T> Result2<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
        return Stream.of(matchers).filter(c -> c._1.get()).findFirst().orElse(defaultCase)._2.get();
    }
}
