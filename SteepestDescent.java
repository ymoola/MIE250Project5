import java.util.ArrayList;

public class SteepestDescent {
	private double eps = 0.001; // tolerance
	private int maxIter = 100; // maximum number of iterations
	private double stepSize = 0.05; // step size alpha
	private double x0 = 1.0; // starting point
	private ArrayList<double[]> bestPoint = new ArrayList<>(); // best point found for all polynomials
	private double[] bestObjVal; // best objective function value found for all polynomials
	private double[] bestGradNorm; // best gradient norm found for all polynomials
	private long[] compTime; // computation time needed for all polynomials
	private int[] nIter; // no . of iterations needed for all polynomials
	private boolean resultsExist = false; // whether or not results exist

	// constructors
	public SteepestDescent() { // void constructor
	}

	public SteepestDescent(double eps, int maxIter, double stepSize, double x0) {
		this.eps = eps;
		this.maxIter = maxIter;
		this.stepSize = stepSize;
		this.x0 = x0;
	}

	// getters
	public double getEps() {
		return this.eps;
	}

	public int getMaxIter() {
		return this.maxIter;
	}

	public double getStepSize() {
		return this.stepSize;
	}

	public double getX0() {
		return this.x0;
	}

	public double[] getBestObjVal() {
		return this.bestObjVal;
	}

	public double[] getBestGradNorm() {
		return this.bestGradNorm;
	}

	public double[] getBestPoint(int i) {
		return this.bestPoint.get(i);
	}

	public int[] getNIter() {
		return this.nIter;
	}

	public long[] getCompTime() {
		return this.compTime;
	}

	public boolean hasResults() {
		return this.resultsExist;
	}

	// setters
	public void setEps(double a) {
		this.eps = a;
	}

	public void setMaxIter(int a) {
		this.maxIter = a;
	}

	public void setStepSize(double a) {
		this.stepSize = a;
	}

	public void setX0(double a) {
		this.x0 = a;
	}

	public void setBestObjVal(int i, double a) {
		this.bestObjVal[i] = a;
	}

	public void setBestGradNorm(int i, double a) {
		this.bestGradNorm[i] = a;
	}

	public void setBestPoint(int i, double[] a) {
		this.bestPoint.set(i, a);
	}

	public void setCompTime(int i, long a) {
		this.compTime[i] = a;
	}

	public void setNIter(int i, int a) {
		this.nIter[i] = a;
	}

	public void setHasResults(boolean a) {
		this.resultsExist = a;
	}

	// other methods
	
	
	public void init(ArrayList<Polynomial> P) {// initialize member arrays to correct size
		// setting dimension to size of P
		this.bestGradNorm = new double[P.size()];
		this.bestObjVal = new double[P.size()];
		this.compTime = new long[P.size()];	
		this.nIter = new int[P.size()];
		double [] points;

		for (int i = 0; i < P.size(); ++i) {
			points = new double[(P.get(i)).getN()];
			this.bestPoint.add(i, points); // i loops through size of P to initialize best point ArrayList to number of variables
											
		}
	}
	
	public boolean getParamsUser() {
		
		double eps;
		eps = (Pro5_moolayus.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.0, Double.MAX_VALUE)); // calls get double to  get tolerance
																								
