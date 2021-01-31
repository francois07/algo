import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Random;

public class Projet {
  static Random random = new Random();

  public static void main(String[] args) {
    int vsup = (int) Math.pow(10, 2), // les valeurs des objets seront au hasard dans [0:vsup+1]
        csup = (int) Math.pow(10, 3), // la capacité du sac sera au hasard dans [0:csup+1]
        nsup = (int) Math.pow(10, 2); // le nombre d'objets sera au hasard dans [0:nsup+1]

    int N = (int) Math.pow(10, 6); // nombre de sacs aléatoires de la validation statistique
    // (N est le nombre de "runs" de la validation statistique)

    int[] valeursSGV = new int[N], // valeurs des N sacs aléatoires, "gloutons par valeurs"
        valeursSGD = new int[N]; // valeurs des N sacs aléatoires, "gloutons par densités"
    for (int r = 0; r < N; r++) { // r est le numéro du "run" de la validation statistique
      if (r % 1000 == 0)
        System.out.print("."); // mettre un peu d'animation dans l'exécution...
      // génération d'un ensemble d'objets aléatoires en valeurs et tailles
      int n = random.nextInt(nsup + 1); // nombre d'objets au hasard dans [0:nsup+1]
      int c = random.nextInt(csup + 1), // taille du sac aléatoire dans [0:csup+1]
          tsup = c / 10; // les objets seront de taille aléatoire dans [1:tsup+1]
      // Un ensemble de n objets aléatoires, valeurs dans [0:vsup+1] et tailles dans
      // [1:tsup+1]
      objet[] objets = ObjetsAleatoires(n, vsup, tsup);
      // remarque : pas d'objet de taille 0. Ils conduiraient à une densité v/t
      // infinie.
      boolean[] sgpv = sacGloutonParValeurs(objets, c); // sac de contenance c, "glouton par valeurs"
      boolean[] sgpd = sacGloutonParDensites(objets, c); // id., "glouton par densités de valeurs"
      int valeurSGV = valeurDuSac(sgpv, objets), // valeur du sac "glouton par valeur"
          valeurSGD = valeurDuSac(sgpd, objets); // valeur du sac "glouton par densités"
      valeursSGV[r] = valeurSGV;
      valeursSGD[r] = valeurSGD;
    }
    System.out.println();
    int medianeRecursive_gpv = medianeRecursive(valeursSGV), medianeRecursive_gpd = medianeRecursive(valeursSGD);
    int medianeIterative_gpv = medianeIterative(valeursSGV), medianeIterative_gpd = medianeIterative(valeursSGD);
    float moyenne_gpv = moyenne(valeursSGV), moyenne_gpd = moyenne(valeursSGD);
    System.out.printf("medianes récursive et iterative et moyenne des valeurs des sacs : \n");
    System.out.printf("Sacs glouton par valeurs : %d, %d, %d\n", medianeRecursive_gpv, medianeIterative_gpv,
        (int) moyenne_gpv);
    System.out.printf("Sacs glouton par densité : %d, %d, %d\n", medianeRecursive_gpd, medianeIterative_gpd,
        (int) moyenne_gpd);
    EcrireDansFichier(valeursSGV, "valeursSGV.csv");
    EcrireDansFichier(valeursSGD, "valeursSGD.csv");
    System.out.println("Valeurs des sacs \"gloutons par valeurs\" dans le fichier " + "valeursSGV.csv");
    System.out.println("Valeurs des sacs \"gloutons par densités\" dans le fichier " + "valeursSGD.csv");
    // Pour générer les histogrammes : ouvrir une fenêtre terminal,
    // se placer dans le répertoire contenant les fichiers "valeursSGV.csv" et
    // "valeursSGD.csv"
    // et le fichier histogramme.py
    // et lancer la commande "python histogramme.py" (python 2.x)
    // ou "python3 histogramme.py" (python 3.x)
  }

