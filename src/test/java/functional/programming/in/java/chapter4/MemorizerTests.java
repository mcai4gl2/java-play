package functional.programming.in.java.chapter4;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class MemorizerTests {
    @Test
    public void memorize_carry() {
        Function<Integer, Function<Integer, Integer>> func =
                Memorizer.memoize(x -> Memorizer.memoize((Integer y) -> x + y));

        Assert.assertEquals(Integer.valueOf(3), func.apply(1).apply(2));
    }
}
