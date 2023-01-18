
public class SDArmijo extends SteepestDescent {
	
	SteepestDescent SD = new SteepestDescent();
	
	private double maxStep = 1.0D; // Armijo max step size
	private double beta = 1.0E-4D; // Armijo beta parameter
	private double tau = 0.5D; // Armijo tau parameter
	private int K = 10; // Armijo max no. of iterations
	
	// constructors
	public SDArmijo () {
		
	}
	public SDArmijo(double maxStep, double beta, double tau, int K) {
		this.maxStep = maxStep;
		this.beta = beta;
		this.tau = tau;
		this.K = K;
	}
	
	//getters
	public double getMaxStep() {
		return this.maxStep;
	}
	
	public double getBeta() {
		return this.beta;
	}
	public double getTau() {
		return this.tau;
	}
	public int getK() {
		return this.K;
	}
	
	//setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}
	
	public void setBeta(double a) {
		this.beta = a;
	}
	
	public void setTau(double a) {
		this.tau = a;
	}
	
	public void setK(int a) {
		this.K = a;
	}
	
	//other methods
	
	@Override 
	public double lineSearch(Polynomial P, double[] x) {	
		double beta;
		beta = this.beta;
		double func = P.f(x);
		double maxIter = this.K;		
		double step = this.maxStep;
		double lhsStore[] = new double[x.length];
		double[] dir = direction(P, x);
		double normSq = Math.pow(P.gradientNorm(x), 2);		
		double tauParam;
		tauParam = this.tau;
		
		
		//For loop to store LHS condition in an array
		
		for (int i = 0; i < x.length; i++) {
			lhsStore[i] = x[i] + step * dir[i];
		}
		double lhsCond = P.f(lhsStore);
		
		int iter = 0;
		boolean iterCond;
		iterCond = (iter < maxIter);
		//iterating until LHS of condition is equal to RHS of the condition or if the iterations has reached K
		while(P.f(lhsStore) > (func - step * beta * normSq) && (iterCond)) {
			
			//for every loop, alpha is multiplied by a user specified tau value until Armijo Decrease condition is satisfied
			step *= tauParam;
			
			//adjusting LHS condition after tau multiplication
			for (int i = 0; i < x.length; i++) {
				lhsStore[i] = x[i] + step * dir[i];
			}

			iter++;
			
		}
		
		//if the iterations reach K, return an arbitrary value so that in run function, the line search if statement can break the loop algorithm
		if((lhsCond > func - step * beta * normSq ) && !(iter < maxIter)){
			step = -10.0D;
			System.out.println("   Armijo line search did not converge!");
		}

		return step;
		
		
	}
	@Override
	public boolean getParamsUser() {
		//function to get user input for step size, beta, tau and K, returns false if user inputs 0.
		double maxStep;
	    maxStep = Pro5_moolayus.getDouble("Enter Armijo max step size (0 to cancel): ", 0.0, Double.MAX_VALUE);
	    if (maxStep == 0.0)
	         return false;
	     
	    double beta;
	    beta = Pro5_moolayus.getDouble("Enter Armijo beta (0 to cancel): ", 0.0, 1.0);
	    if (beta == 0.0)
	            return false;
	        
	    double tau;
	    tau = Pro5_moolayus.getDouble("Enter Armijo tau (0 to cancel): ", 0.0, 1.0);
	    if (tau == 0.0)
	               return false;
	        
	    int iter;
	    iter = Pro5_moolayus.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE);
	    if (iter == 0.0)
	    	return false;

	    if(super.getParamsUser()) { //before setting parameters specific to SDArmijo, check to see if any of the common parameters from steepest descent class return false
		    this.maxStep = maxStep;
		    this.beta = beta;
		    this.tau = tau;
		    this.K = iter;
	    }
	    return true;	
	}
	
	@Override
	public void print() { 
		super.print();
		double max;
		max = this.maxStep;
		double beta;
		beta = this.beta;
		double tau;
		tau = this.tau;
		double k;
		k = this.K;
		//printing parameters
	    System.out.println("Armijo maximum step size: " + max);
	    System.out.println("Armijo beta: " + beta);
	    System.out.println("Armijo tau: " + tau);
	    System.out.println("Armijo maximum iterations: " + k);
	}
	

}
