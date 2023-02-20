package edu.iastate.cs228.hw1;

/**
 *  
 * @author Nathan Turnis
 */

/**
 * 
 * Living refers to the life form occupying a square in a plain grid. It is a 
 * superclass of Empty, Grass, and Animal, the latter of which is in turn a superclass
 * of Badger, Fox, and Rabbit. Living has two abstract methods awaiting implementation. 
 *
 */
public abstract class Living 
{
	protected Plain plain; // the plain in which the life form resides
	protected int row;     // location of the square on which 
	protected int column;  // the life form resides
	
	// constants to be used as indices. 
	protected static final int BADGER = 0; 
	protected static final int EMPTY = 1; 
	protected static final int FOX = 2; 
	protected static final int GRASS = 3; 
	protected static final int RABBIT = 4; 
	
	public static final int NUM_LIFE_FORMS = 5; 
	
	// life expectancies 
	public static final int BADGER_MAX_AGE = 4; 
	public static final int FOX_MAX_AGE = 6; 
	public static final int RABBIT_MAX_AGE = 3; 
	
	
	/**
	 * Censuses all life forms in the 3 X 3 neighborhood in a plain.
	 *
	 * @param population counts of all life forms
	 * @return
	 */
	protected void census(int[] population)
	{
		// Count the numbers of Badgers, Empties, Foxes, Grasses, and Rabbits  
		// in the 3x3 neighborhood centered at this Living object.  Store the 
		// counts in the array population[] at indices 0, 1, 2, 3, 4, respectively.

		int plainWidth = plain.getWidth();

		//1x1 plain
		if(plainWidth == 1){
			switch(plain.grid[0][0].who()){
				case BADGER:
					population[BADGER] = 1;
					break;
				case EMPTY:
					population[EMPTY] = 1;
					break;
				case FOX:
					population[FOX] = 1;
					break;
				case GRASS:
					population[GRASS] = 1;
					break;
				case RABBIT:
					population[RABBIT] = 1;
					break;
			}
		}

		//2x2 top left neighborhood
		 else if(row == 0 && column == 0){
			 for(int i = 0; i < 2; i++){
				 for(int j = 0; j < 2; j++){
					 population[whoToIncrease(i, j)]++;
				 }
			 }

		//2x2 bottom right neighborhood
		} else if(row == plainWidth - 1 && column == plainWidth - 1) {
			 for(int i = plainWidth - 1; i > plainWidth - 3; i--) {
				 for (int j = plainWidth - 1; j > plainWidth - 3; j--) {
					 population[whoToIncrease(i, j)]++;
				 }
			 }

		//2x2 bottom left neighborhood
		} else if (row == plainWidth - 1 && column == 0){
			 for(int i = plainWidth - 1; i > plainWidth - 3; i--){
				 for(int j = 0; j < 2; j++){
					 population[whoToIncrease(i, j)]++;
				 }
			 }

		//2x2 top right neighborhood
		} else if(column == plainWidth - 1 && row == 0){
			 for(int i = 0; i < 2; i++){
				 for(int j = plainWidth -1; j > plainWidth - 3; j--){
					 population[whoToIncrease(i, j)]++;
				 }
			 }

		//3x2 left neighborhood
		} else if(column == 0){
			for(int i = row - 1; i < row + 2; i++){
				for(int j = 0; j < 2; j++){
					population[whoToIncrease(i, j)]++;
				}
			}

		//3x2 right neighborhood
		} else if(column == plainWidth - 1){
			for(int i = row - 1; i < row + 2; i++){
				for(int j = plainWidth - 1; j > plainWidth - 3; j--){
					population[whoToIncrease(i, j)]++;
				}
			}

		//2x3 top neighborhood
		} else if(row  == 0){
			for(int i = 0; i < 2; i++){
				for(int j = column - 1; j < column + 2; j++){
					population[whoToIncrease(i, j)]++;
				}
			}

		//2x3 bottom neighborhood
		} else if(row == plainWidth - 1){
			for(int i = row; i > row - 2; i--){
				for(int j = column - 1; j < column + 2; j++){
					population[whoToIncrease(i, j)]++;
				}
			}

		//3x3 middle neighborhood
		} else {
			for(int i = row - 1; i < row + 2; i++){
				for(int j = column - 1; j < column + 2; j++){
					population[whoToIncrease(i, j)]++;
				}
			}
		}
	}

	/**
	* Helper method to determine which living thing to add to the census array
	 *  based on grid location given
	 *
	 * @param i
	 * @param j
	* @return Living index constant
	 */
	private int whoToIncrease(int i, int j){
		switch (plain.grid[i][j].who()) {
			case BADGER:
				return BADGER;
			case EMPTY:
				return EMPTY;
			case FOX:
				return FOX;
			case GRASS:
				return GRASS;
			case RABBIT:
				return RABBIT;
		}
		return 0;
	}

	/**
	 * Gets the identity of the life form on the square.
	 * @return State
	 */
	public abstract State who();
	// To be implemented in each class of Badger, Empty, Fox, Grass, and Rabbit. 
	// 
	// There are five states given in State.java. Include the prefix State in   
	// the return value, e.g., return State.Fox instead of Fox.  
	
	/**
	 * Determines the life form on the square in the next cycle.
	 * @param  pNew  plain of the next cycle
	 * @return Living 
	 */
	public abstract Living next(Plain pNew); 
	// To be implemented in the classes Badger, Empty, Fox, Grass, and Rabbit. 
	// 
	// For each class (life form), carry out the following: 
	// 
	// 1. Obtains counts of life forms in the 3x3 neighborhood of the class object. 

	// 2. Applies the survival rules for the life form to determine the life form  
	//    (on the same square) in the next cycle.  These rules are given in the  
	//    project description. 
	// 
	// 3. Generate this new life form at the same location in the plain pNew.      

}
