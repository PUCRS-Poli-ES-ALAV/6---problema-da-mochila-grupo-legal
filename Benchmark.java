/**
 * Abstract benchmark implementation.
 *
 * Measures total iterations and elapsed time of multiple applications.
 * This class should not be extended more than once per Java program,
 * since it uses multiple static fields.
 */
public abstract class Benchmark {
	/**
	 * Number of iterations for each benchmark.
	 */
	private static long[] iters;

	/**
	 * Timer used for benchmarking, in ns.
	 * Should not be read between resetTimer and stopTimer.
	 */
	private static long timer;

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
	 * Resets the class timer.
	 * It then should not be read before stopTimer is called.
	 */
	protected static void resetTimer() {
		Benchmark.timer = System.nanoTime();
	}

	/**
	 * Get the iteration count for a benchmark.
	 */
	protected static long getIter(int i) {
		return Benchmark.iters[i];
	}

	/**
	 * Increment the iteration count for a benchmark.
	 */
	protected static void incrementIter(int i) {
		Benchmark.iters[i] += 1;
	}

	/**
	 * Reset the number of iterations for all applications.
	 */
	protected static void resetIters(int num_benches) {
		Benchmark.iters = new long[num_benches];

		for (int i = 0; i < num_benches; i++) {
			Benchmark.iters[i] = 0;
		}
	}

	/**
	 * Prints the benchmark results of an application.
	 *
	 * @param <T> the input type
	 * @param <U> the output type
	 * @param i the benchmark index
	 * @param benchmarkName the benchmark name
	 * @param input the benchmark input (or vector size, etc.)
	 * @param output the benchmark output
	 * @param printOutput wether to print the application output or not
	 */
	protected static <T, U> void printResults(
		int i, String benchmarkName, T input, U output, boolean printOutput
	) {
		System.out.print(
			benchmarkName + "(" + input + "): " + getIter(i) + " iter(s) "
			+ "(" + Fib.stopTimer() + " ms)"
		);

		if (printOutput) {
			System.out.println(" = " + output);
		} else {
			System.out.println();
		}
	}

	protected static <T, U> void printResults(
		int i, String benchmarkName, T input, U output
	) {
		Benchmark.printResults(i, benchmarkName, input, output, true);
	}
}

