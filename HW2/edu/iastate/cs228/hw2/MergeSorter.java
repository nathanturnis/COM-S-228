package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Nathan Turnis
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{

		int arraySize = pts.length;
		if(arraySize < 2) {
			return;
		}

		int midpoint = arraySize / 2;
		Point[] leftarr = new Point[midpoint];
		Point[] rightarr = new Point[arraySize - midpoint];

		for(int i = 0; i < midpoint; i++){
			leftarr[i] = pts[i];
		}

		for(int i = midpoint; i < arraySize; i++){
			rightarr[i - midpoint] = pts[i];
		}

		mergeSortRec(leftarr);
		mergeSortRec(rightarr);
		merge(pts, leftarr, rightarr);

	}


	private void merge(Point[] pts, Point[] leftarr, Point[] rightarr){

		int leftSize = leftarr.length;
		int rightSize = rightarr.length;
		int i = 0, j = 0, k = 0; //i for leftarr, j for rightarr, k for pts

		while(i < leftSize && j < rightSize){
			if(pointComparator.compare(leftarr[i], rightarr[j]) < 0){
				pts[k] = leftarr[i];
				i++;
			} else {
				pts[k] = rightarr[j];
				j++;
			}
			k++;
		}

		while(i < leftSize){
			pts[k] = leftarr[i];
			i++;
			k++;
		}

		while(j < rightSize){
			pts[k] = rightarr[j];
			j++;
			k++;
		}

	}

}
