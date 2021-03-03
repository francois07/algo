public class td3bis {
    static int sommeSequentielle(int[] T){
        int n = T.length;
        return ss(T, 0, n);
    }
    private static int ss(int[] T, int k, int n){
        if(k == n) return 0;
        return T[k] + ss(T, k+1, n);
    }
    public static void main(String[] args) {
        int[][] tests = {{1,2,3,4,5}};
        System.out.println("Sommes de toutes les valeurs de T1: " + sommeSequentielle(tests[0]));
    }
}