		if (eps == 0.0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}
		int iter;
		iter = (Pro5_moolayus.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000)); // calls get integer to get max iterations value
																										 
		if (iter == 0.0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} 		
		double x0;
		x0 = (Pro5_moolayus.getDouble("Enter value for starting point (0 to cancel): ",Double.NEGATIVE_INFINITY, Double.MAX_VALUE)); // gets starting point
			if (x0 == 0) {
				System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
				return false;
			}
		setHasResults(false);
		this.maxIter = iter;
		this.x0 = x0;
		this.eps = eps;			
	    System.out.println("\nAlgorithm parameters set!\n");
	    return true;
	}
			
	

	public void run(int i, Polynomial P) { // run the steepest descent algorithm
		double[] copyX0 = new double[P.getN()]; // sets copyX0 to the amount of variables in P
		for (int j = 0; j < P.getN(); ++j) {
			copyX0[j] = this.x0; // setting starting point
		}
		this.setNIter(i, 0);
		double step;
		long start = System.currentTimeMillis();// Get current time
		this.bestPoint.set(i, copyX0); // sets best point with element copyX0 at index i																	 
		this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i));
		this.bestObjVal[i] = P.f(this.bestPoint.get(i)); // best objective value at index i is the function value of the i'th position from best point
		
		while (this.nIter[i] < this.maxIter && this.bestGradNorm[i] > this.eps) {		
			step = this.lineSearch(P, this.bestPoint.get(i));
			if (step == -10.0D) {
				this.setNIter(i, 1);
				break;
			} 
			else {
				double[] temp = direction(P, this.bestPoint.get(i));
				if (nIter[i] <= this.maxIter && this.bestGradNorm[i] > this.eps) { // conditions for setting best point
					for(int j = 0; j < (this.bestPoint.get(i)).length; j++) {
						double [] best = (getBestPoint(i));
						System.arraycopy(getBestPoint(i),0,best,0,getBestPoint(i).length); //copies getBestPoint array onto best array
						best[j] += step * temp[j]; //best point is set by multiplying alpha by direction for each index
						
					}
					
					
				}
					
			}
		
			
			this.setNIter(i,this.getNIter()[i]+1); //setting number of iterations
			this.bestObjVal[i] = P.f(this.bestPoint.get(i));
			this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i));
		}
		
		
		this.compTime[i] = System.currentTimeMillis() - start; // gets computation time by calculating elapsed time from start
																 
		System.out.println("Polynomial " + (i + 1) + " done in " + this.compTime[i] + "ms."); // run function output statement
																								 
		this.resultsExist = true; // sets the method to true so that results can be printed
	}
	

	public double lineSearch(Polynomial P, double[] x) {
		return -10.0D;
	  }

	public double[] direction(Polynomial P, double[] x) { // find the next direction
		double [] grad = P.gradient(x);
		double[] step = grad;
		int i;
	    for (i = 0; i < x.length; i++) 
	      step[i] = -1 * (step[i]) ; 

	    return step;
	}

	public void print() { // print algorithm parameters
		double eps = this.eps;
		double start = this.x0;
		double max = this.maxIter;		
		System.out.println();
		System.out.println("Tolerance (epsilon): " + eps); // print statements for algorithm parameters
		System.out.println("Maximum iterations: " + max);
		System.out.println("Starting point (x0): " + start);
		
	}
	

	public void printStats() { // print statistical summary of results
		System.out.println("---------------------------------------------------\n          norm(grad)       # iter    Comp time (ms)\n---------------------------------------------------"); // printing title for statistical summary																					
		System.out.format("%-7s%13.3f%13.3f%18.3f\n", "Average", Stats.Average(this.bestGradNorm), Stats.Average(this.nIter), Stats.Average(this.compTime)); // formatting for averages
		Object[] stDev = new Object[4]; // new array for standard deviation results
		stDev[0] = "St Dev";
		stDev[1] = Stats.StDev(this.bestGradNorm);
		stDev[2] = Stats.StDev(this.nIter);
		stDev[3] = Stats.StDev(this.compTime);
		System.out.format("%-7s%13.3f%13.3f%18.3f\n", stDev); // formatting of standard deviation results

		Object[] min = new Object[4]; // new array for minimum results
		min[0] = "Min";
		min[1] = Stats.Min(this.bestGradNorm);
		min[2] = Stats.Min(this.nIter);
		min[3] = Stats.Min(this.compTime);
		System.out.format("%-7s%13.3f%13d%18d\n", min); // formatting minimum array

		Object[] max = new Object[4];// new array for maximum results
		max[0] = "Max";
		max[1] = Stats.Max(this.bestGradNorm);
		max[2] = Stats.Max(this.nIter);
		max[3] = Stats.Max(this.compTime);
		System.out.format("%-7s%13.3f%13d%18d\n", max); // formatting max array
		System.out.println();

	}

	public void printAll() {// print final results for all polynomials
		boolean row = true;
		boolean notRow = false;
		this.printSingleResult(0, row);
		for (int i = 1; i < this.bestObjVal.length; ++i) { // loops through length of objective values to print each final result for one polynomial															 
			this.printSingleResult(i, notRow);
		}
	}

	public void printSingleResult(int i, boolean rowOnly) {// print final result for one polynomial , column header optional															
		int y;

		if (rowOnly) {
			System.out.println("-------------------------------------------------------------------------");
			
			System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point");

			System.out.println("-------------------------------------------------------------------------");
		}

		System.out.format("%8d%13.6f%13.6f%9d%17d   %4.4f", i + 1, this.bestObjVal[i], this.bestGradNorm[i], this.nIter[i], this.compTime[i],(this.bestPoint.get(i))[0]); // formatting of each result
		for (y = 1; y < (this.bestPoint.get(i)).length; ++y) {
			System.out.format(", %4.4f", (this.bestPoint.get(i))[y]); // formatting best point which is the y'th index of the i'th element from best point																																										
		}
		System.out.println();
	}
	
	
	

}
