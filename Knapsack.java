import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Knapsack Problem benchmark suite.
 */
public class Knapsack extends Benchmark {
	private static final int NUM_BENCHES = 2;

	public static void main(String[] args) {
		int[] capacities = { 165, 190 };

		List<List<Knapsack.Item>> items = new ArrayList<>() {{
			add(new ArrayList<>() {{
				add(new Knapsack.Item(23, 92));
				add(new Knapsack.Item(31, 57));
				add(new Knapsack.Item(29, 49));
				add(new Knapsack.Item(44, 68));
				add(new Knapsack.Item(53, 60));
				add(new Knapsack.Item(38, 43));
				add(new Knapsack.Item(63, 67));
				add(new Knapsack.Item(85, 84));
				add(new Knapsack.Item(89, 87));
				add(new Knapsack.Item(82, 72));
			}});

			add(new ArrayList<>() {{
				add(new Knapsack.Item(56, 50));
				add(new Knapsack.Item(59, 50));
				add(new Knapsack.Item(80, 64));
				add(new Knapsack.Item(64, 46));
				add(new Knapsack.Item(75, 50));
				add(new Knapsack.Item(17, 05));
			}});
		}};

		for (int i = 0; i < capacities.length; i++) {
			KnapsackInput input = new KnapsackInput(
				capacities[i], items.get(i)
			);

			benchmark(input);
			System.out.println();
		}
	}

	public static void benchmark(KnapsackInput input) {
		Knapsack.resetIters(Knapsack.NUM_BENCHES);

		Knapsack.resetTimer();
		Knapsack.printResults(
			0, "knapsackElsner", input,
			knapsackElsner(input.capacity(), input.items())
		);

		Knapsack.resetTimer();
		Knapsack.printResults(
			1, "knapsackDP", input,
			knapsackDP(input.capacity(), input.items())
		);
	}

	/**
	 * Branch-and-bound-like solution to the Knapsack problem.
	 *
	 * This solution begins by trying to fit all available items and then
	 * operates recursively with subsets of the original item pool.
	 *
	 * @param capacity the knapsack capacity
	 * @param items the collection of available items
	 * @return the maximum possible value to be stored in the knapsack
	 */
	public static int knapsackElsner(int capacity, List<Knapsack.Item> items) {
		Knapsack.incrementIter(0);

		int totalWeight = 0;

		for (Knapsack.Item item : items) {
			totalWeight += item.weight();
		}

		if (totalWeight <= capacity) {
			return totalWeight;
		}

		List<Integer> branches = new ArrayList<>(items.size());

		for (Knapsack.Item item : items) {
			List<Knapsack.Item> itemsSubset = items.stream()
				.filter(i -> i != item)
				.collect(Collectors.toList());

			branches.add(knapsackElsner(capacity, itemsSubset));
		}

		return Collections.max(branches);
	}

    /**
	 * Dynamic programming solution to the Knapsack Problem.
	 *
     * @param capacity the knapsack capacity
     * @param items the collection of available items
     * @return the maximum possible value to be stored in the knapsack
     */
    public static int knapsackDP(int capacity, List<Knapsack.Item> items) {
        int[][] max = new int[items.size() + 1][capacity + 1];

        for (int i = 1; i < items.size(); i++) {
            for (int j = 1; j < capacity; j++) {
				Knapsack.incrementIter(1);

                if (items.get(i).weight() <= j) {
                    max[i][j] = Math.max(
                        max[i - 1][j],
						items.get(i).value()
						+ max[i - 1][j - items.get(i).weight()]
                    );
                } else {
                    max[i][j] = max[i - 1][j];
                }
            }
        }

        return max[items.size()][capacity];
    }

	private static record Item (
		int weight,
		int value
	) { }

	private static record KnapsackInput (
		int capacity,
		List<Knapsack.Item> items
	) {

		@Override
		public String toString() {
			return "c = " + capacity + ", n = " + items.size();
		}
	}
}
