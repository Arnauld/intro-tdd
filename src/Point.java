public class Point {

	public final double x;
	public final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			return MathsUtils.equals(p.x, x) && MathsUtils.equals(p.y, y);
		}
		return true;
	}

	public Point add(Point other) {
		return new Point(x + other.x, y + other.y);
	}

	public Point scale(double f) {
		return new Point(x * f, y * f);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
