package functional.programming.in.java.chapter3;

public interface Result2<T> {
    void bind(Effect<T> success, Effect<String> failure);

    public static <T> Result2<T> failure(String message) {
        return new Failure<>(message);
    }

    public static <T> Result2<T> success(T value) {
        return new Success<>(value);
    }

    public class Success<T> implements Result2<T> {
        private final T value;

        private Success(T t) {
            value = t;
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            success.apply(value);
        }
    }

    public class Failure<T> implements Result2<T> {
        private final String errorMessage;

        private Failure(String s) {
            errorMessage = s;
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            failure.apply(errorMessage);
        }
    }
}
