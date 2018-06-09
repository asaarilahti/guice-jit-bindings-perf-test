package org.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MyBenchmark
{

    @State(Scope.Benchmark)
    public static class BenchmarkState
    {
        final Injector jitInjector = Guice.createInjector();
        final Injector explicitInjector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(TestClass.class);
            }
        });
    }

    @Benchmark
    @Threads(56)
    public void testJit(BenchmarkState state, Blackhole blackHole)
    {
        blackHole.consume(state.jitInjector.getInstance(TestClass.class));
    }

    @Benchmark
    @Threads(56)
    public void testExplicit(BenchmarkState state, Blackhole blackHole)
    {
        blackHole.consume(state.explicitInjector.getInstance(TestClass.class));
    }

}
