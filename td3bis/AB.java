import java.lang.Math;

public class AB {
    private int v;
    private AB g, d;

    AB(int v, AB g, AB d) {
        this.v = v;
        this.g = g;
        this.d = d;
    }

    static int racine(AB a) {
        return a.v;
    }

    static AB gauche(AB a) {
        return a.g;
    }

    static AB droite(AB a) {
        return a.d;
    }

    static boolean vide(AB a) {
        return a == null;
    }

    static int hauteur(AB a) {
        if (vide(a))
            return -1;
        return 1 + Math.max(hauteur(gauche(a)), hauteur(droite(a)));
    }

    static int taille(AB a) {
        if (vide(a))
            return 0;
        return 1 + taille(gauche(a)) + taille(droite(a));
    }

    static AB inserer(int v, AB a) {
        if (vide(a))
            return new AB(v, null, null);
        if (racine(a) == v)
            return a;
        else if (racine(a) > v)
            return new AB(racine(a), inserer(v, gauche(a)), droite(a));
        else
            return new AB(racine(a), gauche(a), inserer(v, droite(a)));
    }

    static AB ahmin(int[] T) {
        int n = T.length;
        return ahmin(T, 0, n);
    }

    private static AB ahmin(int[] T, int i, int j) {
        if (j - i <= 0)
            return null;
        int k = (i + j) / 2;
        return new AB(T[k], ahmin(T, i, k), ahmin(T, k + 1, j));
    }

    static void grd(AB a) {
        if (!vide(a)) {
            grd(gauche(a));
            System.out.print(racine(a) + " ");
            grd(droite(a));
        }
    }
}
