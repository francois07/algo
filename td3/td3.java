public class td3 {
    static int ncss(int[] T, int s){
        int q=T.length, p=0, c=0, somme;
        while(q>0 && p<T.length && p < q){
            somme = T[p] + T[q-1];
            if(somme == s){
                // System.out.println(""+T[p]+"+"+T[q-1]+"="+(T[p]+T[q-1]));
                c++;
                q--;
                p++;
            } else if(somme > s){
                q--;
            } else {
                p++;
            }
        }
        return c;
    }

    static int ncst(int[] T){
        int c=0;
        for(int s : T){
            c+=ncss(T, s);
        }
        return c;
    }

    public static void main(final String[] args){
        int[][] tests = {{1,3,6,8,9,10}, {1,2,3,4,6}};
        System.out.println("4 apparait " + ncss(tests[0], 4) + " fois");
        System.out.println("Il y a " + ncst(tests[0]) + " sommes de valeurs qui apparaissent dans T1");
        System.out.println("Il y a " + ncst(tests[1]) + " sommes de valeurs qui apparaissent dans T2");
    }
}
