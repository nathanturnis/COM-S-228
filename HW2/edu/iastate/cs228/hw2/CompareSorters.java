package edu.iastate.cs228.hw2;

/**
 *  
 * @author Nathan Turnis
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{

	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{
		PointScanner[] scanners = new PointScanner[4];
		Point[] points;
		int trialCount = 1;
		int selection = 1; //1: random integers; 2: file input; 3 or any other number: exit
		int numRandPoints = 1;


		System.out.println("Performances of Four Sorting Algorithms in Point Scanning\n");
		System.out.println("keys: 1 (random integers)  2 (file input)  3 (exit)");

		Scanner scanner = new Scanner(System.in);
		while(selection == 1 || selection == 2){
			System.out.print("Trial " + trialCount + ": ");
			selection = scanner.nextInt();

			if(selection == 1){
				System.out.print("Enter number of random points: ");
				numRandPoints = scanner.nextInt();
				points = new Point[numRandPoints];
				Random random = new Random();
				points = generateRandomPoints(numRandPoints, random);

				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);

				for(int i = 0; i < scanners.length; i++){
					scanners[i].scan();
					scanners[i].writeMCPToFile();
				}
			} else if(selection == 2){

				System.out.println("Points from a file");
				System.out.print("File name: ");
				String fileName = scanner.next();

				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);

				for(int i = 0; i < scanners.length; i++){
					scanners[i].scan();
					scanners[i].writeMCPToFile();
				}
			} else {
				scanner.close();
				break;
			}

			System.out.println("\nalgorithm      size   time(ns)\n-------------------------------");
			for(int i = 0; i < scanners.length; i++){
				System.out.println(scanners[i].stats());
			}
			System.out.println("-------------------------------\n");
			trialCount++;
		}
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if(numPts < 1){
			throw new IllegalArgumentException("Must enter 1 or more points!");
		}

		Point[] pts = new Point[numPts];
		for(int i = 0; i < numPts; i++){
			pts[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}

		return pts;
	}
	
}
