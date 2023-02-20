package edu.iastate.cs228.hw1;

/**
 *  
 * @author Nathan Turnis
 */

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.stream.BaseStream;

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Plain(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);

		//determine the width of the plain
		Scanner widthScanner = new Scanner(file);
		int w = 0;
		while (widthScanner.hasNextLine()) {
			w++;
			widthScanner.nextLine();
		}
		width = w;
		widthScanner.close();
		grid = new Living[width][width];

		//fill grid with each Living object based on file input
		Scanner plainScanner = new Scanner(file);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				String s = plainScanner.next();
				int a;
				switch (s.charAt(0)) {
					case 'B':
						a = Character.getNumericValue(s.charAt(1));
						grid[i][j] = new Badger(this, i, j, a);
						break;
					case 'F':
						a = Character.getNumericValue(s.charAt(1));
						grid[i][j] = new Fox(this, i, j, a);
						break;
					case 'R':
						a = Character.getNumericValue(s.charAt(1));
						grid[i][j] = new Rabbit(this, i, j, a);
						break;
					case 'G':
						grid[i][j] = new Grass(this, i, j);
						break;
					case 'E':
						grid[i][j] = new Empty(this, i, j);
						break;
				}

			}
		}
		plainScanner.close();
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param w  the grid
	 */
	public Plain(int w)
	{
		grid = new Living[w][w];
		width = w;
	}
	
	
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random();
		for(int i =0; i < width; i++){
			for(int j = 0; j < width; j++){
				int randomLiving = generator.nextInt(5); //0: Badger, 1: Empty, 2: Fox, 3: Grass, 4: Rabbit
				switch(randomLiving){
					case 0:
						grid[i][j] = new Badger(this, i, j, 0);
						break;
					case 1:
						grid[i][j] = new Empty(this, i, j);
						break;
					case 2:
						grid[i][j] = new Fox(this, i ,j, 0);
						break;
					case 3:
						grid[i][j] = new Grass(this, i, j);
						break;
					case 4:
						grid[i][j] = new Rabbit(this, i ,j, 0);
						break;
				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		String finalString = "";

		for(int i = 0; i < width; i++){
			for(int j = 0; j < width; j++){
				switch (grid[i][j].who()){
					case BADGER:
						finalString += "B" + ((Badger) grid[i][j]).myAge() + " ";
						break;
					case FOX:
						finalString += "F" + ((Fox) grid[i][j]).myAge() + " ";
						break;
					case RABBIT:
						finalString += "R" + ((Rabbit) grid[i][j]).myAge() + " ";
						break;
					case GRASS:
						finalString += "G  ";
						break;
					case EMPTY:
						finalString += "E  ";
						break;

				}
			}
			finalString += "\n";
		}

		return finalString;
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		File newFile = new File(outputFileName);
		PrintWriter writer = new PrintWriter((outputFileName));
		writer.print(this.toString());
		writer.close();
	}			
}
