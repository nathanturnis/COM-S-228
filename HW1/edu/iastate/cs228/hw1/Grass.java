package edu.iastate.cs228.hw1;

/**
 *  
 * @author Nathan Turnis
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten. 
 *
 */
public class Grass extends Living 
{

	public Grass (Plain p, int r, int c) 
	{
		plain = p;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.GRASS;
	}
	
	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for grass.

		int livingCount[] = new int[NUM_LIFE_FORMS];
		plain.grid[row][column].census(livingCount);

		if((livingCount[RABBIT] / 3) >= livingCount[GRASS]){
			return new Empty(pNew, row, column);
		} else if(livingCount[RABBIT] >= 3) {
			return new Rabbit(pNew, row, column, 0);
		} else {
			plain = pNew;
			return this;
		}
	}
}
