package file.io;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.zip.ZipFile;

/**
 * Reading 6.4 GB file takes 24.473 sec with buffered reader. This is about 260 MB per sec. This is on my sata 3 ssd,
 * which has read speed up to 530 MB/s.
 *
 * If I increase the buffer size from default 8192 to 4096 * 512, I get 28.618 sec, how about smaller buffer? With 4096
 * as buffer size, it takes 22.054 sec. So it looks like buffer size is better to be smaller? With 1024 as buffer size,
 * it takes 22.136 sec.
 *
 * If I read the file from zip file directly, it takes 32.326 secs. This is probably because my CPU is not as fast so
 * it doesn't able to do faster than IO. It may worth to try with a better CPU and see.
 *
 * How can I get more from my SATA 3 ssd?
 */
public class LargeFileReadingTest {

    public static void main(final String[] args) throws Exception {
        MetricRegistry registry = new MetricRegistry();
        Timer timer = registry.timer("timer");

        Timer.Context context = timer.time();
        int lineCount = 0;
        ZipFile zipFile = new ZipFile("C:\\Codes\\temp\\io\\GDELT.MASTERREDUCEDV2.1979-2013.zip");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(zipFile.getInputStream(zipFile.entries().nextElement())))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        }
        context.stop();

        // Expected to be: 87298047
        System.out.println("Num of rows: " + lineCount);

        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.report();
    }
}