  // Exercice 1 : CALCUL DE LA MEDIANE
  static int qselRecursif(int p, int[] T, int i, int j) {
    int m = j - i;
    if (m == 1) {
      return T[i];
    } else {
      int k = segmenter(T, i, j);
      if (p < k) {
        return qselRecursif(p, T, i, k);
      } else if (p == k) {
        return T[k];
      } else {
        return qselRecursif(p, T, k + 1, j);
      }
    }
  }

  public static int quickSelectRecursif(int p, int[] T) { // 1 <= p <= n;
    int n = T.length;
    return qselRecursif(p - 1, T, 0, n);
  }

  static int qselIteratif(int p, int[] T) {
    int n = T.length;
    int pprime = p, i = 0, j = n;
    int k, pppi;
    while (!(i == j)) {
      k = segmenter(T, i, j);
      pppi = pprime + i;
      if (i <= pppi && pppi < k) {
        j = k;
        pprime = p - i;
      } else if (k <= pppi && pppi < k + 1) {
        return T[k];
      } else {
        i = k + 1;
        pprime = p - i;
      }
    }
    return T[pprime];
  }

  public static int quickSelectIteratif(int p, int[] T) {
    return qselIteratif(p - 1, T);
  }

  static int medianeRecursive(int[] T) {
    int n = T.length;
    return quickSelectRecursif(1 + (n - 1) / 2, T);
  }

  static int medianeIterative(int[] T) {
    int n = T.length;
    return quickSelectIteratif(1 + (n - 1) / 2, T);
  }

  static int segmenter(int[] T, int i, int j) {
    int k = i, jp = k + 1;
    while (jp < j) {
      if (T[jp] > T[k]) {
        jp++;
      } else {
        permuter(T, jp, k + 1);
        permuter(T, k + 1, k);
        k++;
        jp++;
      }
    }
    return k;
  }

  static int hasard(int i, int j) {
    Random r = new Random();
    return i + r.nextInt(j - i);
  }

  static void permuter(int[] T, int i, int j) {
    int ti = T[i];
    T[i] = T[j];
    T[j] = ti;
  }

  // EXERCICE 2 :
  // Un objet est défini par son numéro i, sa valeur v, sa taille t, sa densité
  // v/t
  static class objet {
    int i, v, t;
    float d;

    objet(int i, int v, int t, float d) {
      this.i = i;
      this.v = v;
      this.t = t;
      this.d = d;
    }
  }

  // Un ensemble de n objets aléatoires à valeurs et tailles dans [0:vsup+1] et
  // [1:tsup+1]
  static objet[] ObjetsAleatoires(int n, int vsup, int tsup) {
    objet[] E = new objet[n]; // ensemble de n objets
    for (int k = 0; k < n; k++) {
      E[k] = new objet(0, 0, 0, 0);
      E[k].i = k; // On donne le numéro à l'objet.
      E[k].v = random.nextInt(vsup + 1); // On donne une valeur aléatoire v dans [0:vsup+1]
      E[k].t = 1 + random.nextInt(tsup + 1); // On donne une taille aléatoire t dans [1:tsup+1]
      E[k].d = E[k].v / E[k].t; // On calcule la densité aléatoire par rapport aux t et v précédents
    }
    return E;
  }

  static int valeurDuSac(boolean[] sac, objet[] Objets) {
    int n = sac.length;
    objet[] ooi = new objet[n]; // objets dans l'ordre initial :
    int vds = 0; // valeur du sac
    for (objet obj : Objets) {
      vds += obj.v;
    }
    return vds;
  }

  static boolean[] sac(objet[] Objets, int c) {
    // Objets triés par valeurs décroissantes ou par densités décroissantes.
    // Retourne un sac glouton selon le critère du tri.
    int n = Objets.length, r = c;
    boolean[] sac = new boolean[n];
    for (int i = 0; i < n; i++) {
      int taille = Objets[i].t;
      if (taille <= r) {
        sac[i] = true;
        r -= taille;
      } else {
        sac[i] = false;
      }
    }
    return sac;
  }

