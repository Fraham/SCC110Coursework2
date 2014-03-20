import javax.swing.*;

/**
 * Holds the information for each square.
 * 
 * Holds all the methods to be able to make valid moves.
 */
public class ChessSquare 
{
	private int x, y; 							// Holds the coordinates of the square, used to find the right square while searching from a click event. The coordinates start in the top left square
	private squareType type; 					// Holds the type of square, used to check where the square can move and for where other squares can move.
	private JButton btnSquare;					// Holds the square information, which is displayed on the chess board.

	/**
	 * Generates a new instance of a chess square.
	 * 
	 * Uses _location to work out the x and y coordinate values.
	 * 
	 * @param _location The location of the square on the board.
	 * @param _type The type of the square
	 */
	public ChessSquare (int _location, squareType _type)
	{
		y = (int) Math.floor(_location / 8);	// Calculates the y coordinate by using the array index value. So if the index is 7, the y value will be 0.
		x = _location - (8 * y);				// Calculates the y coordinate by using the array index value minus the just calculated y value.
		
		type = _type;							// Sets the square type to the right chess piece.
		btnSquare = new JButton();				// Creates a new button for the square.
		updateSquareImage();					// Updates the icon for the new square.
	}
	
	/**
	 * Returns the x location of the square on the board.
	 * 
	 * @return The x location of the square on the board.
	 */
	public int getX()
	{
		return x;						// returns the x value of the square.
	}
	
	/**
	 * Returns the y location of the square on the board.
	 * 
	 * @return The y location of the square on the board.
	 */
	public int getY()
	{
		return y;						// returns the y value of the square.
	}
	
	/**
	 * Returns the square type of the square on the board.
	 * 
	 * @return The square type of the square on the board.
	 */
	public squareType getSquareType()
	{
		return type;					// returns the square type.
	}
	
	/**
	 * Returns the button information.
	 * 
	 * @return The button information.
	 */
	public JButton getButton()
	{
		return btnSquare;				// returns the button form the square.
	}

	/**
	 * Sets the type of the square.
	 * 
	 * Uses the square types from squareType.
	 * 
	 * @param _type The square type to be set to the square.
	 */
	public void setSquareType(squareType _type)
	{
		type = _type;					// sets the new square type.
		
		updateSquareImage();			// updates the button icon.
	}
	
	/**
	 * Changes the image on the square.
	 */
	private void updateSquareImage()
	{
		ImageIcon i = new ImageIcon(getClass().getResource("EmptySquare.jpg")); 	// image icon to be used to be added to the square.
		
		switch (type) 																// switch using the current square type.
		{
			case EMPTYSQUARE: 														// if the square type is empty.
				i = new ImageIcon(getClass().getResource("EmptySquare.jpg")); 		// load the empty image into the image icon.
				break;
			
			case BISHOP: 															// if the square type is bishop
				i = new ImageIcon(getClass().getResource("Bishop.jpg")); 			// load the bishop image into the image icon.
				break;
				
			case KING:
				i = new ImageIcon(getClass().getResource("King.jpg"));
				break;
				
			case KNIGHT:
				i = new ImageIcon(getClass().getResource("Knight.jpg"));
				break;
				
			case PAWN:
				i = new ImageIcon(getClass().getResource("Pawn.jpg"));
				break;
				
			case QUEEN:
				i = new ImageIcon(getClass().getResource("Queen.jpg"));
				break;
				
			case ROOK:
				i = new ImageIcon(getClass().getResource("Rook.jpg"));
				break;
				
			case SELECTEDSQUARE:
				i = new ImageIcon(getClass().getResource("SelectedSquare.jpg"));
				break;
				
			default: 																// if there value is not right then it will be set to empty.
				i = new ImageIcon(getClass().getResource("EmptySquare.jpg"));
				break;
		}
		
		btnSquare.setIcon(i);														// sets the button icon to the correct type.
	}
	
	/**
	 * Clears a selected square.
	 * 
	 * Sets the square to the empty type.
	 */
	public void clearSelected()
	{
		setSquareType(squareType.EMPTYSQUARE); 		// set the square type to empty.
	}
	
	/**
	 * Moves the piece in the square to a new location.
	 * 
	 * It changes the new square to the old square type and then changes the square type to empty.
	 * 
	 * @param newSquare The new square.
	 */
	public void moveTo(ChessSquare newSquare)
	{		
		newSquare.setSquareType(type); 				// change the new square to the original square type.
		
		setSquareType(squareType.EMPTYSQUARE); 		// set the original square type to empty.
	}
	
