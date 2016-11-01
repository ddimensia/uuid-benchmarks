package com.github.cchacin;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 16,
        time = 2500,
        timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 16,
        time = 2500,
        timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class UUIDBenchmark {
    private TimeBasedGenerator timeBasedGenerator;

    @Setup
    public void setup() {
        this.timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    }

    @Benchmark
    public UUID testSecureRandomUUID() {
        return UUID.randomUUID();
    }

    @Benchmark
    public UUID testJUGUUID() {
        return this.timeBasedGenerator.generate();
    }
}
