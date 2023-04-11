import java.util.ArrayList;
import java.util.List;

public class Fib {
	private static List<Long> fibs = new ArrayList<>();

	public static void main(String[] args) {
		int testNum = 10;

		System.out.printf("fibRec(%d) = %d\n", testNum, Fib.fibRec(testNum));
		System.out.printf("fibIter(%d) = %d\n", testNum, Fib.fibIter(testNum));
		System.out.printf("fibLookUp(%d) = %d\n", testNum, Fib.fibLookUp(testNum));
	}

	public static long fibRec(int n) {
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
			fibs[i] = fibs[i - 1] + fibs[i - 2];
		}

		return fibs[n];
	}

	public static long fibLookUp(int n) {
		if (Fib.fibs.size() <= n) {
			return Fib.fibs.get(n);
		}

		Fib.fibs.set(n, fibLookUp(n - 1) + fibLookUp(n - 2));
		return Fib.fibs.get(n);
	}
}
