public class MathsUtils {

	public static boolean equals(double a, double b) {
		return Math.abs(a - b) < 1e-6;
	}

}
