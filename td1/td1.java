import java.util.Arrays;
import java.lang.Math;

public class Td1 {
    private static void permute(int[] T, int i, int j) {
        int tmp = T[i];
        T[i] = T[j];
        T[j] = tmp;
    }

    public static int[] pplstc(final int[] T) {
        int[] actuel = new int[] { 0, 0 };
        int[] record = actuel;
        for (int i = 1; i < T.length; i++) {
            if (T[i] == T[i - 1]) {
                actuel[1]++;
            } else {
                actuel = new int[] { i, i };
            }
            if ((actuel[1] - actuel[0]) > (record[1] - record[0])) {
                record = actuel;
            }
        }
        return record;
    }

    public static int segmenter(int T[], int i, int j) {
        int k = i;
        int jp = k + 1;
        while (jp < j) {
            if (T[jp] > T[k]) {
                jp++;
            } else {
                permute(T, jp, k + 1);
                permute(T, k, k + 1);
                k++;
                jp++;
            }
        }
        return k;
    }

    public static void quicksort(int T[], int i, int j) {
        if (j - i >= 2) {
            int pivot = segmenter(T, i, j);
            quicksort(T, i, pivot);
            quicksort(T, pivot + 1, j);
        }
    }

    /*
     * I(k, k_a, k_b) : A inter B = C[0:k] union (A[k_a : n_a] inter B[k_b : n_b])
     */
    public static int[] inter(int A[], int B[]) {
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

    public static void main(final String args[]) {
        int[][] tests_pplstc = { { 2, 1, 6, 6, 5, 5, 5, 5, 3, 3, 3, 3, 3 },
                { 5, 5, 5, 6, 6, 6, 6, 6, 2, 3, 3, 6, 6, 6 }, { 7, 7, 7, 7, 1, 1, 2, 3, 3, 3 },
                { 7, 7, 7, 7, 1, 1, 2, 3, 3, 3, 3, 3, 3, 3 } };
        int[][] tests_qs = { { 1, 2, 3, 4, 5, 8, 6, 7, 9 }, { 2, 6, 7, 1, 0, 0, 0, 6 } };
        for (int[] tab : tests_pplstc) {
            System.out.println("Resultat pplstc pour " + Arrays.toString(tab) + " : " + Arrays.toString(pplstc(tab)));
        }
        for (int[] tab : tests_qs) {
            System.out.print("Resultat quickstort pour " + Arrays.toString(tab) + " : ");
            quicksort(tab, 0, tab.length);
            System.out.println(Arrays.toString(tab));
        }
    }
}