package functional.programming.in.java.chapter4;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

import static functional.programming.in.java.chapter4.TailCall.*;

public class TailCallExamplesTests {
    static TailCall<Integer> add(int x, int y) {
        return y == 0
                ? return_(x)
                : suspend(() -> add(x + 1, y - 1));
    }

    @Test
    public void test_add() {
        TailCall<Integer> tailCall = add(3, 1);
        Assert.assertEquals((Integer)4, tailCall.eval());
    }

    static Function<Integer, Function<Integer, Integer>> add = x -> y -> {
        class AddHelper {
            Function<Integer, Function<Integer, TailCall<Integer>>> addHelper =
                    a -> b -> b == 0
                        ? return_(a)
                        : suspend(() -> this.addHelper.apply(a + 1).apply(b - 1));
        }
        return new AddHelper().addHelper.apply(x).apply(y).eval();
    };

    @Test
    public void test_add_function() {
        Assert.assertEquals((Integer)10003, add.apply(3).apply(10000));
    }
}
