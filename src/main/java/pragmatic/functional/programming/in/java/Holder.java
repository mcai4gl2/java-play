package pragmatic.functional.programming.in.java;

import java.util.function.Supplier;

public class Holder {
    private Supplier<Object> object = () -> createAndCacheObject();

    public Object get() {
        return object.get();
    }

    private synchronized Object createAndCacheObject() {
        class Factory implements Supplier<Object> {
            private final Object instance = new Object();

            public Object get() {return instance;}
        }

        if (!Factory.class.isInstance(object))
            object = new Factory();

        return object.get();
    }
}
