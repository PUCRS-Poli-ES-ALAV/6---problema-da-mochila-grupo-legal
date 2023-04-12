import java.util.ArrayList;
import java.util.List;

public class Fib extends Benchmark {
	private static final int NUM_BENCHES = 3;

	private static List<Long> fibs = new ArrayList<>() {{
		add(0L);
		add(1L);
		add(1L);
	}};

	public static void main(String[] args) {
		int[] nums = { 4, 8, 16, 32, 128, 1000, 10000 };
		int firstBench = 0;

		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > 32) {
				firstBench = 1;
			}

			benchmark(nums[i], firstBench);
			System.out.println();
		}
	}

	public static void benchmark(int n, int firstbench) {
		Fib.resetIters(Fib.NUM_BENCHES);

		if (firstbench <= 0) {
			Fib.resetTimer();
			Fib.printResults(0, "fibRec", n, Fib.fibRec(n));
		}

		if (firstbench <= 1) {
			Fib.resetTimer();
			Fib.printResults(1, "fibIter", n, Fib.fibIter(n));
		}

		if (firstbench <= 2) {
			Fib.resetTimer();
			Fib.printResults(2, "fibLookUp", n, Fib.fibLookUp(n));
		}
	}

	public static long fibRec(int n) {
		Fib.incrementIter(0);

		if (n <= 1) {
			return n;
		}

		return fibRec(n - 1) + fibRec(n - 2);
	}

	public static long fibIter(int n) {
		long[] fibs = new long[n + 1];
		fibs[0] = 0;
		fibs[1] = 1;

		for (int i = 2; i < n + 1; i++) {
			Fib.incrementIter(1);
			fibs[i] = fibs[i - 1] + fibs[i - 2];
		}

		return fibs[n];
	}

	public static long fibLookUp(int n) {
		Fib.incrementIter(2);

		if (Fib.fibs.size() > n) {
			return Fib.fibs.get(n);
		}

		Fib.fibs.add(fibLookUp(n - 1) + fibLookUp(n - 2));
		return Fib.fibs.get(n);
	}
}
