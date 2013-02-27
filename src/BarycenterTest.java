import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class BarycenterTest {

	@Test
	public void test() {
		// fail("Not yet implemented");
		assertTrue(true);
	}

	@Test
	public void two_points_with_same_coordinate_are_equals() {
		assertEquals(new Point(17.0, 23.0), new Point(17.0, 23.0));
	}

	@Test
	public void two_points_with_different_coordinate_are_not_equals() {
		Point point1 = new Point(17.0, 23.0);
		Point point2 = new Point(14.0, -2.0);
		assertThat(point1, not(equalTo(point2)));
	}

	@Test
	public void single_point_is_its_own_barycenter() {
		assertEquals(new Point(7.0, 12.0), barycenterOf(new Point(7.0, 12.0)));
	}

	@Test
	public void middle_of_two_points() {
		Point point1 = new Point(10.0, 25.0);
		Point point2 = new Point(20.0, 35.0);
		assertEquals(new Point(15.0, 30.0), barycenterOf(point1, point2));
	}
	
	@Test
	public void barycenter_of_three_points() {
		Point point1 = new Point(10.0, 25.0);
		Point point2 = new Point(20.0, 35.0);
		Point point3 = new Point(30.0,  0.0);
		assertEquals(new Point(20.0, 20.0), barycenterOf(point1, point2, point3));
	}


	private Point barycenterOf(Point point, Point...points) {
		Point eq = point;
		for(Point pt : points) {
			eq = eq.add(pt);
		}
		return eq.scale(1.0 / (1 + points.length));
	}

}
