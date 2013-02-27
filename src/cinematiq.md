# On s'échauffe

Laïus sur la fonctionalité demandée; calcul de barycentre de points...

Créer notre classe de test: `BarycenterTest` via le menu `New / Junit Test Case`
La classe vient avec un test tout fait que l'on execute aussitôt pour le voir devenir échoué comme attendu.
On corrige le test:

```java
@Test
public void test() {
	//fail("Not yet implemented");
	assertTrue(true);
}
```

On relance, c'est désormais vert! hurray!!
On en profite pour s'assurer la vue JUnit que l'on a décocher la case 
(dans le drop down en haut à droite) `Show Test in hierarchy` 

# Notre premier vrai test

Laïus de toujours commencer par un `assertEquals...`
Le nom du test importe peu pour le moment, on raffinera après.

*On mettra tout de suite des décimaux, pour la génération automatique du construteur*

```java
@Test
public void onePoint() {
	assertEquals(new Point(17.0, 23.0), new Point(17.0, 23.0));
}
```

Cela nous amène à plusieurs choses:
1. Il nous manque la classe `Point`
2. Il nous faut implémenter la notion d'égalité entre deux `Point`

-> Passage Clavier

Créer la classe `Point` avec son constructor, mais sans champs 

```java
public class Point {

	public Point(double x, double y) {
	}

}
```

On lance le test: échec => il est nécessaire d'implementer  
la méthode de comparaison: une méthode `equals` qui renvoie
toujours `true`
 
```java
public class Point {

	public Point(double x, double y) {
	}

	@Override
	public boolean equals(Object o) {
		return true;
	}
}
```
 
Le test passe...

Complétons avec un *contre*-cas:
En créant, notre contre cas, renomons le premier test en: 
two_points_with_same_coordinate_are_equals` et créons le
test `two_points_with_different_coordinate_are_not_equals`

On rajoute `import static org.hamcrest.CoreMatchers.*;` pour faciliter
l'écriture de nos assertions:

```java
	@Test
	public void two_points_with_different_coordinate_are_not_equals() {
		Point point1 = new Point(17.0, 23.0);
		Point point2 = new Point(14.0, -2.0);
		assertThat(point1, not(equalTo(point2)));
	}
```

Le test est en échec

On corrige la méthode `equals` pour prendre en compte les coordonnées.

```java
@Override
public boolean equals(Object o) {
	if(o instanceof Point) {
		Point p = (Point)o;
		return MathsUtils.equals(p.x, x) && MathsUtils.equals(p.y, y);
	}
	return true;
}
```

On notera au passage la nécessité de conserver les valeurs x et y, 
jusqu'à présent non utilisées.

Au passage on marquera les champs `final` parce que c'est bien!

```java
	public final double x;
	public final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
```


Et hop, le test passe

Bon et notre barycentre dans ton ça? 
Le barycentre d'un point est lui même:

```java
	@Test
	public void single_point_is_its_own_barycenter() {
		assertEquals(new Point(7.0, 12.0), barycenterOf(new Point(7.0,12.0)));
	}
```

Création de la méthode `barycenterOf`

```java
	private Point barycenterOf(Point point) {
		return point;
	}
```

Teste passe tout de suite :)
On enchaine donc rapidement à deux points: `middle_of_two_points`

```
	@Test
	public void middle_of_two_points() {
		Point point1 = new Point(10.0, 25.0);
		Point point2 = new Point(20.0, 35.0);
		assertEquals(new Point(15.0, 30.0), barycenterOf(point1, point2));
	}
```

On créé une seconde méthode qui ce coup-ci prend deux points en paramètres:

```java
	private Point barycenterOf(Point point1, Point point2) {
		return new Point((point1.x + point2.x)/2.0, (point1.y + point2.y)/2.0);
	}
```

Et... le test passe :)

Rouge -> Vert -> Refactoring

Y a-t-il des choses à refactorer ?
Eh bien on pourrait alléger la longue ligne en ajoutant 
une méthode `add` permettant d'ajouter un point à un autre.

```java
	private Point barycenterOf(Point point1, Point point2) {
		return point1.add(point2).scale(0.5);
	}
```

On implémente rapidement les méthodes `add` et `scale`:

```java
	public Point add(Point other) {
		return new Point(x + other.x, y + other.y);
	}

	public Point scale(double f) {
		return new Point(x * f, y * f);
	}
```

On relance les tests pour s'assurer que rien n'a été cassé: OK

Passons désormais à 3 points:

```java
	@Test
	public void barycenter_of_three_points() {
		Point point1 = new Point(10.0, 25.0);
		Point point2 = new Point(20.0, 35.0);
		Point point3 = new Point(30.0,  0.0);
		assertEquals(new Point(20.0, 20.0), barycenterOf(point1, point2, point3));
	}
```

```java
	private Point barycenterOf(Point point1, Point point2, Point point3) {
		return point1.add(point2).add(point3).scale(1.0/3.0);
	}
```

Et hop le test passe.

```java
	private Point barycenterOf(Point point) {
		return point;
	}

	private Point barycenterOf(Point point1, Point point2) {
		return point1.add(point2).scale(0.5);
	}

	private Point barycenterOf(Point point1, Point point2, Point point3) {
		return point1.add(point2).add(point3).scale(1.0/3.0);
	}
```

Dans notre phase de refactoring, voyons comment nous pourrions simplifier cela.
Remplaçons, les paramètres par un nombre variable de paramètres:

```java
	private Point barycenterOf(Point point, Point...points) {
		Point eq = point;
		for(Point pt : points) {
			eq = eq.add(pt);
		}
		return eq.scale(1.0 / (1 + points.length));
	}
```

On relance les tests : Ouf on a rien cassé

Continuons notre simplification et supprimons la méthode `barycenterOf(Point, Point)`

On relance les tests : ça passe toujours

Continuons sur notre envolée et supprimons la méthode `barycenterOf(Point)`

On relance les tests : ça passe toujours uh uh!
