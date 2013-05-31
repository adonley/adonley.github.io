import java.util.LinkedList;
import java.lang.Integer;
import java.io.*;

public class IntToList {

  private LinkedList<Integer> converted;
  private Integer toBeConverted;


  public static void main(String [] argv) {

	// Create a IntToList Object to use during conversion
	IntToList obj;
	  
    // Create a buffered reader to read the integer
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // To be used to get the input of a file
    String input;
    System.out.printf("%s: ","Please enter an integer to convert");
    
    try {
      // Read in the user input
      input = in.readLine();
      
      // Convert the string to an int using library functions
      obj = new IntToList(Integer.valueOf(input));

      obj.convertToList();
      obj.printList();
      
    } catch (NumberFormatException N) {  // Catch a non-integer input or too large input
  	  System.out.printf("%s\n","Please enter an integer.");
    } catch (Exception E) { // Everything else
      E.printStackTrace();
    }
    
    return;
    
  }

  public IntToList(Integer input) { 
	  // Initializations
	  toBeConverted = input.intValue();
	  converted = new LinkedList<Integer>();
  }
  
  public void convertToList() {

    int temp = toBeConverted.intValue(), remainder;   
    
    // Once the integer is equal to zero, there are no more single digits to extract
    while(temp > 0) {
    	
      // This remainder will be committed to the list of integers
      remainder = temp % 10;
      
      // This will add the integer to the front of the list
      converted.addFirst(new Integer(remainder));
      
      // This will remove a power of 10 from the read in integer, casted to not be a double
      temp = (int)Math.floor(temp / 10);
    }

  }
  
  public void printList() {
	
    System.out.print("Converted Integer to List: ");
	
    // Print out all the single digits in the list
    for (Integer i : converted) {
    	System.out.print(i);
    }
    
    System.out.print("\n");
    
  }

}
