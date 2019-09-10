package base.benchmarkresources

/** [Benchmarker.measure] */
fun addToBenchmark(identifier: Any) {
    Benchmarker.measure(identifier)
}

/** [Benchmarker.calculate] */
fun finishBenchmark() {
    Benchmarker.calculate()
}