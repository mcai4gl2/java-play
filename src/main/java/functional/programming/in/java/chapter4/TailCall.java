package functional.programming.in.java.chapter4;

import java.util.function.Supplier;

public abstract class TailCall<T> {
    public abstract TailCall<T> resume();
    public abstract T eval();
    public abstract boolean isSuspend();

    public static class Return<T> extends TailCall<T> {
        private final T t;
        private Return(T t) {
            this.t = t;
        }

        @Override
        public TailCall<T> resume() {
            throw new IllegalStateException();
        }

        @Override
        public T eval() {
            return t;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    public static class Suspend<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> resume;

        private Suspend(Supplier<TailCall<T>> resume) {
            this.resume = resume;
        }

        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        @Override
        public T eval() {
            TailCall<T> tailRec = this;
            while (tailRec.isSuspend()) {
                tailRec = tailRec.resume();
            }
            return tailRec.eval();
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }

    public static <T> Return<T> return_(T t) {
        return new Return<>(t);
    }

    public static <T> Suspend<T> suspend(Supplier<TailCall<T>> s) {
        return new Suspend<>(s);
    }
}
