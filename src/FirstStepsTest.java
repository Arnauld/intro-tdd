import static org.junit.Assert.*;

import org.junit.Test;

public class FirstStepsTest {

	public static class Point {

		private final double x;
		private final double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				final Point p = (Point) obj;
				return MathsUtils.equals(x, p.x) && MathsUtils.equals(y, p.y);
			}
			return false;
		}

		public Point add(Point point) {
			return new Point(x + point.x, y + point.y);
		}

		public Point scale(double factor) {
			return new Point(x * factor, y * factor);
		}

	}

	@Test
	public void single_point_is_its_own_barycenter() {
		assertEquals(new Point(18, 34), barycenterOf(new Point(18, 34)));
	}

	@Test
	public void middle_of_two_points() {
		assertEquals(new Point(15, 25),
				barycenterOf(new Point(10, 25), new Point(20, 25)));
	}

	@Test
	public void middle_of_three_points() {
		assertEquals(
				new Point(10, 20),
				barycenterOf(new Point(10, 20), new Point(20, 10), new Point(0,
						30)));
	}

	private Point barycenterOf(Point point) {
		return point;
	}

	private Point barycenterOf(Point point1, Point point2) {
		return new Point((point1.x + point2.x) / 2.0,
				(point1.y + point2.y) / 2.0);
	}

	private Point barycenterOf(Point pt, Point... points) {
		Point eq = pt;
		for (Point point : points) {
			eq = eq.add(point);
		}
		return eq.scale(1.0 / (points.length + 1.0));
	}

}
