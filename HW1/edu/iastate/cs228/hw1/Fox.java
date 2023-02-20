package edu.iastate.cs228.hw1;

/**
 *  
 * @author Nathan Turnis
 */

/**
 * A fox eats rabbits and competes against a badger. 
 */
public class Fox extends Animal 
{

	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (Plain p, int r, int c, int a) 
	{
		plain = p;
		row = r;
		column = c;
		age = a;
	}
		
	/**
	 * A fox occupies the square. 	 
	 */
	public State who()
	{
		return State.FOX;
	}
	
	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a fox.

		int livingCount[] = new int[NUM_LIFE_FORMS];
		plain.grid[row][column].census(livingCount);

		if(age >= FOX_MAX_AGE){
			return new Empty(pNew, row, column);
		} else if(livingCount[BADGER] > livingCount[FOX]){
			return new Badger(pNew, row, column, 0);
		} else if(livingCount[BADGER] + livingCount[FOX] > livingCount[RABBIT]) {
			return new Empty(pNew, row, column);
		} else {
			plain = pNew;
			age++;
			return this;
		}
	}
}
