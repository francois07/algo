import java.util.Arrays;

public class Projet {
    private static void permute(int[] T, int i, int j) {
        int tmp = T[i];
        T[i] = T[j];
        T[j] = tmp;
    }

    // INIT: k1 = 0, k2 = k1, jp = k2
    // TERMINATION: jp >= j
    // I(i,j,k): T[i:k1] < T[k1:k2] < T[k2+1:j]
    static int segmenter(int T[], int i, int j) {
        int k = i, jp = k + 1;
        while (jp < j) {
            if (T[jp] > T[k]) {
                jp++;
            } else {
                permute(T, jp, k + 1);
                permute(T, k + 1, k);
                k++;
                jp++;
            }
        }
        return k;
    }

    static int qsel(int p, int T[], int i, int j) {
        int permutation = segmenter(T, i, j);
        if (permutation == p) {
            return T[permutation];
        } else if (permutation < p) {
            return qsel(p, T, permutation + 1, j);
        } else {
            return qsel(p, T, i, permutation - 1);
        }
    }

    static int qselIter(int p, int T[], int i, int j) {
        int permutation = segmenter(T, i, j);
        while (permutation != p) {
            if (permutation < p) {
                permutation = segmenter(T, permutation + 1, j);
            } else {
                permutation = segmenter(T, i, permutation - 1);
            }
        }
        return T[permutation];
    }

    static int quickSelect(int p, int[] T) {
        int n = T.length;
        return qsel(p - 1, T, 0, n);
    }

    static int quickSelectIter(int p, int[] T) {
        int n = T.length;
        return qselIter(p - 1, T, 0, n);
    }

    public static void main(String[] args) {
        int[][] tests_qsel = { { 7, 10, 4, 3, 20, 15 } };
        for (int[] tab : tests_qsel) {
            System.out.println("3-eme valeur de " + Arrays.toString(tab) + ": " + quickSelect(3, tab));
            System.out.println("4-eme valeur de " + Arrays.toString(tab) + ": " + quickSelect(4, tab));
        }
        for (int[] tab : tests_qsel) {
            System.out.println("(iter) 3-eme valeur de " + Arrays.toString(tab) + ": " + quickSelectIter(3, tab));
            System.out.println("(iter) 4-eme valeur de " + Arrays.toString(tab) + ": " + quickSelectIter(4, tab));
        }
    }
}