	/**
	 * Sets all the valid squares.
	 * 
	 * Goes through all the squares and changes the icon of the valid squares to the selected type.
	 * 
	 * @param newSquares The array of squares.
	 */
	public void validSquares(ChessSquare[] newSquares)
	{
		for (int i = 0; i < 64; i++) 										// loop through all the squares.
		{
			if (canMoveTo(newSquares[i], newSquares)) 						// check if the move is valid.
			{
				newSquares[i].setSquareType(squareType.SELECTEDSQUARE); 	// if the square is valid, then change its type to selected.
			}
		}
	}
	
	/**
	 * Calculates if the piece can move to the other square or not.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The new square.
	 * @param newSquareArray The array of squares.
	 * @return If the piece can move to the other square or not.
	 */
	private boolean canMoveTo(ChessSquare newSquare, ChessSquare[] newSquareArray)
	{
		if (newSquare.getSquareType() == squareType.EMPTYSQUARE ||newSquare.getSquareType() == squareType.SELECTEDSQUARE)
		{
			switch(type) 												// switching the type of the square
			{
				case PAWN: 												// casing each different square type.
					return pawnValidMove(newSquare); 					// running the right method for each square type.
					
				case BISHOP:					
					return bishopValidMove(newSquare, newSquareArray);	// the full array is needed to check the whole range of squares across the board.
					
				case KING:					
					return kingValidMove(newSquare);
					
				case KNIGHT:					
					return knightValidMove(newSquare);
					
				case QUEEN:					
					return queenValidMove(newSquare, newSquareArray);
					
				case ROOK:					
					return rookValidMove(newSquare, newSquareArray);
					
				default:
					return false; 										// if the type is not valid then set it to false.
			}
		}
		else
		{		
			return false;
		}
	}
	
