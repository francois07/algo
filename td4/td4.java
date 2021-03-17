public class td4 {
    private static boolean[][] calculerE(int[] Y, int S) {
        int n = Y.length;
        boolean[][] E = new boolean[n + 1][S + 1];
        E[0][0] = true;
        for (int s = 0; s < S + 1; s++) {
            for (int k = 1; k < n + 1; k++) {
                if (s - Y[k - 1] < 0)
                    E[k][s] = E[k - 1][s];
                else
                    E[k][s] = E[k - 1][s] || E[k - 1][s - Y[k - 1]];
            }
        }
        return E;
    }

    public static void ase(boolean[][] E, int[] Y, int k, int s) {
        if (k == 0 && s == 0)
            return;
        else if (E[k - 1][s])
            ase(E, Y, k - 1, s);
        else {
            ase(E, Y, k - 1, s - Y[k - 1]);
            System.out.print(Y[k - 1] + " ");
        }
    }

    public static void main(String[] args) {
        int na = args.length;
        int S = Integer.parseInt(args[0]);
        int[] Y = new int[na - 1];
        int n = Y.length;

        for (int i = 0; i < n - 1; i++)
            Y[i] = Integer.parseInt(args[i + 1]);

        boolean[][] E = calculerE(Y, S);
        // Idealement on afficherait E

        if (E[n][S]) {
            ase(E, Y, n, S);
            System.out.println();
        } else
            System.out.println("Il n'existe pas de sous-tableau de somme S dans Y");
    }
}
