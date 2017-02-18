package functional.programming.in.java.chapter4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Memorizer<T, U> {
    private final Map<T, U> cache = new ConcurrentHashMap<>();

    private Memorizer() {}

    public static <T, U> Function<T, U> memoize(Function<T, U> function) {
        return new Memorizer<T, U>().doMemoize(function);
    }

    private Function<T, U> doMemoize(Function<T, U> function) {
        return input -> cache.computeIfAbsent(input, function::apply);
    }
}
