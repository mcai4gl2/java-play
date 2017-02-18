package functional.programming.in.java.chapter4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Doubler {
    private static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

    public static Function<Integer, Integer> doubleValue =
            x -> cache.computeIfAbsent(x, y -> y * 2);
}