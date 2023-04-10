package edu.iastate.cs228.hw2;

/**
 * 
 * @author Nathan Turnis
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds.

	protected String outputFileName;
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts.length == 0 || pts == null){
			throw new IllegalArgumentException();
		}

		sortingAlgorithm = algo;
		switch(sortingAlgorithm){
			case SelectionSort:
				outputFileName = "selectionsort.txt";
				break;
			case InsertionSort:
				outputFileName = "insertionsort.txt";
				break;
			case MergeSort:
				outputFileName = "mergesort.txt";
				break;
			case QuickSort:
				outputFileName = "quicksort.txt";
				break;
		}

		points = new Point[pts.length];
		for(int i = 0; i < pts.length; i++){
			Point p = new Point(pts[i]);
			points[i] = p;
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
		switch(sortingAlgorithm){
			case SelectionSort:
				outputFileName = "selectionsort.txt";
				break;
			case InsertionSort:
				outputFileName = "insertionsort.txt";
				break;
			case MergeSort:
				outputFileName = "mergesort.txt";
				break;
			case QuickSort:
				outputFileName = "quicksort.txt";
				break;
		}

		File file = new File(inputFileName);
		Scanner scanCount = new Scanner(file);
		int numIntegers = 0;
		while(scanCount.hasNextInt()){
			scanCount.nextInt();
			numIntegers++;
		}

		if(numIntegers %2 != 0){
			scanCount.close();
			throw new InputMismatchException("File needs to have an even number of numbers!");
		}

		Scanner scanner = new Scanner(file);
		points = new Point[numIntegers / 2];
		int i = 0;
		while(scanner.hasNextInt()){
			points[i] = new Point(scanner.nextInt(), scanner.nextInt());
			i++;
		}
		scanner.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter = null;

		switch(sortingAlgorithm) {
			case SelectionSort:
				aSorter = new SelectionSorter(points);
				break;
			case InsertionSort:
				aSorter = new InsertionSorter(points);
				break;
			case MergeSort:
				aSorter = new MergeSorter(points);
				break;
			case QuickSort:
				aSorter = new QuickSorter(points);
				break;
		}

		//sort by x-coordinate
		long startXSort = System.nanoTime();
		aSorter.setComparator(0);
		aSorter.sort();
		int xMedian = aSorter.getMedian().getX();
		long endXSort = System.nanoTime() - startXSort;
		//sort by y-coordinate
		long startYSort = System.nanoTime();
		aSorter.setComparator(1);
		aSorter.sort();
		int yMedian = aSorter.getMedian().getY();
		long endYSort = System.nanoTime() - startYSort;

		medianCoordinatePoint = new Point(xMedian, yMedian);
		scanTime = endXSort + endYSort;
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		return String.format("%-13s", sortingAlgorithm) + "    " + points.length + "    " + scanTime;
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		//outputFileName not given as a parameter, so instead choose name depending on what sorting
		//object it is
		//Example: the SelectionSort will output as selectionsort.txt
		File newFile = new File(outputFileName);
		PrintWriter writer = new PrintWriter((newFile));
		writer.print(this.toString());
		writer.close();
	}
}
