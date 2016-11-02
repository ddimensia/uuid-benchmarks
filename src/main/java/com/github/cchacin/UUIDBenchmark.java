package com.github.cchacin;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 4,
        time = 2500,
        timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 4,
        time = 2500,
        timeUnit = TimeUnit.MILLISECONDS)
@Threads(Threads.MAX)
@State(Scope.Benchmark)
public class UUIDBenchmark {
    private TimeBasedGenerator timeBasedGenerator;

    @Setup
    public void setup() {
        this.timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    }

    @Benchmark
    @Fork(1)
    public UUID testSecureRandomUUID() {
        return UUID.randomUUID();
    }

    @Benchmark
    @Fork(1)
    public UUID testJUGUUID() {
        return this.timeBasedGenerator.generate();
    }

    @Benchmark
    @Fork(value = 1,
            jvmArgs = {"-Djava.security.egd=file:/dev/./urandom"})
    public UUID testThreadLocalSecureUUID() {
        return ThreadLocalUUIDGenerator.generateUUID();
    }
}