	/**
	 * Checks the valid moves for the pawn.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @return Whether the square is a valid move.
	 */
	private boolean pawnValidMove(ChessSquare newSquare)
	{
		int yDistance = y - newSquare.getY(); 								// how far apart the squares are from each other.
		
		if (yDistance < 3 && yDistance > 0 && x == newSquare.getX()) 		// if the squares are within 2 squares and in the right x column.
		{
			if (y != 6 && yDistance > 1) 									// if the pawn is not on the original row and the square is more than one square away.
			{
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks the valid moves for the bishop.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @param newSquareArray The array of squares.
	 * @return Whether the square is a valid move.
	 */
	private boolean bishopValidMove(ChessSquare newSquare, ChessSquare[] newSquareArray)
	{
		return moveDiagonally(newSquare, newSquareArray);
	}
	
	/**
	 * Checks the valid moves for the king.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @return Whether the square is a valid move.
	 */
	private boolean kingValidMove(ChessSquare newSquare)
	{
		int xDistance  = Math.abs(x - newSquare.getX()), yDistance = Math.abs(y - newSquare.getY());
			// get the absolute distance between the squares for both x and y.
		
		if ((xDistance == 1 && yDistance == 1) || (xDistance == 0 && yDistance == 1) || (xDistance == 1 && yDistance == 0))
			// if the square is one square away in each direction.
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks the valid moves for the knight.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @return Whether the square is a valid move.
	 */
	private boolean knightValidMove(ChessSquare newSquare)
	{
		int xDistance  = Math.abs(x - newSquare.getX()), yDistance = Math.abs(y - newSquare.getY());
			// get the absolute distance between the squares for both x and y.
		
		if ((xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1))
			// if the square in the knight jumping position.
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks the valid moves for the queen.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @param newSquareArray The array of squares.
	 * @return Whether the square is a valid move.
	 */
	private boolean queenValidMove(ChessSquare newSquare, ChessSquare[] newSquareArray)
	{
		return moveDiagonally(newSquare, newSquareArray) || moveStriaght(newSquare, newSquareArray);
	}
	
	/**
	 * Checks the valid moves for the rook.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @param newSquareArray The array of squares.
	 * @return Whether the square is a valid move.
	 */
	private boolean rookValidMove(ChessSquare newSquare, ChessSquare[] newSquareArray)
	{
		return moveStriaght(newSquare, newSquareArray);
	}
	
	/**
	 * Checks the valid moves for the squares in a straight line. 
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @param newSquareArray The array of squares.
	 * @return Whether the square is a valid move.
	 */
	private boolean moveStriaght(ChessSquare newSquare, ChessSquare[] newSquareArray)
	{
		if(y == newSquare.getY()) 									// if the square is in the same y coordinate.
		{
			if (newSquare.getX() < x) 								// if the square has a smaller x value.
			{
				for (int i = newSquare.getX(); i < x; i++) 			// loops through until the original square is reached.
				{
					if (newSquareArray[(8 * y) + i].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[(8 * y) + i].getSquareType() != squareType.SELECTEDSQUARE)
																	//checks if the square is a chess piece
					{
						return false;
					}
				}
			}
			else if (newSquare.getX() > x) 							// if the square has a greater x value.
			{
				for (int i = newSquare.getX(); i > x; i--) 			// loops through until the original square is reached.
				{
					if (newSquareArray[(8 * y) + i].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[(8 * y) + i].getSquareType() != squareType.SELECTEDSQUARE)
																	//checks if the square is a chess piece
					{
						return false;
					}
				}
			}
			return true;
		}
		else if (x == newSquare.getX()) 							// if the square is in the same x coordinate.
		{
			if (newSquare.getY() < y)  								// if the square has a smaller y value.
			{
				for (int i = newSquare.getY(); i < y; i++) 			// loops through until the original square is reached.
				{
					if (newSquareArray[(8 * i) + x].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[(8 * i) + x].getSquareType() != squareType.SELECTEDSQUARE)
																	//checks if the square is a chess piece
					{
						return false;
					}
				}
			}
			else if (newSquare.getY() > y) 							// if the square has a greater y value.
			{
				for (int i = newSquare.getY(); i > y; i--) 			// loops through until the original square is reached.
				{
					if (newSquareArray[(8 * i) + x].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[(8 * i) + x].getSquareType() != squareType.SELECTEDSQUARE)
																	//checks if the square is a chess piece
					{
						return false;
					}
				}
			}
			return true;
		}
		
		return false;
	}

	/**
	 * Checks the valid moves for the squares in a diagonal line.
	 * 
	 * It will return true if the square being searched if a valid move, false otherwise.
	 * 
	 * @param newSquare The square being checked if valid.
	 * @param newSquareArray The array of squares.
	 * @return Whether the square is a valid move.
	 */
	private boolean moveDiagonally(ChessSquare newSquare, ChessSquare[] newSquareArray)
	{
		int xDistance  = Math.abs(x - newSquare.getX()), yDistance = Math.abs(y - newSquare.getY()); 
																// get the absolute distance between the squares for both x and y.
		
		if (xDistance == yDistance) 							// if the square is on the diagonal line.
		{
			if (newSquare.getX() > x) 							// if the square is to the right
			{
				if (newSquare.getY() > y) 						// if the square is to the bottom
				{
					for(int i = newSquare.getY(); i > y; i--) 	// loops through until the original square is reached.
					{
						int index = (8 * i) + (i - y) + x;		// the (8 * i), i is the y coordinate, the end finds the x coordinate.
						
						if (newSquareArray[index].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[index].getSquareType() != squareType.SELECTEDSQUARE)
																//checks if the square is a chess piece
						{
							return false;
						}
					}
				}
				else  													// if the square is to the top
				{
					int count = 0;  									// counts the amount of squares away to get the right index
					
					for(int i = newSquare.getY(); i < y; i++)  			// loops through until the original square is reached.
					{
						int index = (8 * i) + newSquare.getX() - count; // the (8 * i), i is the y coordinate, the end finds the x coordinate.
						
						count++; 										//increment count
						
						if (newSquareArray[index].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[index].getSquareType() != squareType.SELECTEDSQUARE)
																		//checks if the square is a chess piece
						{
							return false;
						}
					}
				}
			}
			else 												// if the square is to the left
			{
				if (newSquare.getY() > y) 						// if the square is to the bottom
				{
					for(int i = newSquare.getY(); i > y; i--) 	// loops through until the original square is reached.
					{
						int index = (8 * i) + (y - i) + x;  	// the (8 * i), i is the y coordinate, the end finds the x coordinate.
						
						if (newSquareArray[index].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[index].getSquareType() != squareType.SELECTEDSQUARE)
																//checks if the square is a chess piece
						{
							return false;
						}
					}
				}
				else 													// if the square is to the top
				{
					int count = 0; 										// counts the amount of squares away to get the right index
					
					for(int i = newSquare.getY(); i < y; i++) 			// loops through until the original square is reached.
					{
						int index = (8 * i) + newSquare.getX() + count; // the (8 * i), i is the y coordinate, the end finds the x coordinate.
						
						count++; 										// increment count
						
						if (newSquareArray[index].getSquareType() != squareType.EMPTYSQUARE && newSquareArray[index].getSquareType() != squareType.SELECTEDSQUARE)
																		// checks if the square is a chess piece
						{
							return false;
						}
					}					
				}
			}
			return true;												// return true, as the square is a valid movement.
		}
		
		return false;													// return false, as the square is not in the diagonal.
	}
}
