package disruptor.play;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * Example code from Disruptor Getting Started page: https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started
 */
public class LongEventMain {

    public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Received event with value: " + event.get() + " on " + Thread.currentThread().getName());
    }

    public static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.set(buffer.getLong(0));
    }

    public static void main(String[] args) throws Exception {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
//        disruptor.handleEventsWith(LongEventMain::handleEvent);
        // Creating two separate handlers, they will form a working pool and run in different threads
        disruptor.handleEventsWithWorkerPool(new WorkHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event) throws Exception {
                System.out.println("Received event with value: " + event.get() + " on "
                        + Thread.currentThread().getName());
            }
        }, new WorkHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event) throws Exception {
                System.out.println("Received event with value: " + event.get() + " on "
                        + Thread.currentThread().getName());
            }
        });

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        System.out.println("Publishing on " + Thread.currentThread().getName());
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(LongEventMain::translate, bb);
            Thread.sleep(500);
        }
    }
}
