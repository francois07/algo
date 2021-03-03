public class td3bis {
    static int sommeSequentielle(int[] T) {
        int n = T.length;
        return ss(T, 0, n);
    }

    private static int ss(int[] T, int k, int n) {
        if (k == n)
            return 0;
        return T[k] + ss(T, k + 1, n);
    }

    static int sommeDiviserPourRegner(int[] T) {
        int n = T.length;
        return sdpr(T, 0, n);
    }

    private static int sdpr(int[] T, int i, int j) {
        if (i == j)
            return 0;
        int k = (i + j) / 2;
        return sdpr(T, i, k) + T[k] + sdpr(T, k + 1, j);
    }

    static int somme(int a, int b) {
        if (b == 0)
            return a;
        return somme(a + 1, b - 1);
    }

    static int produitSequentiel(int a, int b) {
        if (b == 0)
            return 0;
        return a + produitSequentiel(a, b - 1);
    }

    static int produitDichotomique(int a, int b) {
        if (b == 0)
            return 0;
        if (b % 2 == 0)
            return produitDichotomique(a << 1, b >> 1);
        else
            return a + produitDichotomique(a << 1, b >> 1);
    }

    static void endroit(int[] T, int k, int n) {
        if (k == n)
            return;
        System.out.print(T[k] + " ");
        endroit(T, k + 1, n);
    }

    static void envers(int[] T, int k, int n) {
        if (k == n)
            return;
        System.out.println(T[n - 1 - k]);
        envers(T, k + 1, n);
    }

    static String renversee(String c) {
        if (c.length() == 0)
            return c;
        return renversee(c.substring(1, c.length())) + c.substring(0, 1);
    }

    static boolean estPalindrome(String c) {
        if (c.length() == 1 || c.length() == 0)
            return true;
        if (c.charAt(0) != c.charAt(c.length() - 1))
            return false;
        return estPalindrome(c.substring(1, c.length() - 1));
    }

    public static void main(String[] args) {
        int[][] tests = { { 1, 2, 3, 4, 5, 6 } };
        System.out.println("Somme de toutes les valeurs de T1: " + sommeSequentielle(tests[0]));
        System.out
                .println("Somme diviser pour regner de toutes les valeurs de T1: " + sommeDiviserPourRegner(tests[0]));
        System.out.println("Somme de 2 et 3: " + somme(2, 3));
        System.out.println("Produit de 2 et 3: " + produitSequentiel(2, 3));
        System.out.println("Produit dichotomique de 2 et 3: " + produitDichotomique(2, 3));
        System.out.println("Affichage à l'endroit de [1,2,3,4]: ");
        endroit(new int[] { 1, 2, 3, 4 }, 0, 4);
        System.out.println("Affichage à l'envers de [1,2,3,4]: ");
        envers(new int[] { 1, 2, 3, 4 }, 0, 4);
        System.out.println("test renversé: " + renversee("test"));
        System.out.println("test palindrome? " + estPalindrome("test"));
        System.out.println("kayak palindrome? " + estPalindrome("kayak"));
        System.out.println("kayyak palindrome? " + estPalindrome("kayyak"));

        System.out.println("\nExercice 11");
        AB a3 = null;
        a3 = AB.inserer(3, a3);
        a3 = AB.inserer(1, a3);
        a3 = AB.inserer(5, a3);
        a3 = AB.inserer(2, a3);
        a3 = AB.inserer(4, a3);
        a3 = AB.inserer(7, a3);
        System.out.print("Valeurs de l'arbre a3, affichage grd : ");
        AB.grd(a3);
        System.out.println();
        System.out.printf("hauteur(a3) = %d, taille(a3) = %d\n", AB.hauteur(a3), AB.taille(a3));
        int[] T = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }; // 15 valeurs,
        // l'arbre de AB.hauteur minimum est de AB.hauteur 4.
        System.out.print("T = ");
        endroit(T, 0, T.length);
        AB a = AB.ahmin(T);
        System.out.print("Parcours grd de l'ahmin a : ");
        AB.grd(a);
        System.out.println();
        System.out.printf("hauteur(a) = %d\n", AB.hauteur(a));
    }
}
