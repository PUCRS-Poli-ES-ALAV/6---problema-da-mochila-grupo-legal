public abstract class Benchmark {
	/**
	 * Timer used for benchmarking, in ns.
	 * Should not be read between resetTimer and stopTimer.
	 */
	private static long timer;

	/**
	 * Number of applications being benchmarked.
	 */
	private static int num_benches;

	/**
	 * Number of iterations for each benchmark.
	 * Must be of size num_benches.
	 */
	private static long[] iters;

	/**
	 * Resets the class timer.
	 * It then should not be read before stopTimer is called.
	 */
	private static void resetTimer() {
		Benchmark.timer = System.nanoTime();
	}

	/**
	 * Stops the class timer, storing the time elapsed in ns
	 * since it was last reset inside the field.
	 *
	 * @return the time elapsed in ms
	 */
	private static double stopTimer() {
		Benchmark.timer = System.nanoTime() - Benchmark.timer;
		return Benchmark.timer / 1e6;
	}

	/**
	 * Reset the number of iterations for all applications.
	 */
	private static void resetIterations() {
		for (int i = 0; i < num_benches; i++) {
			iters[i] = 0;
		}
	}
}

