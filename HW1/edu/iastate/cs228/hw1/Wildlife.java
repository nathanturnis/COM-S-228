package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Nathan Turnis
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with
 * squares inhabited by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class Wildlife 
{
	/**
	 * Update the new plain from the old plain in one cycle. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew)
	{
		// For every life form (i.e., a Living object) in the grid pOld, generate  
		// a Living object in the grid pNew at the corresponding location such that 
		// the former life form changes into the latter life form. 
		// 
		// Employ the method next() of the Living class.

		int width = pOld.getWidth();
		for(int i = 0; i < width; i++){
			for(int j = 0; j < width; j++){
				pNew.grid[i][j] = pOld.grid[i][j].next(pNew);
			}
		}
	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		int trial = 1; //trial counter for keeping trick which trial we are on
		int plainSelection = 1; //1: generate random plain, 2: generate from file, 3 or any other number: exit program
		int plainWidth = 0; //width of plain
		int numCycles = 0; //how many cycles to run
		boolean oddCycle = false;  //used to determine which plain to print

		Plain even;   				 // the plain after an even number of cycles
		Plain odd;                   // the plain after an odd number of cycles

		System.out.println("Simulation of Wildlife of the Plain");
		System.out.println("keys: 1 (random plain)  2 (file input)  3 (exit) \n");

		Scanner scanner = new Scanner(System.in);
		while(plainSelection == 1 || plainSelection == 2) {
			plainWidth = 0;
			numCycles = 0;
			System.out.print("Trial " + trial + ": ");
			plainSelection = scanner.nextInt();
			if (plainSelection == 1) {
				System.out.println("Random plain");
					//continue asking user for grid width until width 1 or high is selected
					while(plainWidth < 1) {
						System.out.print("Enter grid width: ");
						plainWidth = scanner.nextInt();
					}
					//continue asking user for num of cycles until 1 or more cycles is chosen
					while(numCycles < 1){
						System.out.print("Enter the number of cycles: ");
						numCycles = scanner.nextInt();
					}

				even = new Plain(plainWidth);
				even.randomInit();
				odd = new Plain(even.getWidth());

			} else if (plainSelection == 2) {
				System.out.println("Plain input from a file");
				System.out.print("File name: ");
				String file = scanner.next();
					//continue asking user for num of cycles until 1 or more cycles is chosen
					while(numCycles < 1){
						System.out.print("Enter the number of cycles: ");
						numCycles = scanner.nextInt();
					}
				even = new Plain(file);
				odd = new Plain(even.getWidth());

			} else {
				scanner.close();
				break;
			}

			System.out.println("Initial plain:\n");
			System.out.println(even.toString());

			for(int i = 0; i < numCycles; i++){

				//if we are on an even cycle
				if(i % 2 == 0){
					updatePlain(even, odd);
					oddCycle = true;
				}

				//if we are on an odd cycle
				if(i % 2 != 0){
					updatePlain(odd, even);
					oddCycle = false;
				}
			}
			System.out.println("Final plain:\n");
			if(oddCycle){
				System.out.println(odd.toString());
			} else if(!oddCycle) {
				System.out.println(even.toString());
			}
			trial++;
		}
	}
}
