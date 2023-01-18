public class Stats {
	private static double average, sTDev;

	public static double Average(double[] x) { // calculating average for type double
		average = 0.0;
		for (double v : x) {
			average += v; // gets total
		}

		return average / (double) x.length; // divides total by length of argument to get average
	}

	public static double Average(int[] x) { // calculating average for type integer
		average = 0.0;
		for (int v : x) {
			average += v; // gets total
		}

		return average / (double) x.length; // divides total by length of argument to get average
	}

	public static double Average(long[] x) { // calculating average for type long
		average = 0.0;
		for (long v : x) {
			average += v; // gets total
		}

		return average / (double) x.length; // divides total by length of argument to get average
	}

	public static double StDev(double[] x) { // calculating standard deviation for type double
		sTDev = 0.0;
		double temp = Average(x); // first gets average of the argument
		for (double v : x) {
			sTDev += Math.pow(v - temp, 2); // for every v in array x, sTDev will store the square of value v minus
											// average
		}
		return Math.sqrt(sTDev / ((double) x.length - 1.0)); // to get standard deviation, sTDev will be divided by the
																// length of the argument
	}

	public static double StDev(int[] x) { // calculating standard deviation for type integer
		sTDev = 0.0;
		double temp = Average(x); // first gets average of the argument
		for (int v : x) {
			sTDev += Math.pow(v - temp, 2); // for every v in array x, sTDev will store the square of value v minus
											// average
		}
		return Math.sqrt(sTDev / ((double) x.length - 1.0)); // to get standard deviation, sTDev will be divided by the
																// length of the argument
	}

	public static double StDev(long[] x) { // calculating standard deviation for type long
		sTDev = 0.0;
		double temp = Average(x); // first gets average of the argument
		for (long v : x) {
			sTDev += Math.pow(v - temp, 2); // for every v in array x, sTDev will store the square of value v minus
											// average
		}
		return Math.sqrt(sTDev / ((double) x.length - 1.0)); // to get standard deviation, sTDev will be divided by the
																// length of the argument
	}

	public static long Min(long[] x) { // calculating minimum value for type long
		long min = x[0];
		for (long v : x) {
			if (v < min) { // loop through x and check if v is smaller than the initial minimum. If true,
							// it will become the new minimum
				min = v;
			}
		}
		return min;
	}

	public static double Min(double[] x) { // calculating minimum value for type double
		double min = x[0];
		for (double v : x) {
			if (v < min) { // loop through x and check if v is smaller than the initial minimum. If true,
							// it will become the new minimum
				min = v;
			}
		}
		return min;
	}

	public static int Min(int[] x) { // calculating minimum value for type integer
		int min = x[0];
		for (int v : x) {
			if (v < min) { // loop through x and check if v is smaller than the initial minimum. If true,
							// it will become the new minimum
				min = v;
			}
		}
		return min;
	}

	public static long Max(long[] x) { // calculating maximum value for type long
		long max = x[0];
		for (long v : x) {
			if (v > max) { // loop through x and check if v is bigger than the initial max. If true, it
							// will become the new max
				max = v;
			}
		}
		return max;
	}

	public static double Max(double[] x) { // calculating maximum value for type double
		double max = x[0];
		for (double v : x) {
			if (v > max) { // loop through x and check if v is bigger than the initial max. If true, it
							// will become the new max
				max = v;
			}
		}
		return max;
	}

	public static int Max(int[] x) { // calculating maximum value for type integer
		int max = x[0];
		for (int v : x) {
			if (v > max) { // loop through x and check if v is bigger than the initial max. If true, it
							// will become the new max
				max = v;
			}
		}
		return max;
	}

}
