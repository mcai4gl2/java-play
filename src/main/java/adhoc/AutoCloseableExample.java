package adhoc;

public class AutoCloseableExample {
    static class MyResource implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("Closing!");
        }
    }

    // Example from: http://stackoverflow.com/questions/13141302/implements-closeable-or-implements-autocloseable/13141382#13141382
    public static void main(String[] args) throws Exception {
        try (MyResource res = new MyResource()) {

        }
    }
}
