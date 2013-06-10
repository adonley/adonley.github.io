/*
 * Interview Question at insideVault
 */

import java.lang.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CopyPasteProblem {
	
	public class KeyStroke {
		
		int numberOfA;
		int buffer; // Buffer value at a particular Keystroke
		int indexOfParent;  // To we can view the optimal solution
		
		public KeyStroke(int tempA, int tempBuff) {
			numberOfA = tempA;
			buffer = tempBuff;
			indexOfParent = 0;
		}
		
		public KeyStroke(int tempA, int tempBuff, int tempParent) {
			numberOfA = tempA;
			buffer = tempBuff;
			indexOfParent = tempParent;
		}	
	}
	
	ArrayList <KeyStroke>strokes;
	
	public CopyPasteProblem() {
		// Initialize the Strokes (ArrayList to be able to access
		//   an index very quickly)
		strokes = new ArrayList<KeyStroke>();
	}
	
	public void printStroke(KeyStroke key) {
		System.out.printf("A's = %8d, Buffer = %8d, Parent = %8d\n", key.numberOfA, key.buffer, key.indexOfParent);
	}
	
	public void printAllStrokes() {
		for(int i = 0; i < strokes.size(); i++) {
			System.out.print(i + ": ");
			printStroke(strokes.get(i));
		}
	}
	
	public void printResults() {
		System.out.printf("\nThe min number of keystrokes was %d and the final value of those keystrokes was %d. \n\n", strokes.size()-1, strokes.get(strokes.size() -1 ).numberOfA);
	}
	
	public void calculateOptomization(int numberToOptimize) {
		
		KeyStroke tempStroke = new KeyStroke(0,0);
		strokes.add(tempStroke);
		
		// strokes.get is constant because its an arraylist
		for(int i = 1; strokes.get(i-1).numberOfA <= numberToOptimize; i++ ) {
			
			// This has to do with the recurance. Nothing in the buffer until there is a select-all, copy, paste
			if(i - 6 >= 0) {
				// Have to find max at this stage. I'm keeping the values in terms of their object - list access form
				//   to accententuate the dynamic side of what's going on. Still constant access time because of ArrayList.
				
				// Case :If doing select all , copy, paste yields the max value for this many keystrokes
				if(strokes.get(i-6).numberOfA * 2 >= strokes.get(i-1).numberOfA + 1
						&& strokes.get(i-6).numberOfA * 2 >= strokes.get(i-2).numberOfA + strokes.get(i-2).buffer) {
					
					tempStroke = new KeyStroke(strokes.get(i-6).numberOfA * 2, strokes.get(i-6).numberOfA ,i-6);
					strokes.add(tempStroke);
					
				} 
				// Case: If doing paste from the buffer yields the max value for this number of keystrokes 
				else if(strokes.get(i-2).numberOfA + strokes.get(i-2).buffer >= strokes.get(i-6).numberOfA * 2
						&& strokes.get(i-2).numberOfA + strokes.get(i-2).buffer >= strokes.get(i-1).numberOfA + 1) {
					
					tempStroke = new KeyStroke(strokes.get(i-2).numberOfA + strokes.get(i-2).buffer ,strokes.get(i-2).buffer ,i-2);
					strokes.add(tempStroke);
					
				}
				// Case: If pressing A to add one a yields the max number of 'a's for this number of keystrokes
				else {
					
					tempStroke = new KeyStroke(strokes.get(i-1).numberOfA + 1,strokes.get(i-1).buffer ,i-1);
					strokes.add(tempStroke);
					
				}
			}
			else { // There is nothing in the clip board until you select-all, copy, paste 
				tempStroke = new KeyStroke(strokes.get(i-1).numberOfA + 1,strokes.get(i-1).buffer ,i-1);
				strokes.add(tempStroke);
			}
		}
		
	}
	
	public void otherResults() {
		boolean quit = false;
		int val;
		
		do {
			System.out.println("-----------------------------------------");
			System.out.println("Press 1 to see the results path");
			System.out.println("Press 2 to print maxes at every keystroke");
			System.out.println("Press anything else to quit");
			System.out.println("-----------------------------------------");
			val = getInput();
			System.out.print("\n");
			switch(val) {
				case 1:
					printResultPath();
					break;
				case 2:
					printAllStrokes();
					break;
				default:
					quit = true;
					break;
			}
			
		} while (!quit);
	}
	
	public int getInput() {
		
		int val;
		Scanner in = new Scanner(System.in);
		val = in.nextInt();
		return val;
		
	}
	
	public void printResultPath() {
		
		boolean finished = false;
		int index = strokes.size()-1, distance;
		ArrayList <KeyStroke>path = new ArrayList<KeyStroke>();
		ArrayList <String>humanReadablePath = new ArrayList<String>();
		
		while(!finished) {
			path.add(strokes.get(index));
			distance = index - strokes.get(index).indexOfParent;
			
			if(distance > 2) {
				humanReadablePath.add(new String("Select-All, Copy, Paste"));
			}
			else if (distance == 2) {
				humanReadablePath.add(new String("Paste                  "));
			}
			else {
				humanReadablePath.add(new String("Press a                "));
			}
			index = strokes.get(index).indexOfParent;
			if(index == 0) {
				path.add(strokes.get(index));
				finished = true;
			}
		}
		
		for(int i = humanReadablePath.size()-1; i >= 0; i--) {
			System.out.printf("%s %10d 'a's\n", humanReadablePath.get(i),path.get(i).numberOfA);
		}
		
		// This was used to print out all info about the path.
		// Not useful information.
		/* for(int i = path.size()-1; i >= 0; i--) {
			printStroke(path.get(i));
		} */
		
		System.out.print("\n");
	}
	
	public static void main(String [] args) {
		
		CopyPasteProblem cpy = new CopyPasteProblem();
		
		System.out.println("\nHow many 'a's would you like to produce with the minimum amount of keystrokes?");
		
		int numberToOptimize = cpy.getInput();
		
		cpy.calculateOptomization(numberToOptimize);
		
		cpy.printResults();
		
		cpy.otherResults();
			
		return;
	}
		
}