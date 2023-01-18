import java.util.ArrayList;
import java.util.List;

public class Polynomial {
	private int n; // no. of variables
	private int degree; // degree of polynomial
	private double[][] coefs = new double[1][1]; // coefficients

	// constructors
	public Polynomial() { // void constructor
		this.n = 0;
		this.degree = 0;
		this.coefs[0][0] = 0.0;
	}

	public Polynomial(int n, int degree, double[][] coefs) {
		this.n = n;
		this.degree = degree;
		this.coefs = coefs;
	}

	// getters
	public int getN() {
		return n;
	}

	public int getDegree() {
		return degree;
	}

	public double[][] getCoefs() {
		return coefs;
	}

	// setters
	public void setN(int a) {
		this.n = a;
	}

	public void setDegree(int a) {
		this.degree = a;
	}

	public void setCoef(int j, int d, double a) {
		this.coefs[j][d] = a;
	}

	// other methods
	public void init() { // initialize member arrays to correct size
		this.coefs = new double[this.n][this.degree + 1];

	}

	public double f(double[] x) // calculate function value at point x
	{
		int i = 0;
		int degree =this.degree;
		double res = 0;	
		while(i< getN()) { // while loop to loop through each variable that user has inputed		
			for(int d = 0; d < this.coefs[i].length; d++) { // for loop to loop through each degree the user has inputed
				res += coefs[i][d]*Math.pow(x[i],(degree - d)); //coeff * x * respective degree based on the index of the loop				
			}			
			i++;
		}
		return res ;
		
		
	}

	public double[] gradient(double[] x) // calculate gradient at point x
	{
		int i = 0;
		double xpower;
		double[] res;
		res = new double[getN()]; // create array to store gradients with dimension of number of variables;
		
		
		while (i < getN()) { // while loop to loop through each variable that user has inputed		
			for (int j = 0; j < getDegree(); j++) { // for loop to loop through each degree the user has inputed
				int deriv = getDegree() - j;
				xpower = Math.pow(x[i], (deriv - 1));
				res[i] = res[i] + (getCoefs()[i][j] * deriv *  xpower); // formula to find derivative of the function which is the gradient																						
			}
			i+=1;
		}

		return res;
	}

	public double gradientNorm(double[] x) // calculate norm of gradient at point x
	{
		double norm;
		double[] grad;
		double res = 0;
		grad = gradient(x); // finds gradient of argument
		int i=0;

		while (i < x.length) { // loops through each index of the length of the argument
			double gradsq;
			gradsq = Math.pow(grad[i], 2);
			res += gradsq; // squares all the gradients added together
			i+=1;
		}
		norm = Math.sqrt(res); // finally finds the norm by square rooting res
		

		return norm; 
	}

	public boolean isSet() {
		return (getN() != 0 && getDegree() != 0);
	}
	
	public boolean notSet() {
		return(getDegree() == 0);
	}
	

	public void setVar(Polynomial poly, List<List<String>> coEfficients, List<String> coEffsList) {
		coEfficients.add(coEffsList);
		poly.setN(coEfficients.size());
		
		
	}

	
	public void coefSetLoad(Polynomial P, List<List<String>> coEfficients, ArrayList<Polynomial> Pol) {
		P.init(); //init member arrays to correct size
		int i = 0;
		while( i < coEfficients.size()) { 
			for (int j = 0; j < coEfficients.get(i).size(); j++) {
				double val = Double.parseDouble(coEfficients.get(i).get(j));
				P.setCoef(i, j, val);
			}
			i=i+1;  
		}
		Pol.add(P);
		
	}
	

	public void print() // print out the polynomial
	{
		System.out.print("f(x) = "); 
		
        for(int i=1; i<=getN(); i++) { //for loop to loop through each variable that user has inputed
            System.out.print("( ");
            
            for(int j=1, k=getDegree(); j<=(getDegree()+1); j++,k--){  //for loop to go through each degree of the polynomial.
                if(k==0){
                    System.out.printf("%.2f",coefs[i-1][j-1]); //when k==0, there is just a constant left, so that is all that's printed
                }
                else {
                    System.out.print(String.format("%.2f",coefs[i-1][j-1]) + "x" + i + "^" + k + " + "); //else, it extracts the coefficient from array and i and k is representing which variable the polynomial belongs to and the power respectively.
                }
                
            }
            
            if(i == getN()) {
                System.out.print(" )\n"); //if the loop has reached the number of variables, the print statement will end with brackets.
            }
            else{
                System.out.print(" ) + "); //else, it closes the brackets for that polynomial and moves to the next variable in the for loop
            }
        }
     
        
	}


}
