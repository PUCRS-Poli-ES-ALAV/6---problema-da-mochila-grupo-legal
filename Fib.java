import java.util.ArrayList;
import java.util.List;

public class Fib extends Benchmark {
	protected static int num_benches = 3;

	private static List<Long> fibs = new ArrayList<>() {{
		add(0L);
		add(1L);
		add(1L);
	}};

	public static void main(String[] args) {
		int[] nums = { 4, 8, 16, 32 };

		for (int n : nums) {
			benchmark(n);
		}
	}

	public static void benchmark(int n) {
		double time;

		Fib.resetTimer();
		Fib.fibRec(n);
		time = Fib.stopTimer();
		System.out.println(time + " ms");

		Fib.resetTimer();
		Fib.fibIter(n);
		time = Fib.stopTimer();
		System.out.println(time + " ms");

		Fib.resetTimer();
		Fib.fibLookUp(n);
		time = Fib.stopTimer();
		System.out.println(time + " ms");
	}

	public static long fibRec(int n) {
		Fib.iters[0] += 1;

		if (n <= 1) {
			return n;
		}

		return fibRec(n - 1) + fibRec(n - 2);
	}

	public static long fibIter(int n) {
		Fib.iters[1] += 1;

		long[] fibs = new long[n + 1];
		fibs[0] = 0;
		fibs[1] = 1;

		for (int i = 2; i < n + 1; i++) {
			fibs[i] = fibs[i - 1] + fibs[i - 2];
		}

		return fibs[n];
	}

	public static long fibLookUp(int n) {
		Fib.iters[2] += 1;

		if (Fib.fibs.size() > n) {
			return Fib.fibs.get(n);
		}

		Fib.fibs.add(fibLookUp(n - 1) + fibLookUp(n - 2));
		return Fib.fibs.get(n);
	}
}
