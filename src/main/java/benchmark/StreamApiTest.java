package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 3, time = 5)
@State(Scope.Benchmark)
@Threads(4)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StreamApiTest {

    @Param({"10000", "100000", "1000000"})
    private int size;

    private static final ArrayList<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(StreamApiTest.class.getSimpleName())
                .output("/Users/ruijiehuang/Downloads/temp/testStream.log")
                .forks(2)
                .build();

        try {
            new Runner(options).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }

    }

    @Setup
    public void setup(){
        for (int i = 0; i < size; i++) {
            int v = (int) (Math.random() * 10);
            numbers.add(v);
        }
    }

    @Benchmark
    public void testParallel(){
        numbers.parallelStream().max(Integer::compare);
    }

    @Benchmark
    public void testSingle(){
        numbers.stream().max(Integer::compare);
    }
}
