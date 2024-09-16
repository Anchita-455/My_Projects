/**
 * This class is responsible for creating a snake board that 
 * will be printed throughout the game until it ends. It holds 
 * the snake and the target. The board is a default size of 20 by 20.
 * 
 *	@author	Anchita Dash
 *	@since	May 3, 2022
 */
public class SnakeBoard {
	
	/*	fields	*/
	private char[][] board;			// The 2D array to hold the board
	private int high;
	private int wide;
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width)
	{
		high = height;
		wide = width;
		board = new char[high][wide];
	}
	
	/**
	 *	Print the board to the screen.
	 */
	public void printBoard(Snake snake, Coordinate target)
	{
		printHeader();
		for(int i = 0; i < board.length; i++)
		{
			printRow(i,snake,target);
		}
		printHeader();
	}
	
	
	/* Helper methods go here	*/
	public void printHeader()
	{
		System.out.print("+" + " ");
		for(int i = 0; i < board[0].length; i++)
		{
			System.out.print("-" + " ");
		}
		System.out.print("+");
		System.out.println();
		
	}
	
	public void printRow(int row, Snake s, Coordinate target)
	{
		System.out.print("|" + " ");
		for(int i = 0; i < board[0].length; i++)
		{
			if(isHead(new Coordinate(row,i),s))
			{
				System.out.print("@ ");
			}
			else if(isBody(new Coordinate(row,i),s))
			{
				System.out.print("* ");
			}
			else if(target.getRow() == row && target.getCol() == i)
			{
				System.out.print("+ ");
			}
			else
			{
				System.out.print("  ");
			}
		}
		System.out.print("|");
		System.out.println();
	}
	
	private boolean isHead(Coordinate c, Snake s)
	{
		if(s.get(0).getValue().equals(c))
		{
			return true;
		}
		return false;
	}
	
	private boolean isBody(Coordinate c, Snake s)
	{
		for(int i = 1; i < s.size(); i++)
		{
			if(s.get(i).getValue().equals(c))
			{
				return true;
			}
		}
		return false;
	}
	/*	Accessor methods	*/
	public int getHeight()
	{
		return high;
	}
	
	public int getWidth()
	{
		return wide;
	}
	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args) {
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Snake snake = new Snake(new Coordinate(3, 3));
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
}
