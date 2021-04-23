import java.lang.Math;
import java.util.Arrays;

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

    public static int[][][] calculerMA(int[][] G) {
        int n = G.length;
        int S = G[0].length;
        int[][] A = new int[n + 1][S + 1];
        int[][] M = new int[n + 1][S + 1];

        for (int i = 0; i < n + 1; i++)
            M[i][0] = 0;

        for (int i = 1; i < n + 1; i++) {
            for (int s = 0; s < S + 1; s++) {
                M[i][s] = Integer.MIN_VALUE;
                for (int sp = 1; sp < S + 1; sp++) {
                    int m = G[i - 1][sp] + M[i - 1][s - sp];
                    if (m > M[i][s]) {
                        M[i][s] = m;
                        A[i][s] = sp;
                    }
                }
            }
        }

        return new int[][][] { M, A };
    }

    static void afficher(int[][] B) {
        int m = B.length, n = B[0].length;
        // affichage des valeurs d'un tableau de booléens.
        for (int l = m - 1; l >= 0; l--)
            System.out.println(l + " : " + Arrays.toString(B[l]));
    }

    public static void main(String[] args) {
        // int na = args.length;
        // int S = Integer.parseInt(args[0]);
        // int[] Y = new int[na - 1];
        // int n = Y.length;

        // for (int i = 0; i < n - 1; i++)
        // Y[i] = Integer.parseInt(args[i + 1]);

        // boolean[][] E = calculerE(Y, S);
        // // Idealement on afficherait E

        // if (E[n][S]) {
        // ase(E, Y, n, S);
        // System.out.println();
        // } else
        // System.out.println("Il n'existe pas de sous-tableau de somme S dans Y");
        int[][] G = new int[][] { { 10, 12, 12, 14, 14, 15, 15, 15, 17, 17, 17 },
                { 10, 10, 12, 12, 16, 16, 16, 16, 16, 16, 16 }, { 10, 10, 14, 14, 14, 14, 14, 14, 14, 16, 16 },
                { 10, 14, 14, 14, 16, 16, 16, 16, 16, 16, 16 }, { 10, 10, 10, 12, 12, 13, 13, 14, 15, 16, 16 },
                { 8, 8, 10, 10, 10, 12, 12, 14, 14, 14, 14 }, { 5, 5, 5, 7, 7, 10, 10, 12, 12, 13, 13 } };
        int[][][] MA = calculerMA(G);
        afficher(MA[0]);
        System.out.println("");
        afficher(MA[1]);
    }
}
