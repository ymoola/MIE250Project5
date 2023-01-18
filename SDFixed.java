
public class SDFixed extends SteepestDescent {
	
	SteepestDescent SD = new SteepestDescent();
	
	private double alpha = 0.01D;
	// constructors
	public SDFixed () {
		
	}
	public SDFixed(double alpha) {
		this.alpha = alpha;
		
	}
	
	//getters
	public double getAlpha() {
		return this.alpha;  
		
	}
	
	// setters
	public void setAlpha(double a) {
		this.alpha = a;
	}
	
	@Override
	public double lineSearch(Polynomial P, double [] x) { // fixed step size
		return this.alpha; //this line search method just returns step size like the previous projects 
		
	}
	
	@Override
	public boolean getParamsUser() { // get algorithm parameters from user
		double fixed;
	    fixed = Pro5_moolayus.getDouble("Enter fixed step size (0 to cancel): ", 0.0D, Double.MAX_VALUE);
	    if (fixed == 0.0) {
	    	return false;
	    } 
	    if(super.getParamsUser()) { //before setting parameters specific to SDFixed, check to see if any of the common parameters from steepest descent class return false
		    this.alpha = fixed;	  
		}
	    return true;
	} 
	
	
	
	@Override
	public void print() {
		super.print();
		double alpha;
		alpha = this.alpha;
		System.out.println("Fixed step size (alpha): " + alpha);
	}
	

	

}
