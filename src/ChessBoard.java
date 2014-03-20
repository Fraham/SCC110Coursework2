import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Holds the information for the chess board.
 * This holds the information about the board. Holds the array of buttons and which buttons have been selected.
 * It also creates the frame and design of the game, which will display all the squares.
 */
public class ChessBoard implements ActionListener
{
	private ChessSquare[] board;						// holds all the buttons used in the chess board.
	private int intSelectedLoction = -1;				// holds the index of the square that has been pressed.
	private int intOriginal = -1;						// holds the index of the square being moved to.
	
	/**
	 *Generates a new instance of a chess board.
	 *
	 *It will setup the board with the right pieces in the right place and will setup the frame to display the game.
	 */
	public ChessBoard()
	{
		setupBoard();
		
		setupFrame();
	}

	/**
	 * Sets up the board.
	 * 
	 * Makes sure the the chess pieces have the right starting location. Making the rest of the squares empty.
	 */
	private void setupBoard()
	{
		board = new ChessSquare[64]; 									// creating the 8x8 grid.
		
		for (int i = 0; i < 64; i++) 									// looping the height elements.
		{
			if (i > 55) 												// if on the final row.
			{
				if (i == 56|| i == 63) 									// if the end columns, must be a rook.
				{
					board[i] = new ChessSquare(i, squareType.ROOK);
				}
				if (i == 57|| i == 62) 									// if one in from the ends, must be a knight.
				{
					board[i] = new ChessSquare(i, squareType.KNIGHT);
				}
				if (i == 58|| i == 61) 									// if two in from the ends, must be a bishop
				{
					board[i] = new ChessSquare(i, squareType.BISHOP);
				}
				if (i == 59) 											// if four in from the end, must be a queen.
				{
					board[i] = new ChessSquare(i, squareType.QUEEN);
				}
				if (i == 60) 											// if three in from the end, must be a king.
				{
					board[i] = new ChessSquare(i, squareType.KING);
				}
			}
			else if (i > 47) 											// if on the line is on the row of of pawns.
			{
				board[i] = new ChessSquare(i, squareType.PAWN);
			}
			else 														// else fill with empty squares.
			{
				board[i] = new ChessSquare(i, squareType.EMPTYSQUARE);
			}			
		}
	}
	
	/**
	 * Creates the frame for the game of chess.
	 */
	private void setupFrame() 
	{
		JFrame frame = new JFrame(); 									// create a frame for the chess game.
		
		frame.setTitle("Chess"); 										// setting the default values for the frame.
		frame.setSize(44*8, 44*8);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel gridPanel = new JPanel();
		
		GridLayout gridLayout = new GridLayout(8, 8);
		
		for (int i = 0; i < 64; i++) 									// looping the through all the squares.
		{
			gridPanel.add(board[i].getButton());						// add the button to the grid pattern.
			
			board[i].getButton().addActionListener(this);				// adding the button to the action listener.
		}
		
		gridPanel.setLayout(gridLayout); 								// apply the grid layout.
		
		frame.setContentPane(gridPanel);								// applying to the content to the frame.
		
		frame.setVisible(true); 										// making the frame visible
	}
	
	/**
	 * Find the index of the square.
	 * 
	 * @param e The event sent to the listener.
	 */
	private void findButtonLocation(ActionEvent e)
	{
		for(int i = 0; i < 64; i++) 									// looping the through all the squares.
		{
			if (e.getSource() == board[i].getButton()) 					// getting right square information.
			{
				intSelectedLoction = i; 								// saving the index for later use.
				break; 													// ending the loop once the square has been found.
			}
		}
	}
	
	/**
	 * Clear all the selected square back to empty squares.
	 */
	private void clearSquares()
	{
		for(int i = 0; i < 64; i++) 									// looping the through all the squares.
		{
			if (board[i].getSquareType() == squareType.SELECTEDSQUARE) 	// if the square is selected then clear it to empty.
			{
				board[i].clearSelected();
			}
		}
	}
	
	/**
	 * Displays all the valid squares depending on the selected square.
	 */
	private void showValid()
	{		
		board[intSelectedLoction].validSquares(board); 					//Displays all the valid squares depending on the selected square.
	}
	
	/* 
	 * Handles the event when the squares are pressed.
	 * 
	 * @param e The event sent to the listener.
	 */
	public void actionPerformed(ActionEvent e)
	{		
		if (intSelectedLoction == -1)
		{
			findButtonLocation(e); 															// find the index of the square being looked at.
			
			if (board[intSelectedLoction].getSquareType() == squareType.EMPTYSQUARE || board[intSelectedLoction].getSquareType() == squareType.SELECTEDSQUARE) 
																							// if the user selected a square without a piece in it.
			{
				intSelectedLoction = -1; 													// returning the selected value back to default.
			}
			else 																			// if the user selected a piece.
			{
				intOriginal = intSelectedLoction;											// setting the new original selected to be used later.
				
				showValid(); 																// displays all the valid squares.
			}
		}
		else
		{			
			findButtonLocation(e); 															// find the index of the square being looked at.
			
			if (board[intSelectedLoction].getSquareType() == squareType.SELECTEDSQUARE) 	// if the square is a valid location.
			{
				board[intOriginal].moveTo(board[intSelectedLoction]); 						// move the original square to the new square.
				
				intSelectedLoction = -1; 													// returning the selected value back to default.
				
				clearSquares(); 															// returning the selected value back to default.
			}			
			else if (board[intSelectedLoction].getSquareType() != squareType.EMPTYSQUARE)
			{
				intSelectedLoction = -1; 													// returning the selected value back to default.
				
				clearSquares(); 															// returning the selected value back to default.
				
				actionPerformed(e); 														// calling the method again with the new square selected.
				
			}			
		}
	}
}
