package edu.iastate.cs228.hw1;

/**
 *  
 * @author Nathan Turnis
 */

/**
 * A badger eats a rabbit and competes against a fox. 
 */
public class Badger extends Animal
{

	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Badger (Plain p, int r, int c, int a) 
	{
		plain = p;
		row = r;
		column = c;
		age = a;
	}
	
	/**
	 * A badger occupies the square. 	 
	 */
	public State who()
	{
		return State.BADGER;
	}
	
	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group of foxes. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a badger.

		int livingCount[] = new int[NUM_LIFE_FORMS];
		plain.grid[row][column].census(livingCount);

		if(age >= BADGER_MAX_AGE){
			return new Empty(pNew, row, column);
		} else if(livingCount[BADGER] == 1 && livingCount[FOX] > 1){
			return new Fox(pNew, row, column, 0);
		} else if(livingCount[BADGER] + livingCount[FOX] > livingCount[RABBIT]) {
			return  new Empty(pNew, row, column);
		} else {
			//will be badger with age +1
			plain = pNew;
			age++;
			return this;
		}
	}
}
