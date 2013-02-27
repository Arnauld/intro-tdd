# On s'�chauffe

La�us sur la fonctionalit� demand�e; calcul de barycentre de points...

Cr�er notre classe de test: `BarycenterTest` via le menu `New / Junit Test Case`
La classe vient avec un test tout fait que l'on execute aussit�t pour le voir devenir �chou� comme attendu.
On corrige le test:

```java
@Test
public void test() {
	//fail("Not yet implemented");
	assertTrue(true);
}
```

On relance, c'est d�sormais vert! hurray!!
On en profite pour s'assurer la vue JUnit que l'on a d�cocher la case 
(dans le drop down en haut � droite) `Show Test in hierarchy` 

# Notre premier vrai test

La�us de toujours commencer par un `assertEquals...`
Le nom du test importe peu pour le moment, on raffinera apr�s.

*On mettra tout de suite des d�cimaux, pour la g�n�ration automatique du construteur*

```java
@Test
public void onePoint() {
	assertEquals(new Point(17.0, 23.0), new Point(17.0, 23.0));
}
```

Cela nous am�ne � plusieurs choses:
1. Il nous manque la classe `Point`
2. Il nous faut impl�menter la notion d'�galit� entre deux `Point`

-> Passage Clavier

Cr�er la classe `Point` avec son constructor, mais sans champs 

```java
public class Point {

	public Point(double x, double y) {
	}

}
```

On lance le test: �chec => il est n�cessaire d'implementer  
la m�thode de comparaison: une m�thode `equals` qui renvoie
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

Compl�tons avec un *contre*-cas:
En cr�ant, notre contre cas, renomons le premier test en: 
two_points_with_same_coordinate_are_equals` et cr�ons le
test `two_points_with_different_coordinate_are_not_equals`

On rajoute `import static org.hamcrest.CoreMatchers.*;` pour faciliter
l'�criture de nos assertions:

```java
	@Test
	public void two_points_with_different_coordinate_are_not_equals() {
		Point point1 = new Point(17.0, 23.0);
		Point point2 = new Point(14.0, -2.0);
		assertThat(point1, not(equalTo(point2)));
	}
```

Le test est en �chec

On corrige la m�thode `equals` pour prendre en compte les coordonn�es.

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

On notera au passage la n�cessit� de conserver les valeurs x et y, 
jusqu'� pr�sent non utilis�es.

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

Bon et notre barycentre dans ton �a? 
Le barycentre d'un point est lui m�me:

```java
	@Test
	public void single_point_is_its_own_barycenter() {
		assertEquals(new Point(7.0, 12.0), barycenterOf(new Point(7.0,12.0)));
	}
```

Cr�ation de la m�thode `barycenterOf`

```java
	private Point barycenterOf(Point point) {
		return point;
	}
```

Teste passe tout de suite :)
On enchaine donc rapidement � deux points: `middle_of_two_points`

```
	@Test
	public void middle_of_two_points() {
		Point point1 = new Point(10.0, 25.0);
		Point point2 = new Point(20.0, 35.0);
		assertEquals(new Point(15.0, 30.0), barycenterOf(point1, point2));
	}
```

On cr�� une seconde m�thode qui ce coup-ci prend deux points en param�tres:

```java
	private Point barycenterOf(Point point1, Point point2) {
		return new Point((point1.x + point2.x)/2.0, (point1.y + point2.y)/2.0);
	}
```

Et... le test passe :)

Rouge -> Vert -> Refactoring

Y a-t-il des choses � refactorer ?
Eh bien on pourrait all�ger la longue ligne en ajoutant 
une m�thode `add` permettant d'ajouter un point � un autre.

```java
	private Point barycenterOf(Point point1, Point point2) {
		return point1.add(point2).scale(0.5);
	}
```

On impl�mente rapidement les m�thodes `add` et `scale`:

```java
	public Point add(Point other) {
		return new Point(x + other.x, y + other.y);
	}

	public Point scale(double f) {
		return new Point(x * f, y * f);
	}
```

On relance les tests pour s'assurer que rien n'a �t� cass�: OK

Passons d�sormais � 3 points:

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
Rempla�ons, les param�tres par un nombre variable de param�tres:

```java
	private Point barycenterOf(Point point, Point...points) {
		Point eq = point;
		for(Point pt : points) {
			eq = eq.add(pt);
		}
		return eq.scale(1.0 / (1 + points.length));
	}
```

On relance les tests : Ouf on a rien cass�

Continuons notre simplification et supprimons la m�thode `barycenterOf(Point, Point)`

On relance les tests : �a passe toujours

Continuons sur notre envol�e et supprimons la m�thode `barycenterOf(Point)`

On relance les tests : �a passe toujours uh uh!
