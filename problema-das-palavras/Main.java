public class Main{
    public static String s1 = "Casablanca";
    public static String s2 = "Portentoso";
    public static String s3 = "Maven, a Yiddish word meaning accumulator of knowledge, began as an attempt to " +
            "simplify the build processes in the Jakarta Turbine project. There were several" + 
            " projects, each with their own Ant build files, that were all slightly different." +
            "JARs were checked into CVS. We wanted a standard way to build the projects, a clear "+ 
            "definition of what the project consisted of, an easy way to publish project information" +
            "and a way to share JARs across several projects. The result is a tool that can now be" +
            "used for building and managing any Java-based project. We hope that we have created " +
            "something that will make the day-to-day work of Java developers easier and generally help " +
            "with the comprehension of any Java-based project.";
    public static String s4 = "This post is not about deep learning. But it could be might as well. This is the power of " +
            "kernels. They are universally applicable in any machine learning algorithm. Why you might" +
            "ask? I am going to try to answer this question in this article." + 
            "Go to the profile of Marin Vlastelica Pogančić" + 
            "Marin Vlastelica Pogančić Jun";

    private static long timer;

    private static void resetTimer() {
        Main.timer = System.nanoTime();
        }

        private static double stopTimer() {
            Main.timer = System.nanoTime() - Main.timer;
            return Main.timer / 1e6;
            }
    public static void main(String [] args){
        resetTimer();
        System.out.println(ed(s1, s2, s1.length() - 1, s2.length() - 1));
        System.out.println("tempo = " + stopTimer());
    }

    public static int ed(String S, String T, int i, int j){
        // Base cases.
        if (i < 0 || j < 0) {
            if (i < 0 && j < 0) {
                return 0;
            }

            if (i < 0) {
                return T.length();
            } else {
                return S.length();
            }
        }

        // Recursive cases.
        if(S.charAt(i) == T.charAt(j)) {
            return ed(S, T, i - 1, j - 1);
        }

        int sub = ed(S, T, i-1, j-1) + 1;
        int ins = ed(S, T, i, j-1) + 1;
        int rem = ed(S, T, i-1, j) + 1;

        return Math.min(Math.min(sub, ins), rem);
    }
}