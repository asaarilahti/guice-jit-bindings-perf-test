## How to run
* mvn clean install
* java -jar target/benchmarks.jar

## Example result from a 56 core instance

    Benchmark                  Mode  Cnt         Score        Error  Units
    MyBenchmark.testExplicit  thrpt  200  40267365.435 ± 213715.507  ops/s
    MyBenchmark.testJit       thrpt  200   3182249.432 ±  54969.581  ops/s

## Stack trace from testJit
55 threads blocked on (Guice 4.2):

    "org.sample.MyBenchmark.testJit-jmh-worker-56" #78 daemon prio=5 os_prio=0 tid=0x00007f9f602b0800 nid=0x1bdb6 waiting for monitor entry [0x00007f9edfefc000]
       java.lang.Thread.State: BLOCKED (on object monitor)
            at com.google.inject.internal.InjectorImpl.getJustInTimeBinding(InjectorImpl.java:254)
            - waiting to lock <0x00000003d5a0c720> (a com.google.inject.internal.InheritingState)
            at com.google.inject.internal.InjectorImpl.getBindingOrThrow(InjectorImpl.java:222)
            at com.google.inject.internal.InjectorImpl.getProviderOrThrow(InjectorImpl.java:1040)
            at com.google.inject.internal.InjectorImpl.getProvider(InjectorImpl.java:1071)
            at com.google.inject.internal.InjectorImpl.getProvider(InjectorImpl.java:1034)
            at com.google.inject.internal.InjectorImpl.getInstance(InjectorImpl.java:1086)
            at org.sample.MyBenchmark.testJit(MyBenchmark.java:34)
            at org.sample.generated.MyBenchmark_testJit_jmhTest.testJit_thrpt_jmhStub(MyBenchmark_testJit_jmhTest.java:124)
            at org.sample.generated.MyBenchmark_testJit_jmhTest.testJit_Throughput(MyBenchmark_testJit_jmhTest.java:85)

## vmstat output

### testExplicit (95 % cpu usage)
    procs -----------------------memory---------------------- ---swap-- -----io---- -system-- --------cpu--------
     r  b         swpd         free         buff        cache   si   so    bi    bo   in   cs  us  sy  id  wa  st
    57  0            0      9065572         4172      7472340    0    0     0     6 57351 9738 94   0   5   0   0
    56  0            0      9063996         4172      7472312    0    0     0     2 57357 9720 95   0   5   0   0
    57  0            0      9074628         4172      7472312    0    0     0    15 57357 9827 94   0   6   0   0

### testJit (6 % cpu usage)
    procs -----------------------memory---------------------- ---swap-- -----io---- -system-- --------cpu--------
     r  b         swpd         free         buff        cache   si   so    bi    bo   in   cs  us  sy  id  wa  st
     4  0            0     12288164         4172      7472536    0    0     0     7 37412 226305 6  1  94   0   0
     5  0            0     12263984         4172      7472536    0    0     0    10 36783 219193 6  1  93   0   0
     4  0            0     12267280         4172      7472536    0    0     0     0 36537 220927 6  1  93   0   0
