package edu.iastate.cs228.hw1;

import com.sun.java.accessibility.util.EventQueueMonitor;

/**
 *  
 * @author Nathan Turnis
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal 
{

	/**
	 * Creates a Rabbit object.
	 * @param p: plain  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (Plain p, int r, int c, int a) 
	{
		plain = p;
		row = r;
		column = c;
		age = a;
	}
		
	// Rabbit occupies the square.
	public State who()
	{
		return State.RABBIT;
	}
	
	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox.  
	 * @param pNew     plain of the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a rabbit.

		int livingCount[] = new int[NUM_LIFE_FORMS];
		plain.grid[row][column].census(livingCount);

		if(age >= RABBIT_MAX_AGE){
			return new Empty(pNew, row, column);
		} else if(livingCount[GRASS] == 0){
			return new Empty(pNew, row, column);
		} else if((livingCount[FOX] + livingCount[BADGER] >= livingCount[RABBIT]) && livingCount[FOX] > livingCount[BADGER]) {
			return new Fox(pNew, row, column, 0);
		} else if(livingCount[BADGER] > livingCount[RABBIT]){
			return new Badger(pNew, row, column, 0);
		}  else {
			plain = pNew;
			age++;
			return this;
		}
	}
}
