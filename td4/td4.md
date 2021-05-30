# TD4

## Exercice 1

### Supposons le problème résolu

On a trouvé un sous-ensemble des `n` spots, de durée `≤ T`,
et de somme maximum.

Le `n`-ème spot fait-il partie de ce sous-ensemble ?

1. OUI : la somme maximum est g(n-1) + la somme maximum d'un sous-ensemble des
   n-1 premiers spots, pour un slot de durée T - d(n-1)

1. NON : la somme maximum est celle d'un sous-ensemble des
   n-1 premiers spots, pour un slot de durée T.

`m(n, T)` = gain total maximum de diffusion
d'un sous-ensemble des n premiers spots, pour un slot
de durée T.

`m(n,T) = max { g(n-1) + m(n-1, T-d(n-1)), m(n-1, T) }`

### Généralisation

`m(k,t)` = gain max d'une diffusion d'un
sous-ensemble des k premiers spots, dans un slot de durée t.

Base : m(0, t) = 0, qqsoit t, 0 ≤ t ≤ T

qqsoit `k`, `1 ≤ k ≤ n`, `0 ≤ t ≤ T`
`m(k,t) = max { g(k-1) + m(k-1, t-d(k-1)), m(k-1, t) }`

Autres cas de base possibles :

- `m(1,t) = max( g(0) + m(0, t-d(0)), 0)` qqsoit `t`, `0 <= t ≤ T`
- `m(1,t) = g(0)` si `t-d(0) ≥ 0` sinon `0`.
  **Attention** : le cas général est alors pour `2 ≤ k ≤ n`.

Cas de base : m(0, t) = 0, qqsoit t, `0 ≤ t ≤ T`

Cas général (hérédité) : qqsoit `k`, `1 ≤ k ≤ n`, `0 ≤ t ≤ T`
`m(k,t) = max { g(k-1) + m(k-1, t-d(k-1)), m(k-1, t) }`

Nous calculerons toutes les valeurs m(k,t), par k croissant, et nous mémoriserons "à la volée", toutes ces valeurs dans un tableau `M[0:n+1][0:t+1]`, de terme général `M[k][t] = m(k,t)`.

## Exercice 2

`e(n,S)`: "Il existe un sous-ensemble de Y=[0:n] de somme `S`"

### Supposons le problème résolu.

- `e(n,S) = e(n-1,S)` ou `e(n-1, S-Y[n-1])`
- `S - Y[n-1] + Y[n-1] = S`

### Cas général

qqsoit k, `1 <= k <=n` et qqsoit s, `0 <= s <=S` :
`e(k,s) = e(k-1,s) ou e(k-1, s-Y[k-1])`

Base: k=0

- `e(0,0)` : "Il existe un sous-ensemble de Y=[0:0] de somme 0" -- VRAI
- `e(0,1)` : "Il existe un sous-ensemble de Y=[0:0] de somme 1" -- FAUX
- `e(0,S)` : "Il existe un sous-ensemble de Y=[0:0] de somme `S`" -- FAUX

- Calculons `E[0:n+1][0:S+1]` de terme général `E[k][s] = e(k,s)`
- Écrivons une fonction qui affiche, s'il existe, un sous ensemble de `Y[0:n]`, de somme `S`. Soit `Z(k,s)` un sous-ensemble de `Y[0:k]`, de somme `s`.
  1. `k = 0`, alors `Z(0,s) = []`
  2. `1 <= k <= n` et le `k`-ème élément de Y n'est pas dans `Z(k,s)`. Alors `Z(k,s) = Z(k-1,s)`
  3. `k > 0` et le `k`-ème élément de Y est dans Y'(k,s). Alors `Z(k,s) = Z(k-1,s-Y[k-1]) U {Y[k-1]}`

Appel principal avec k = n et s = S

## Exercice 3

`m(n,S)` : gain total d'une répartition optimale du stock S sur le sous-ensemble des n premiers enetrepôts.

Comment avons-nous obtenu m(n,S) ?

Quelle quantité de stock a-t-ton livré au `n`-ème entrepôt?

```
m(n,S) = Max{
    g(n-1,0) + m(n-1,S-0),
    g(n-1,1) + m(n-1,S-1),
    g(n-1,2) + m(n-1,S-2),
    ...,
    g(n-1,S) + m(n-1,0)
}
```

`m(n,S) = Max{ g(n-1, q), m(n-1, S-q) }` pour `0 <= q <= S`

### Cas général

``m(k,s) = Max{ g(k-1, q), m(k-1, s-q) }` pour `0 <= q <= s`

Base: `m(0,s) = g(0,0) + g(1,0) + ... + g(n-1,0)`
