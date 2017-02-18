package functional.programming.in.java.chapter4;

import org.junit.Test;

import java.math.BigInteger;
import static functional.programming.in.java.chapter4.TailCall.*;

public class FibonacciTests {
    public static int fibonacci(int number) {
        if (number == 0 || number == 1)
            return number;
        return fibonacci(number - 1) + fibonacci(number - 2);
    }

    @Test
    public void test_simple_implementation() {
        int n = 10;
        for (int i = 0; i <= n; i++) {
            System.out.print(fibonacci(i) + " ");
        }
    }

    public static BigInteger fibonacciFast_(BigInteger acc1, BigInteger acc2, BigInteger x) {
        if (x.equals(BigInteger.ZERO))
            return BigInteger.ZERO;
        else if (x.equals(BigInteger.ONE))
            return acc1.add(acc2);
        else
            return fibonacciFast_(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE));
    }

    public static BigInteger fibonacciFast(int x) {
        return fibonacciFast_(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x));
    }

    @Test
    public void test_fast() {
        int n = 10;
        for (int i = 0; i <= n; i++) {
            System.out.print(fibonacciFast(i) + " ");
        }
    }

    public TailCall<BigInteger> fibonacciFast2_(BigInteger acc1, BigInteger acc2, BigInteger x) {
        if (x.equals(BigInteger.ZERO))
            return return_(BigInteger.ZERO);
        else if (x.equals(BigInteger.ONE))
            return return_(acc1.add(acc2));
        else
            return suspend(() -> fibonacciFast2_(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
    }

    public BigInteger fibonacciFast2(int x) {
        return fibonacciFast2_(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x)).eval();
    }

    @Test
    public void test_fast2() {
        int n = 10;
        for (int i = 0; i <= n; i++) {
            System.out.print(fibonacciFast2(i) + " ");
        }
    }
}
