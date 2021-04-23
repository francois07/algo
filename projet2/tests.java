import java.util.Random;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class tests {
  static class LA { // liste d'arcs.
    int j, cij;
    LA r;

    LA(int j, int cij, LA r) {
      this.j = j;
      this.cij = cij;
      this.r = r;
    }

    int sommet() {
      return j;
    }

    int cout() {
      return cij;
    }

    LA reste() {
      return r;
    }
  }

  static void descriptionGraphViz(LA[] g, String fileName) {
    /*
     * g est un graphe représenté par une table de liste d'arcs. Ecrit dans le
     * fichier fileName la description du graphe du projet P pour le logiciel
     * graphViz. Cette description est à coller dans la fenêtre gauche du site
     * https://dreampuf.github.io/GraphvizOnline Voir aussi l'excellent site
     * GraphViz Pocket Reference, https://graphs.grevian.org
     */
    try {
      int n = g.length;
      PrintWriter ecrivain;
      ecrivain = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      ecrivain.println("digraph g{ rankdir=LR;");
      for (int i = 0; i < n; i++) {
        for (LA las = g[i]; (!vide(las)); las = las.reste()) {
          // las : liste des arcs sortants
          int j = las.sommet(), pij = las.cout();
          ecrivain.println(i + "->" + j + "[label=" + pij + "]" + ";");
        }
      }
      ecrivain.println("}");
      ecrivain.println(
          "/* Description à coller dans la fenêtre gauche du site " + "https://dreampuf.github.io/GraphvizOnline */");
      ecrivain.println("/* Voir aussi l'excellent site GraphViz Pocket Reference, " + "https://graphs.grevian.org */");
      ecrivain.close();
    } catch (IOException e) {
      System.out.println("Erreur écriture");
    }
  }

  static boolean vide(LA l) {
    return l == null;
  }

  static LA[] grapheAleatoire(int n) {
    LA[] g = new LA[n];
    for (int i = 0; i < n - 1; i++) {
      int[] S = permutation(i + 1, n); // S est une permutation de [i+1:n]
      int dsi = hasard(1, n - i); // ds(i) au hasard, 1 ≤ ds(i) < n-i
      S = Arrays.copyOfRange(S, 0, dsi); // les sommets vers lesquels i envoie un arc
      for (int j : S) { // pour tout arc i->j
        int cij = (j - i) + hasard(0, n); // coût de l'arc
        g[i] = new LA(j, cij, g[i]); // ajout de l'arc i -> j au graphe g.
      }
    }
    return g;
  }

  static int hasard(int i, int j) {
    Random r = new Random();
    return i + r.nextInt(j - i);
  }

  static int[] permutation(int inf, int sup) {
    int n = sup - inf;
    Random r = new Random();
    int[] T = new int[n];
    for (int i = 0; i < n; i++)
      T[i] = inf + i;
    for (int j = n - 1; j > 0; j--) {
      int i = hasard(0, j);
      permuter(T, i, j);
    }
    return T;
  }

  static void permuter(int[] T, int i, int j) {
    int ti = T[i];
    T[i] = T[j];
    T[j] = ti;
  }

  static void afficher(LA[] g) {
    int n = g.length;
    for (int i = 0; i < n; i++) {
      System.out.printf("%d : ", i);
      for (LA las = g[i]; !vide(las); las = las.reste()) {
        int j = las.sommet(), pij = las.cout();
        System.out.printf("(%d,%d) ", j, pij);
      }
      System.out.println();
    }
  }

  static LA[] symetrique(LA[] g) {
    int n = g.length;
    // retourne le graphe g', symétrique du graphe g.
    LA[] gp = new LA[n];
    for (int i = 0; i < n; i++) {
      for (LA A = g[i]; !vide(A); A = A.reste()) {
        int j = A.sommet();
        gp[j] = new LA(i, A.cout(), gp[j]);
      }
    }
    return gp;
  }

  static int[] calculerM(LA[] g) {
    /*
     * retourne un tableau MA={M,A} où M[0:n] est de terme général M[j] = m(j) =
     * coût minimum d'un chemin allant de 0 à j, et A = arg M. S'il n'existe pas de
     * chemin de 0 à j on pose m(j) = infini et arg m(j) = j
     */
    int n = g.length;
    int[] M = new int[n];

    for (int i = 1; i < n; i++) {
      M[i] = 10000;
    }
    for (LA A = g[0]; !vide(A); A = A.reste()) {
      M[A.sommet()] = A.cout();
    }
    for (int i = 1; i < n; i++) {
      for (LA A = g[i]; !vide(A); A = A.reste()) {
        int j = A.sommet();
        M[j] = Math.min(M[i] + A.cout(), M[j]);
      }
    }

    return M;
  }

  public static void main(String[] args) {
    LA[] g = grapheAleatoire(5);
    // System.out.println();
    // LA[] gp = symetrique(g);
    // afficher(gp);
    int[] M = calculerM(g);
    System.out.println(Arrays.toString(M));
    descriptionGraphViz(g, "test.txt");
  }
}
