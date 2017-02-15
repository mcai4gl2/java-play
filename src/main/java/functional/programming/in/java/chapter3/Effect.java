package functional.programming.in.java.chapter3;

@FunctionalInterface
public interface Effect<T> {
    void apply(T t);
}
