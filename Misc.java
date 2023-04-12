import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Miscellaneous benchmark suite.
 *
 * Applications:
 *	- maxVal1: Maximum value of a list without divide and conquer
 *	- maxVal2: Maximum value of a list with divide and conquer
 *	- mergeSort: Fast sorting algorithm
 *	- karatsuba: Fast multiplication algorithm
 */
public class Misc extends Benchmark {
	private static final int NUM_BENCHES = 4;

	public static void main(String[] args) {
		int[] listSizes = { 32, 2048, 1048576 };

		for (int s : listSizes) {
			Misc.benchmarkList(s);
			System.out.println();
		}

		int[] intSizes = { 4, 16, 64 };

		for (int s: intSizes) {
			Misc.benchmarkKaratsuba(s);
			System.out.println();
		}
	}

	/**
	 * Benchmarks the applications which work on lists.
	 *
	 * A new randomly filled list of a certain size is generated for each run.
	 *
	 * @param size the list size
	 */
	public static void benchmarkList(int size) {
		Misc.resetIters(Misc.NUM_BENCHES);
		List<Integer> list = randomList(size);

		Misc.resetTimer();
		Misc.printResults(0, "maxVal1", size, maxVal1(list));

		Misc.resetTimer();
		Misc.printResults(1, "maxVal2", size, maxVal2(list));

		Misc.resetTimer();
		Misc.printResults(2, "mergeSort", size, mergeSort(list), false);
	}

	/**
	 * Benchmarks the Karatsuba algorithm.
	 *
	 * @param size the integer size in bits
	 */
	public static void benchmarkKaratsuba(int size) {
		Misc.resetIters(Misc.NUM_BENCHES);
		Misc.resetTimer();

		Misc.printResults(
			3, "karatsuba", size,
			karatsuba(Long.MAX_VALUE, Long.MAX_VALUE, size)
		);
	}

	/**
	 * Finds the maximum value in a list, using an iterative method
	 *
	 * @param list the list to be used
	 * @return the maximum value in the list
	 */
	public static Integer maxVal1(List<Integer> list) {
		Integer max = list.get(0);

		for (Integer x : list) {
			Misc.incrementIter(0);

			if (x > max) {
				max = x;
			}
		}

		return max;
	}

	/**
	 * Finds the maximum value in a list, using divide and conquer.
	 *
	 * @param list the list to be used
	 * @return the maximum value in the list
	 */
	public static Integer maxVal2(List<Integer> list) {
		return maxVal2(list, 0, list.size() - 1);
	}

	private static Integer maxVal2(
		List<Integer> list, Integer start, Integer end
	) {
		Misc.incrementIter(1);

		if (end - start <= 1) {
			return Math.max(list.get(start), list.get(end));
		} else {
			int m = (start + end) / 2;
			int v1 = maxVal2(list, start, m);
			int v2 = maxVal2(list, m + 1, end);
			return Math.max(v1, v2);
		}
	}

	/**
	 * Sorts a list of comparable elements using the merge sort algorithm.
	 *
	 * @param <T> the type of elements in the list
	 * @param list the list of elements to sort
	 * @return a new list with the elements from list in ascending order
	*/
	public static <T extends Comparable<T>> List<T> mergeSort(List<T> list) {
		Misc.incrementIter(2);

		if (list.size() < 2) {
			return list;
		}

		List<T> a = mergeSort(list.subList(0, list.size() / 2));
		List<T> b = mergeSort(list.subList(list.size() / 2, list.size()));

		// Merge both arrays.
		List<T> res = new ArrayList<>(list.size());
		int i = 0;
		int j = 0;

		// While both arrays are not exhausted,
		// add the smallest element.
		while (i < a.size() && j < b.size()) {
			if (a.get(i).compareTo(b.get(j)) <= 0) {
				res.add(a.get(i));
				i++;
			} else {
				res.add(b.get(j));
				j++;
			}
		}

		// Add the remaining elements.
		while (i < a.size()) {
			res.add(a.get(i));
			i++;
		}

		while (j < b.size()) {
			res.add(b.get(j));
			j++;
		}

		return res;
	}

	/**
	 * Karatsuba algorithm for fast multiplication.
	 *
	 * @param x the first operand
	 * @param y the second operand
	 * @param size the integer size in bits
	 * @return x * y
	 */
	public static long karatsuba(long x, long y, int size) {
		Misc.incrementIter(3);

		if (size == 1) {
			return x * y;
		}

		int m = (int) Math.ceil(size / 2);
		long a = (long) (Math.floor(x / Math.pow(2, m)));
		long b = (long) (x % Math.pow(2, m));
		long c = (long) (Math.floor(x / Math.pow(2, m)));
		long d = (long) (y % Math.pow(2, m));
		long e = Misc.karatsuba(a, c, m);
		long f = Misc.karatsuba(b, d, m);
		long g = Misc.karatsuba(b, c, m);
		long h = Misc.karatsuba(a, d, m);

		return (long) (Math.pow(2, 2 * m)) * e
			+ (long) (Math.pow(2, m)) * (g + h) + f;
	}

	/**
	 * Generate a random list of a certain size.
	 *
	 * @param size the size of the resulting list
	 * @return the list of randomly generated elements
	 */
	private static List<Integer> randomList(int size){
		List<Integer> list = new ArrayList<>(size);
		Random random = new Random();
		int bound = size * 5;

		for(int _i = 0; _i < size; _i++){
			list.add(random.nextInt(bound));
		}

		return list;
	}
}
