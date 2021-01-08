import java.util.Arrays;
import java.lang.Math;

public class Td1 {
    private static void permute(int[] T, int i, int j) {
        int tmp = T[i];
        T[i] = T[j];
        T[j] = tmp;
    }

    // INIT: d = 0, f = 1, dp = d, fp = f
    // TERMINATION: fp >= T.length
    // I(d,f,fp): T[d:f] premier plus long sous tableau constant du fp-prefixe de T
    static int[] pplstc(final int[] T) {
        int d = 0, f = 1;
        int dp = d, fp = f;
        while (fp < T.length) {
            if (T[fp] == T[fp - 1]) {
                fp++;
            } else {
                dp = fp;
                fp++;
            }
            if ((fp - dp) > (f - d)) {
                d = dp;
                f = fp;
            }
        }
        return new int[] { d, f };
    }

    // INIT: k1 = 0, k2 = k1, jp = k2
    // TERMINATION: jp >= j
    // I(i,j,k): T[i:k1] < T[k1:k2] < T[k2+1:j]
    static int[] segmenter(int T[], int i, int j) {
        int k1 = i, k2 = k1 + 1, jp = k2;
        while (jp < j) {
            if (T[jp] > T[k1]) {
                jp++;
            } else if (T[jp] == T[k1]) {
                permute(T, k2, jp);
                k2++;
                jp++;
            } else {
                permute(T, jp, k1);
                permute(T, jp, k2);
                k1++;
                k2++;
                jp++;
            }
        }
        return new int[] { k1, k2 };
    }

    static void quicksort(int T[], int i, int j) {
        if (j > i) {
            int[] k1k2 = segmenter(T, i, j);
            quicksort(T, i, k1k2[0]);
            quicksort(T, k1k2[1], j);
        }
    }

    // INIT: n_a = A.length, n_b = B.length, C = int[min(n_a, b_b)], k_a = k_b = 0
    // TERMINATION: k_a >= n_a || k_b >= n_b
    // I(k,k_a,k_b) : A inter B = C[0:k] union (A[k_a : n_a] inter B[k_b : n_b])
    static int[] inter(int A[], int B[]) {
        int n_a = A.length, n_b = B.length;
        int[] C = new int[Math.min(n_a, n_b)];
        int k_a = 0, k_b = 0, k = 0;
        while (k_a < n_a && k_b < n_b) {
            if (A[k_a] == B[k_b]) {
                C[k] = A[k_b];
                k++;
                k_a++;
                k_b++;
            } else {
                if (A[k_a] < B[k_b]) {
                    k_a++;
                } else {
                    k_b++;
                }
            }
        }
        return Arrays.copyOfRange(C, 0, k);
    }

    // INIT: d = f = s = dp = fp = sp = 0
    // TERMINATION: fp >= T.length
    // I(d,f): sum(T[d:f]) > Toute sum(T[i:j]) tel que i, j dans [0:d]
    static int[] pstsm(int T[]) {
        int d = 0, f = 0, s = T[0];
        int dp = d, fp = f, sp = s;
        while (fp < T.length) {
            if (sp < 0) {
                dp = fp;
                sp = T[fp];
                fp++;
            } else {
                sp += T[fp];
                fp++;
            }
            if (sp > s) {
                s = sp;
                d = dp;
                f = fp;
            }
        }
        return new int[] { d, f, s };
    }

    public static void main(final String args[]) {
        int[][] tests_pplstc = { { 1, 2, 1, 1, 3, 3, 3, 4, 4, 4 } };
        int[][] tests_qs = {
                { 3, 3, 3, 4, 0, 1, 4, 3, 4, 0, 0, 2, 3, 0, 0, 4, 3, 0, 1, 1, 0, 2, 0, 1, 2, 0, 3, 0, 2, 1, 1, 3 } };
        int[][] tests_pstsm = { { -1, 2, 1, -4, 3, 4, -6, 2, 3, 2, -3, 1 } };
        for (int[] tab : tests_pplstc) {
            System.out.println("Resultat pplstc pour " + Arrays.toString(tab) + " : " + Arrays.toString(pplstc(tab)));
        }
        for (int[] tab : tests_qs) {
            System.out.print("Resultat quickstort pour " + Arrays.toString(tab) + " : ");
            quicksort(tab, 0, tab.length);
            System.out.println(Arrays.toString(tab));
        }
        for (int[] tab : tests_pstsm) {
            System.out.println("Resultats pstsm pour " + Arrays.toString(tab) + " : " + Arrays.toString(pstsm(tab)));
        }
    }
}