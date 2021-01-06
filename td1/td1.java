import java.util.Arrays;

public class Td1 {
    private static void permute(int[] T, int i, int j){
        int tmp = T[i];
        T[i] = T[j];
        T[j] = tmp;
    }
    public static int[] pplstc(final int[] T){
        int[] record = new int[]{0,0};
        int[] actuel = new int[]{0,0};
        for(int i=1; i<T.length; i++){
            if(T[i] == T[i-1]){
                actuel[1] = i;
            } else {
                actuel = new int[]{i,i};
            }
            if((actuel[1] - actuel[0]) > (record[1] - record[0])){
                record = actuel;
            }
        }
        return record;
    }
    public static int[] segmenter(int T[], int i, int j){
        int k1 = i;
        int k2 = k1+1;
        int jp = k2;
        while(jp < j){
            if(T[jp] > T[k1]){
                jp++;
            } else if(T[jp] == T[k1]) {
                permute(T, k2, jp);
            } else {
                permute(T, jp, k1);
                permute(T, jp, k2);
                k1++; k2++; jp++;
            }
        }
        return new int[]{k1, k2};
    }
    public static void quicksort(int T[], i, j){
        if((j-i) > 1){
            int[] k1k2 = segmenter(T, i, j);
            quicksort(T, i, k1k2[0]);
            quicksort(T, k1k2[1], j);
        }
    }
    public static void main(final String[] args){
        int[][] tests = {
            {2, 1, 6, 6, 5, 5, 5, 5, 3, 3, 3, 3, 3},
            {5, 5, 5, 6, 6, 6, 6, 6, 2, 3, 3, 6, 6, 6},
            {7, 7, 7, 7, 1, 1, 2, 3, 3, 3},
            {7, 7, 7, 7, 1, 1, 2, 3, 3, 3, 3, 3, 3, 3}
        };
        for(int[] tab : tests){
            System.out.println("Resultat pour " + Arrays.toString(tab) + ":\n" + Arrays.toString(pplstc(tab)));
        }
    }
}