public class EditDistance extends Benchmark {
	private static final int NUM_BENCHES = 2;

	public static void main(String[] args) {
		String[] strings = {
			"Casablanca",
			"Portentoso",

			"Maven, a Yiddish word meaning accumulator of knowledge, began as an attempt to " +
			"simplify the build processes in the Jakarta Turbine project. There were several" +
			" projects, each with their own Ant build files, that were all slightly different." +
			"JARs were checked into CVS. We wanted a standard way to build the projects, a clear "+
			"definition of what the project consisted of, an easy way to publish project information" +
			"and a way to share JARs across several projects. The result is a tool that can now be" +
			"used for building and managing any Java-based project. We hope that we have created " +
			"something that will make the day-to-day work of Java developers easier and generally help " +
			"with the comprehension of any Java-based project.",

			"This post is not about deep learning. But it could be might as well. This is the power of " +
			"kernels. They are universally applicable in any machine learning algorithm. Why you might" +
			"ask? I am going to try to answer this question in this article." +
			"Go to the profile of Marin Vlastelica Pogančić" +
			"Marin Vlastelica Pogančić Jun"
		};

		int firstBench = 0;

		for (int i = 0; i < strings.length; i += 2) {
			if (i > 0) {
				firstBench = 1;
			}

			EditDistance.EdInput input = new EditDistance.EdInput(
				strings[i], strings[i + 1], firstBench
			);

			benchmark(input);
			System.out.println();
		}
	}

	public static void benchmark(EditDistance.EdInput input) {
		EditDistance.resetIters(EditDistance.NUM_BENCHES);

		if (input.firstBench() <= 0) {
			EditDistance.resetTimer();
			EditDistance.printResults(
				0, "edRec", input, edRec(input.s(), input.t())
			);
		}

		if (input.firstBench() <= 1) {
			EditDistance.resetTimer();
			EditDistance.printResults(
				1, "edDP", input, edDP(input.s(), input.t())
			);
		}
	}

    /**
	 * Recursive solution to the Edit Distance problem.
	 *
     * @param s the first string
     * @param t the second string
     * @return the edit distance between s and t
     */
    public static int edRec(String s, String t){
		return edRec(s, t, s.length() - 1, t.length() - 1);
    }

	private static int edRec(String s, String t, int m, int n) {
		EditDistance.incrementIter(0);

        // Base cases.
        if (m < 0 || n < 0) {
            if (m < 0 && n < 0) {
                return 0;
            }

            if (m < 0) {
                return t.length();
            } else {
                return s.length();
            }
        }

        // Recursive cases.
        if(s.charAt(m) == t.charAt(n)) {
            return edRec(s, t, m - 1, n - 1);
        }

        int sub = edRec(s, t, m-1, n-1) + 1;
        int ins = edRec(s, t, m, n-1) + 1;
        int rem = edRec(s, t, m-1, n) + 1;

        return Math.min(Math.min(sub, ins), rem);
	}

    /**
	 * Dynamic programming solution to the Edit Distance problem.
	 *
     * @param s the first string
     * @param t the second string
     * @return the edit distance between s and t
     */
    public static int edDP(String s, String t){
        int m = s.length();
        int n = t.length();

        int[][] mat = new int[m + 1][n + 1];

        mat[0][0] = 0;

        for(int i = 1; i <= m; i++){
            mat[i][0] = mat[i - 1][0] + 1;
        }
        for(int j = 1; j <= n; j++){
            mat[0][j] = mat[0][j - 1] + 1;
        }

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
				EditDistance.incrementIter(1);
				int extraCost;

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    extraCost = 0;
                } else {
                    extraCost = 1;
                }

                mat[i][j] = Math.min(
					mat[i - 1][j] + 1,
					Math.min(mat[i][j - 1]+1, mat[i - 1][j - 1] + extraCost)
				);
            }
        }

        return mat[m][n];
    }

	private static record EdInput(
		String s,
		String t,
		int firstBench
	) {
		@Override
		public String toString() {
			return "|s| = " + s.length() + ", |t| = " + t.length();
		}
	}
}

