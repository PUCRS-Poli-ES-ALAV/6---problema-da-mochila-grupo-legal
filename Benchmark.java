public abstract class Benchmark {
	/**
	 * Number of applications being benchmarked.
	 */
	protected static int num_benches;

	/**
	 * Number of iterations for each benchmark.
	 * Must be of size num_benches.
	 */
	protected static long[] iters;

	/**
	 * Timer used for benchmarking, in ns.
	 * Should not be read between resetTimer and stopTimer.
	 */
	private static long timer;

	/**
	 * Resets the class timer.
	 * It then should not be read before stopTimer is called.
	 */
	protected static void resetTimer() {
		Benchmark.timer = System.nanoTime();
	}

	/**
	 * Stops the class timer, storing the time elapsed in ns
	 * since it was last reset inside the field.
	 *
	 * @return the time elapsed in ms
	 */
	protected static double stopTimer() {
		Benchmark.timer = System.nanoTime() - Benchmark.timer;
		return Benchmark.timer / 1e6;
	}

	/**
	 * Reset the number of iterations for all applications.
	 */
	protected static void resetIterations() {
		for (int i = 0; i < num_benches; i++) {
			iters[i] = 0;
		}
	}
}

