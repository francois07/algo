<!-- omit in toc -->
# Projet 2 d'algorithmique

<!-- omit in toc -->
## Table des matières

- [Exercice 1](#exercice-1)
  - [Fonction CalculerMA](#fonction-calculerma)
  - [Fonction symetrique](#fonction-symetrique)
  - [Fonction coutArc](#fonction-coutarc)
  - [Fonction acm](#fonction-acm)
- [Exercice 2](#exercice-2)
  - [Fonction coutMin_et_argCoutMin](#fonction-coutmin_et_argcoutmin)
  - [Fonction coutParMinimisationLocale](#fonction-coutparminimisationlocale)
- [Résultat](#résultat)

<div style="page-break-after: always;"></div>

## Exercice 1

### Fonction CalculerMA
>`m(j)` : Coût minimum d'un chemin allant du sommet `0` au sommet `j`.

>`c(u,v)` : Coût de l'arc allant du sommet `u` au sommet `v`.

$$
m(j) =
  min_{v\in pred(j)}( m(v) + c(v,j) ),\space
  pred(j) \space\text{l'ensemble des sommets connectés à j}
$$

On procède par relâchement d'un contrainte sur l'ensemble des prédécésseurs du sommet `j`, une technique abordée lors des premiers TDs de programmation dynamique.

> `m(j,k)` : Coût minimum d'un chemin allant du sommet `0` au sommet `j` sous la contrainte que les prédécésseurs de `j` sont dans l'ensemble de sommets `[0:k]`


$$
\forall n \geqslant j, m(j,n) = m(j)
$$

$$
 \text{Base : }
 \begin{cases}
  m(0,0) = 0\\
  m(j,0) = +\infty, \forall j 
 \end{cases}
$$

$\forall k|1\leqslant k<n,$
- Si il n'existe pas d'arc `k-1->j`, $m(j,k)=m(j,k-1)$
- Si il existe un arc `k-1->j`, $m(j,k)=min\{\space m(k-1, k-1)+c(k-1, j),\space m(j, k-1)\space\}$


```java
static int[][] calculerMA(LA[] g) {
  int n = g.length;
  int[] M = new int[n];
  int[] A = new int[n];

  // Initialisation : m(j) = +inf, arg(j) = j
  for (int i = 1; i < n; i++) {
    M[i] = Integer.MAX_VALUE / 2;
    A[i] = i;
  }

  // Relachement
  for (int i = 1; i < n; i++) {
    for (LA la = g[i - 1]; !vide(la); la = la.reste()) {
      int j = la.sommet(), c = la.cout();
      int m = M[i - 1] + c;
      if (m < M[j]) {
        M[j] = m;
        A[j] = i - 1;
      }
    }
  }

  return new int[][] { M, A };
}
```

<div style="page-break-after: always;"></div>

### Fonction symetrique
On représente un arc quelconque du graphe `g` allant du sommet `i` au sommet `j` par `i : (j, cij)`. L'arc correspondant dans `gp` devrait donc être `j : (i, cij)`. Si il existe déjà un arc à l'indice j, on le met en reste.

```java
static LA[] symetrique(LA[] g) {
  int n = g.length;
  LA[] gp = new LA[n];

  // Parcours des arcs du graphe g
  for (int i = 0; i < n; i++) {
    for (LA A = g[i]; !vide(A); A = A.reste()) {
      int j = A.sommet();
      // i : (j, cij) -> j : (i, cij)
      gp[j] = new LA(i, A.cout(), gp[j]);
    }
  }

  return gp;
}
```

### Fonction coutArc
On parcours simplement la liste d'arcs `g[i]` jusqu'à trouver un arc dirigé vers le sommet `j`. Si il n'en existe pas, on retourne `-1`.

```java
static int coutArc(int i, int j, LA[] g) {
  int c = -1;

  // Recherche de l'arc sortant du sommet i dirigé vers le sommet j
  for (LA A = g[i]; !vide(A); A = A.reste()) {
    if (A.sommet() == j)
      c = A.cout();
  }

  return c;
}
```

<div style="page-break-after: always;"></div>

### Fonction acm
Le tableau A nous permet de retrouver le chemin de coût minimum `j -> j-1 -> ... -> 0`. Or, on souhaite afficher ce chemin de `0` jusqu'à `j`, c'est-à-dire dans le sens inverse. Pour cela, on aura recours à une recursion très similaire à celle du TD6. La forme de cette dernière ressemblera aussi à celle de l'inversion d'une chaîne de caractères par recursion, ce qui semble cohérent vu notre objectif.

```java
static void acm(int[] A, LA[] g, int j) {
  // Condition d'arrêt de la recursion, lorsqu'on est arrivé au sommet 0
  if (j == 0) {
    System.out.print("0");
    return;
  }
  int aj = A[j];
  // Recursion permettant l'affichage inverse du chemin optimal connu
  acm(A, g, aj);
  System.out.printf("--(%d)-->%d", coutArc(aj, j, g), j);
}
```

<div style="page-break-after: always;"></div>

## Exercice 2

### Fonction coutMin_et_argCoutMin
On cherche l'arc de cout minimum d'une liste d'arcs `las`. La structure de notre calcul sera très similaire au calcul linéaire du minimum d'un tableau. Seulement, on n'itère pas sur un tableau mais sur la liste d'arcs `las` et pour accèder aux valeurs à comparer, on utilise la méthode `cout()` de la structure `LA`.
Aussi, on retournera en plus du coût l'argument (le sommet) correspondant à ce coût minimum.

```java
static int[] coutMin_et_argCoutMin(LA las) {
  int cijstar = las.cout();
  int jstar = las.sommet();

  // Calcul de l'arc sortant de las de coût minimum
  for (LA la = las; !vide(la); la = la.reste()) {
    int c = la.cout();
    if (c < cijstar) {
      cijstar = c;
      jstar = la.sommet();
    }
  }

  return new int[] { cijstar, jstar };
}
```

### Fonction coutParMinimisationLocale
On souhaite parcourir le chemin de coût local minimum et calculer son coût total. Pour cela, on part de `g[0]` puis on détermine son sommet connecté de coût minimum avec la fonction `coutMin_et_argCoutMin()` qui retourne un tableau portant en indice 0 le coût minimum local et en indice 1 l'argument (le sommet) correspondant.

On ajoute alors le coût retourné au coût final. On répete ce procédé sur `g[jstar]`, tel quel `jstar` est l'argument retourné par la fonction précédente.

```java
static int coutParMinimisationLocale(LA[] g) {
  int c = 0;
  LA las = g[0];

  // Parcours du chemin de coût local minimum,
  // en partant du sommet 0 jusqu'au sommet final
  while (!vide(las)) {
    int[] min = coutMin_et_argCoutMin(las);
    c += min[0];
    las = g[min[1]];
  }

  return c;
}
```

## Résultat

La commande `java CCM 10 1000` retourne le texte suivant.

```
graphe G :
0 : (2,5) (4,11) (7,12) 
1 : (4,9) (3,10) (5,11) (7,7) (6,11) 
2 : (4,3) (9,8) (8,14) 
3 : (9,9) 
4 : (6,3) (5,6) (9,13) 
5 : (6,1) (7,2) (9,6) (8,5) 
6 : (8,9) 
7 : (8,8) (9,2) 
8 : (9,1) 
9 : 
M = [0, 1073741823, 5, 1073741823, 8, 14, 11, 12, 19, 13]
A = [0, 1, 0, 3, 2, 4, 4, 0, 2, 2]
Coût d'un chemin de coût minimum jusqu'en 9 : 13
[0, 1, 0, 3, 2, 4, 4, 0, 2, 2]
0--(5)-->2--(8)-->9
affichage des chemins de coût minimum de 0 à tous les autres sommets :
Il n'y a pas de chemin de 0 à 1
0--(5)-->2 coût = 5
Il n'y a pas de chemin de 0 à 3
0--(5)-->2--(3)-->4 coût = 8
0--(5)-->2--(3)-->4--(6)-->5 coût = 14
0--(5)-->2--(3)-->4--(3)-->6 coût = 11
0--(12)-->7 coût = 12
0--(5)-->2--(14)-->8 coût = 19
0--(5)-->2--(8)-->9 coût = 13
Description du graphe dans le fichier g.graphviz
Coût par minimisation locale = 21
Validation statistique à 1000 runs
.
Médiane des distances relatives : 1.454545
Max des distances relatives : 3.909091
```

La première partie de ce résultat correspond au graphe suivant.

![graphe](resultat.png)
