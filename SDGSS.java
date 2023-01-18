
public class SDGSS extends SteepestDescent{
	
	SteepestDescent SD = new SteepestDescent();
	
	private final double _PHI_= (1. + Math.sqrt(5))/2.;
	private double maxStep = 1.0; // Armijo max step size  
	private double minStep = 0.001; // Armijo beta parameter
	private double delta = 0.001; // Armijo delta parameter
	
	// constructors
	public SDGSS () {
		
	}
	public SDGSS(double maxStep , double minStep , double delta) {
		this.maxStep = maxStep;
		this.minStep = minStep;
		this.delta = delta;
	}
	
	// getters
	public double getMaxStep() {
		return this.maxStep;
	}
	public double getMinStep() {
		return this.minStep;
	}
	public double getDelta () {
		return this.delta;
	}
	
	  
	 
	// setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}
	public void setMinStep(double a) {
		this.minStep = a;
	}
	public void setDelta(double a) {
		this.delta = a;
	}
	
	//other methods 
	
	@Override
	public double lineSearch(Polynomial P, double [] x) {// step size from GSS		
		double goldenRatio;
		goldenRatio = this._PHI_; //golden ratio 
		double[] dir = direction(P, x); //calculating direction array
		double c;
	    c = this.minStep + (this.maxStep - this.minStep) / goldenRatio; //calculating c as instructed in document 
	    return GSS(this.minStep, this.maxStep, c, x, dir, P); //calls GSS to complete the line search 
	}
	
	@Override
	public boolean getParamsUser() { // get algorithm parameters from user
		double max;
	    max = Pro5_moolayus.getDouble("Enter GSS maximum step size (0 to cancel): ", 0.0, Double.MAX_VALUE);
	    if (max == 0.0) {
	    	return false; //returns false if user inputs 0
	    }
	    
	    double min;
	    min = Pro5_moolayus.getDouble("Enter GSS minimum step size (0 to cancel): ", 0.0, max);
	    if (min == 0.0) {
	    	return false; 
	    }
	    double delta;
	    delta = Pro5_moolayus.getDouble("Enter GSS delta (0 to cancel): ", 0.0, Double.MAX_VALUE);
	    if (delta == 0.0) {
	    	return false;
	    }
	       
	    if (super.getParamsUser()) { //before setting parameters specific to SDGSS, check to see if any of the common parameters from steepest descent class return false		
			this.delta = delta;
			this.minStep = min;
			this.maxStep = max;
			
		}
	    return true;
	}
	
	@Override
	public void print() {
		double max;
		double min;
		double delta;
		max = this.maxStep;
		delta = this.delta;
		min = this.minStep;		
		super.print();
	    System.out.println("GSS maximum step size: " + max);
	    System.out.println("GSS minimum step size: " + min);
	    System.out.println("GSS delta: " + delta);
		
	}
	private double GSS(double a, double b, double c, double [] x, double [] dir, Polynomial P) {
		do {
			  boolean condition;
			  condition = (b-a<this.delta);
		      double[] pointsA = new double[x.length];
		      double[] pointsB = new double[x.length];
		      double[] pointsC = new double[x.length];
		      
		      //Adjusting each array to get the minimum of f[(x^k +(alpha * direction))]
		      
		      for (int i = 0; i < x.length; i++) {
			      pointsA[i] = x[i] + a * dir[i];
			      pointsB[i] = x[i] + b * dir[i]; 
			      pointsC[i] = x[i] + c * dir[i]; 
		      }
		      double fxA = P.f(pointsA);
		      double fxB = P.f(pointsB);
		      double fxC = P.f(pointsC);
		      
		      //SPECIAL CONSIDERATIONS
		      
		      //if F(c) is bigger than function value at either end point: two cases.
		      if (fxC > fxA || fxC > fxB) { 
		    	  boolean rEnd;
		    	  boolean lEnd;
		    	  rEnd = (fxA >= fxB);
		    	  lEnd = (fxA < fxB);
		    	  
		    	  //case 1: return largest step size if fA is bigger than or equal to fB
		    	  if (rEnd) {
		    		  return b;	
		    	  }
		    	  //case 2: return smallest step size if fA is smaller than fB
		      	  else if(lEnd) { 
		    		  return a;
		      	  }
		      }
		      
		      //BASE CONDITION
		      if (condition) {
		    	  return (b+a)/2;
		      }
		      
		      
		      double[]Y;
		      Y = new double[x.length];
		       
		    	  double y;
		    	  double Wac = (Math.abs(c-a));
		    	  double Wcb = (Math.abs(b-c));
		    	  double rightInterval;
		    	  double leftInterval;
		    	  leftInterval = (a + (Wac) / this._PHI_);
		    	  rightInterval = (b - (Wcb) / this._PHI_);
		    	  
		    	  if (Wcb > Wac)
		    		  y = rightInterval; //y value if right interval is bigger than left
		    	  
		    	  else {
		    		  y = leftInterval; //y value if left interval is bigger than right
		    	  }
		    	  
		    	  
		    	//Adjusting Y array to get the minimum of f[(x^k +(alpha * direction))]
		    	  for (int i = 0; i < x.length; i++)
		    		 Y[i] = x[i] + y * dir[i];
		    	  
		    	  
		    	 //getting function value of Y
		    	 double fY = P.f(Y);
		    	 
		    	 //checking if F(y) is smaller than F(c)
		    	 if (fY < fxC) {
		    		 
		    		 //checking if b-c is bigger than c-a so the code knows which y value to use based on which endpoint is bigger
		    		 if(Wcb > Wac) { 
		    			 return GSS(c,b,y,x,dir,P); //recurse through gss with new values
		    		 }
		    		 //if the other endpoint is bigger
		    		 else if (Wac > Wcb) {
		    			 return GSS(y, b, c, x, dir, P); //recurse through GSS with new values
		    		 }
		    		 
		    	 }
		    	 //if F(y) is bigger than F(c)
		    	 else {
		    		//checking if b-c is bigger than c-a so the code knows which y value to use based on which endpoint is bigger
		    		 if(Wcb > Wac) {
		    			 return GSS(a,y,c,x,dir,P);		 
		    		 }
		    		//if the other endpoint is bigger
		    		 else if(Wac > Wcb) {
		    			 return GSS(a, c, y, x, dir, P);
		    		 }
		    		 
		    	 }
		    	  	    	  
		    } while (true);
		
	
	
		
	}
	
	
	
		  
		  
		
	

	
	

}
