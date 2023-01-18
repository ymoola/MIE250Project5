import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Pro5_moolayus {
	private static BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	private static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException  {
		System.out.println();
		System.out.print("Enter file name (0 to cancel): "); // prompting user to enter file name
		String fileName = BufferedReader.readLine();

        if ((fileName.equals("0"))) {
            System.out.println("\nFile loading process canceled.\n");
            return false;
        }
        //start of try catch statement that only try's this part of the code if the file exists
        try {
        
            int validPols = 1;
            int totalPols = 1;
            List<List<String>> coEfficients = new ArrayList<>();
            // buffered reader to read the file
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            String line = buffer.readLine();
            Polynomial poly;
            boolean degreeConsistent;
            int degree;
            boolean degreeCheck;
            while(true){
                poly = new Polynomial(); //new polynomial object
                degreeConsistent = true;
                degreeCheck = true;
                while(true) {

                    if(degreeConsistent) { 
                        List<String> coEffsList = new ArrayList<>();
                        String temp = ""; //empty string to store coefficients
                        for(int i = 0 ; i<line.length();i++){
                            if(line.charAt(i)!=',') { //splits line by commas
                                temp = temp + line.charAt(i);
                            }else{
                                coEffsList.add(temp); //adds coefs to List
                                temp = "";
                            }
                        }
                        for(int i = 0 ; i<line.length();i++){
                            if(line.charAt(i)==',') {
                                temp = ""; //if there is a comma, the string for coefficients remain empty
                            }else if (line.charAt(i)!='\n'){ 
                                temp = temp + line.charAt(i); //temp is only updated as long as there is no new line
                            }
                        }
                        coEffsList.add(temp); //adds coefficients to the List of coefficients
                        int size;
                        size = coEffsList.size();
                        degree = size - 1; //degree is equal to the size of the List -1 to account for the constant 
                        boolean valid;
                        valid = poly.getDegree() == degree; //Condition for the polynomial to be valid and counted
                        if (degreeCheck) {
                            poly.setDegree(degree); //if degrees are coherent, the degree is set
                            degreeCheck = false;
                        } else if (!valid) { //if dimensions are not consistent
                            int error = (totalPols);
                            System.out.println("\nERROR: Inconsistent dimensions in polynomial " + error + "!");
                            degreeConsistent = false;
                        }
                        if (degreeConsistent) {
                            poly.setVar(poly,coEfficients , coEffsList);
                        }

                    }
                    //reads the next line but breaks off loop if it's null or has a * at the first index.
                    line = buffer.readLine();
                    if(line == null){
                        break;
                    }else if(line.indexOf("*") == 0){
                        break;
                    }
                }
                if (degreeConsistent) {
                    poly.coefSetLoad(poly, coEfficients, P); //function in polynomial class that sets the coefficients
                    validPols++; //Only increases if degrees are consistent so doesn't count polynomials that are inconsistent in degree
                }
                totalPols++; //increases after each polynomial regardless of consistency so that we can use this variable to print at which polynomial there is inconsistent dimensions.
                line = buffer.readLine();
                //for the big loop to keep checking if the line is empty, so it can break out the loop
                if(line == null){
                    break;
                }
                coEfficients = new ArrayList<>(); //basically clears the List for the next polynomial
            }
            buffer.close(); //closing file
            System.out.println("\n" + (validPols - 1) + " polynomials loaded!\n"); // printing how many valid polynomials have been loaded
            return true;
            
        } catch (IOException ex) {
        	System.out.println("\nERROR: File not found!\n"); // output message if file does not exist from try catch
			return false;
        }
	}
	
	

	public static void displayMenu() {
		//prining all menu selection options
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
		System.out.print("\nEnter choice: ");
	}

	
	public static void printPolynomials(ArrayList<Polynomial> P) {
		boolean empty;
		empty = P.size() == 0;
		if (empty) { //checking if ArrayList is empty
			System.out.println("\nERROR: No polynomial functions are loaded!\n"); //if it is then prints this message
			return;
		}
		System.out.println();
		System.out.println("---------------------------------------------------------\nPoly No.  Degree   # vars   Function\n---------------------------------------------------------"); // polynomials title format
		double loopSize;
		loopSize = P.size();
		for (int x = 0; x < loopSize; x++) {
			System.out.format("%8d%8d%9d   ", x+1, P.get(x).getDegree(), P.get(x).getN()); // for each element in array list P, the respective values are formatted in order	
			P.get(x).print(); // print function from polynomial class to print the polynomial	
		}
		System.out.println();
	}
	
	public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {		
        System.out.println("\nSet parameters for SD with a fixed line search:");
        boolean fixed;
        fixed = SDF.getParamsUser();
        if (!fixed) { // if a user pressed 0, the function will return false and the following message is printed
          System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); 
        }
        System.out.println("Set parameters for SD with an Armijo line search:"); //otherwise, the user proceeds to input parameters
        boolean armijo;
        armijo = SDA.getParamsUser();
        if (!armijo) { // if a user pressed 0, the function will return false and the following message is printed
          System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); 
        }
        System.out.println("Set parameters for SD with a golden section line search:"); //otherwise, the user proceeds to input parameters
        boolean golden;
        golden = SDG.getParamsUser();
        if(!golden) { // if a user pressed 0, the function will return false and the following message is printed
          System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); 
        }
	}
	
	public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		//printing parameters
		System.out.println();
		System.out.print("SD with a fixed line search:");
        SDF.print();
        System.out.println();
        System.out.print("SD with an Armijo line search:");
        SDA.print();
        System.out.println();
        System.out.print("SD with a golden section line search:");
        SDG.print();
        System.out.println();
	}
	
	private static void runAll(SDFixed SDF, SDArmijo SDA,SDGSS SDG,ArrayList<Polynomial> P) {
		//method to run all line searches. First I initialize each member array to correct size
		// Then a for loop to run through every array in the arraylist P
		SDF.init(P);
		SDA.init(P);
		SDG.init(P);
		System.out.println("\nRunning SD with a fixed line search:");
	    int r;
	    for (r = 0; r < P.size(); r++)
	      SDF.run(r, P.get(r)); 
	    System.out.println("\nRunning SD with an Armijo line search:");
	    for (r = 0; r < P.size(); r++)
	      SDA.run(r, P.get(r)); 
	    System.out.println("\nRunning SD with a golden section line search:");
	    for (r = 0; r < P.size(); r++)
	      SDG.run(r, P.get(r)); 
	    System.out.println("\nAll polynomials done.\n");
		
		
	}
	public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {
		
		
		if (!(SDF.hasResults()) || !(SDA.hasResults()) || !(SDG.hasResults())) {
			System.out.println("\nERROR: Results do not exist for all line searches!\n");
			return;			  
		}


		//method to print results. For each line search, there is detailed results and statistical summary that is printed.
		System.out.println("\nDetailed results for SD with a fixed line search:");
		SDF.printAll();
		System.out.println("\nStatistical summary for SD with a fixed line search:");
		SDF.printStats();
		System.out.println("\nDetailed results for SD with an Armijo line search:");
		SDA.printAll();
		System.out.println("\nStatistical summary for SD with an Armijo line search:");
		SDA.printStats();
		System.out.println("\nDetailed results for SD with a golden section line search:");
		SDG.printAll();
		System.out.println("\nStatistical summary for SD with a golden section line search:");
		SDG.printStats();

	}
	
	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		boolean Fresults = SDF.hasResults();
		boolean Aresults = SDA.hasResults();
		boolean GSSresults = SDG.hasResults();
		if (Fresults && Aresults && GSSresults) {
	          System.out.println();
	          System.out.println("---------------------------------------------------\n          norm(grad)       # iter    Comp time (ms)\n---------------------------------------------------");
	          
	          //printing stats for fixed line search
	          Object[] fixed = new Object[4];
	          fixed [0] = "Fixed";
	          fixed [1] = Stats.Average(SDF.getBestGradNorm());
	          fixed [2] = Stats.Average(SDF.getNIter());
	          fixed [3] = Stats.Average(SDF.getCompTime());
	          System.out.format("%-7s%13.3f%13.3f%18.3f\n", fixed);
	          
	        //printing stats for armijo line search
	          Object[] armijo = new Object[4];
	          armijo [0] = "Armijo";
	          armijo [1] = Stats.Average(SDA.getBestGradNorm());
	          armijo [2] = Stats.Average(SDA.getNIter());
	          armijo [3] = Stats.Average(SDA.getCompTime());
	          System.out.format("%-7s%13.3f%13.3f%18.3f\n", armijo);
	          
	        //printing stats for GSS line search
	          Object[] GSS = new Object[4];
	          GSS [0] = "GSS";
	          GSS [1] = Stats.Average(SDG.getBestGradNorm());
	          GSS [2] = Stats.Average(SDG.getNIter());
	          GSS [3] = Stats.Average(SDG.getCompTime());
	          System.out.format("%-7s%13.3f%13.3f%18.3f\n", GSS);
	         
	          System.out.println("---------------------------------------------------");
	          
	          //getting averages for bestgradNorm
	          double avgFNorm = Stats.Average(SDF.getBestGradNorm());
	          double avgArmijoNorm = Stats.Average(SDA.getBestGradNorm());
	          double avgGSSNorm = Stats.Average(SDG.getBestGradNorm());
	          String printNorm = "Fixed"; 
	          
	          Double[] winnerNorms = new Double[3];
	          winnerNorms[0] = avgFNorm;
	          winnerNorms[1] = avgArmijoNorm;
	          winnerNorms[2] = avgGSSNorm;
	          
	          //checking if fixed is smaller than the other searches
	          
	          if((winnerNorms[0] < winnerNorms[1])) {
	        	  if (winnerNorms[0] < winnerNorms[2]) {
	        		  printNorm = "Fixed";
	        	  }
	          }
	          //checking if armijo is smaller than the other searches
	          
	          if((winnerNorms[1] < winnerNorms[0])) {
	        	  if (winnerNorms[1] < winnerNorms[2]) {
	        		  printNorm = "Armijo";
	        	  }
	          }
	          
	          //checking if GSS is smaller than the other searches
	          if((winnerNorms[2] < winnerNorms[0])) {
	        	  if (winnerNorms[2] < winnerNorms[1]) {
	        		  printNorm = "GSS";
	        	  }
	          }
	          
	          //getting averages for nIter
	          double avgFIter = Stats.Average(SDF.getNIter());
	          double avgArmijoIter = Stats.Average(SDA.getNIter());
	          double avgGSSIter = Stats.Average(SDG.getNIter());
	          String printIter = "Fixed";   
	          
	          
	          
	          Double[] winnerIter = new Double[3];
	          winnerIter[0] = avgFIter;
	          winnerIter[1] = avgArmijoIter;
	          winnerIter[2] = avgGSSIter;
	          
	          
	        //checking if fixed is smaller than the other searches
	          if((winnerIter[0] < winnerIter[1])) {
	        	  if (winnerIter[0] < winnerIter[2]) {
	        		  printIter = "Fixed";
	        	  }
	          }
	          
	        //checking if armijo is smaller than the other searches
	          if((winnerIter[1] < winnerIter[0])) {
	        	  if (winnerIter[1] < winnerIter[2]) {
	        		  printIter = "Armijo";
	        	  }
	          }
	          
	        //checking if GSS is smaller than the other searches
	          if((winnerIter[2] < winnerIter[0])) {
	        	  if (winnerIter[2] < winnerIter[1]) {
	        		  printIter = "GSS";
	        	  }
	          }
	             
	        //getting averages for CompTime
	          double avgFComp = Stats.Average(SDF.getCompTime());
	          double avgArmijoComp = Stats.Average(SDA.getCompTime());
	          double avgGSSComp = Stats.Average(SDG.getCompTime());	          
	          String printComp = "Fixed";
	          
	          Double[] winnerComp = new Double[3];
	          winnerComp[0] = avgFComp;
	          winnerComp[1] = avgArmijoComp;
	          winnerComp[2] = avgGSSComp;
	          
	          
	        //checking if fixed is smaller than the other searches
	          if((winnerComp[0] < winnerComp[1])) {
	        	  if (winnerComp[0] < winnerComp[2]) {
	        		  printComp = "Fixed";
	        	  }
	          }
	          
	          //checking if armijo is smaller than the other searches
	          if((winnerComp[1] < winnerComp[0])) {
	        	  if (winnerComp[1] < winnerComp[2]) {
	        		  printComp = "Armijo";
	        	  }
	          }
	          
	          //checking if GSS is smaller than the other searches
	          if((winnerComp[2] < winnerComp[0])) {
	        	  if (winnerComp[2] < winnerComp[1]) {
	        		  printComp = "GSS";
	        	  }
	          }
	        
	          
	          
	          String finalW = "hi, just initializing here!";
	          
	          //if the winner of gradNorm equals the winner of iter and comp, it becomes the overall winner
	          if (printNorm.equals(printIter)){
	        		 if (printIter.equals(printComp)) {
	        	  		finalW = printNorm; 
	          		}
	        		 else {
	        			 finalW = "Unclear";
	        		 }
	          }
	          
	          //otherwise, it becomes unclear who the overall winner is
	          else {
	        	  finalW = "Unclear";
	          }
	          Object[] winner = new Object[4];
	          winner[0] = "Winner";
	          winner[1] = printNorm;
	          winner[2] = printIter;
	          winner[3] = printComp;
	          System.out.format("%-7s%13s%13s%18s\n", winner);
	          System.out.println("---------------------------------------------------");
	          System.out.println("Overall winner: " + finalW);
	          System.out.println();
	    } 
		
		//if any of the searches yield false for hasResults, this message is printed
		else {
			System.out.println("\nERROR: Results do not exist for all line searches!\n");
		}	
		
	}

	
	
	public static int getInteger(String prompt, int LB, int UB) {
		int userval = 0;

		boolean check;
		do {		
			
			check = true;
			System.out.print(prompt); // sets check to true and prints the prompt
			try {	
				userval = Integer.parseInt(BufferedReader.readLine());
				if (userval >= LB && userval <= UB) { //checking if user input is within bounds, breaks loop if it is and function returns that value
					break;					
				}
				
				else {
					check = false; //sets check to false to determine what errors there are based on UB and 0
				}
				
			} catch (IOException | NumberFormatException e) {
				check = false;
			} // try-catch statements for input format errors
			
			
			if (!check) {
				if (UB == Integer.MAX_VALUE) { // if UB is max value of an integer, then this is printed.
					System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", 0);
				} else {
					System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", 0, UB); // Otherwise, the UB is replaced with the argument.																																																	
				}
			}
		} while (!check); // loops while check is false
		return userval;
	}
	
	
	public static double getDouble(String prompt, double LB, double UB) {
		double userval = 0.0;

		boolean check; // declares variable check with data type boolean
		do {
			check = true; // sets check to true and prints the prompt
		
			System.out.print(prompt);
			try {	
				userval = Double.parseDouble(BufferedReader.readLine()); // user input is stored as a double in userval variable
				if (userval >= LB && userval <= UB) { //checking if user input is within bounds, breaks loop if it is and function returns that value
					break;
					
				}
				else {
					check = false; //sets check to false to determine what errors there are based on LB and UB
				}
			} catch (IOException | NumberFormatException e) {
				check = false; // try catch statements for exceptions
			}


			if (!check) { // if check is false, the following will be output
				if (LB == Double.MAX_VALUE && UB != Double.MAX_VALUE) {
					System.out.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n\n", UB); 
											 
				} 
				else if (UB == Double.MAX_VALUE && LB == Double.NEGATIVE_INFINITY) {
					System.out.format("ERROR: Input must be a real number in [-infinity, infinity]!\n\n");
					
				}
				else if (UB == Double.MAX_VALUE && LB!=-Double.MAX_VALUE) {
					System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB);
				}
				
				
				else {
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);   
																											
				}
			}
		} while (!check); // loops while check is false

		return userval; // returns user input
	}
	
	
	public static void main(String[] args) throws IOException {
		ArrayList<Polynomial> arrayListPolynomials = new ArrayList<Polynomial>(); // new array list
		
		//objects for each new linesearch class
		SDGSS gss = new SDGSS();
		SDArmijo armijo = new SDArmijo();		
		SDFixed fix = new SDFixed();		
		SteepestDescent SD = new SteepestDescent(); 
		//String input for storage of user input
		String input;
		do {
			displayMenu();
			input = BufferedReader.readLine(); // gets user input and stores in input variable
			switch (input) {
			case "l":
			case "L":
				 loadPolynomialFile(arrayListPolynomials); // calls load polynomial on arrayListPolynomials
				 SD.setHasResults(false);
				 gss.setHasResults(false);
				 armijo.setHasResults(false);
				 fix.setHasResults(false);			
				break;
			case "f":
			case "F":				
				printPolynomials(arrayListPolynomials);																																																			
				break;
			case "x":
			case "X":
				compare(fix, armijo, gss);				
				break;
			case "r":
			case "R":
				boolean none = arrayListPolynomials.size() == 0;
				if (none) {
					System.out.println("\nERROR: No polynomial functions are loaded!\n"); // if no polynomials are loaded, this is printed
																							 
				}else {
			          runAll(fix, armijo, gss, arrayListPolynomials);
		        }
				break;
			case "s":
			case "S":
				getAllParams(fix, armijo, gss);
		        break;
			case "p":
			case "P":
				printAllParams(fix,armijo,gss);
		        break;
			case "c":
			case "C":
				arrayListPolynomials.clear(); // clears list of polynomials
				System.out.println("\nAll polynomials cleared.\n"); // output message
				SD.setHasResults(false);
				gss.setHasResults(false);
				armijo.setHasResults(false);
				fix.setHasResults(false);
				break;
			case "d":
			case "D":
					printAllResults(fix, armijo, gss, arrayListPolynomials);			          

				break;
			case "q":
			case "Q":
				break;
			default:
				System.out.println("\nERROR: Invalid menu choice!\n");
				break;
				
			
			}
			
		} while (!input.equals("q") && !input.equals("Q"));
		System.out.println("\nArrivederci.");

	}


	
		
		 
		
	


}