  static boolean[] sacGloutonParValeurs(objet[] Objets, int c) {
    qspvd(Objets); // tri quicksort des objets par valeurs décroissantes
    return sac(Objets, c); // sac glouton par valeurs.
  }

  static boolean[] sacGloutonParDensites(objet[] Objets, int c) {
    qspdd(Objets); // tri quicksort des objets par densités décroissantes
    return sac(Objets, c); // sac glouton par densités décroissantes
  }

  static void qspvd(objet[] Objets) {
    // quickSort des objets par valeurs décroissantes
    qspvd(Objets, 0, Objets.length);
  }

  static void qspdd(objet[] Objets) {
    // quickSort des objets par densités décroissantes
    qspdd(Objets, 0, Objets.length);
  }

  static void qspvd(objet[] Objets, int i, int j) {
    // quicksort par valeurs décroissantes de Objets[i:j]
    if (j - i < 2)
      return;
    int k = spvd(Objets, i, j);
    qspvd(Objets, i, k);
    qspvd(Objets, k + 1, j);
  }

  static void qspdd(objet[] Objets, int i, int j) {
    // quicksort par densites décroissantes
    if (j - i < 2)
      return;
    int k = spdd(Objets, i, j);
    qspdd(Objets, i, k);
    qspdd(Objets, k + 1, j);
  }

  static int spvd(objet[] Objets, int i, int j) {
    // segmentation de Objets[i:j] par valeurs décroissantes
    // I(k,jp) :
    // valeurs de Objets[i:k] >= valeurs de Objets[k] > valeurs de Objets[k+1:jp]
    int k = i, jp = k + 1;
    while (jp < j) {
      if (Objets[jp].v <= Objets[k].v) {
        jp++;
      } else {
        permuter(Objets, k + 1, k);
        permuter(Objets, jp, k + 1);
        k++;
        jp++;
      }
    }
    return k;
  }

  static int spdd(objet[] Objets, int i, int j) {
    // segmentation de Objets[i:j] par densités décroissantes
    // I(k,jp) : densités de Objets[i:k] >= densités de Objets[k:k+1] > densités de
    // Objets[k+1:jp]
    int k = i, jp = k + 1;
    while (jp < j) {
      if (Objets[jp].d <= Objets[k].d) {
        jp++;
      } else {
        permuter(Objets, k + 1, k);
        permuter(Objets, jp, k + 1);
        k++;
        jp++;
      }
    }
    return k;
  }

  static void permuter(objet[] Objets, int i, int j) {
    objet x = Objets[i];
    Objets[i] = Objets[j];
    Objets[j] = x;
  }

  static void EcrireDansFichier(int[] V, String fileName) {
    try {
      int n = V.length;
      PrintWriter ecrivain;
      ecrivain = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (int i = 0; i < n - 1; i++)
        ecrivain.println(V[i]);
      ecrivain.println(V[n - 1]);
      ecrivain.close();
    } catch (IOException e) {
      System.out.println("Erreur écriture");
    }
  }

  // CALCUL DE LA MOYENNE
  static float moyenne(int[] T) {
    int n = T.length;
    float s = 0;
    for (int i = 0; i < n; i++)
      s = s + T[i];
    return s / n;
  }

  // PROCEDURES NON UTILISEES mais utiles lors de la phase de mise au point du
  // programme
  static void afficher(String s) {
    System.out.print(s);
  };

  static void afficher(boolean[] B) {
    int n = B.length;
    for (int i = 0; i < n; i++)
      if (B[i])
        System.out.print(i + " ");
    System.out.println();
  }

  static void afficher(int[] T) {
    int n = T.length;
    for (int i = 0; i < n; i++)
      System.out.print(T[i] + " ");
    System.out.println();
  }

  static void afficher(objet[] T) {
    int n = T.length;
    afficher("i-v-t-d : ");
    for (int i = 0; i < n; i++) {
      objet o = T[i];
      System.out.printf("%d-%d-%d-%f | ", o.i, o.v, o.t, o.d);
    }
    System.out.println();
  }

  static void newline() {
    System.out.println();
  }
}