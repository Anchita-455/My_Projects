/**
 *	Snake Game - In this game, the snake travels all around the board looking for a target.
 The target is like a food for the snake, and after eating this target, it grows it tail
 by one asterisk. If the snake runs over itself or tries to cross the boundary it dies and
 the game ends. The score of the game is calculated every time the snake eats a target.
 *	
 *	@author	Anchita Dash
 *	@since	May 3, 2022
 */
 
import java.io.PrintWriter;
import java.util.Scanner;

public class SnakeGame {
	
	private Snake snake;		// the snake in the game
	private SnakeBoard board;	// the game board
	private Coordinate target;	// the target for the snake
	private int score;			// the score of the game
	private int openSpaces;

	/*	Constructor	*/
	public SnakeGame() 
	{ 
		board = new SnakeBoard(20,20);
		snake = new Snake(new Coordinate(7,7));
		openSpaces = (board.getHeight()*board.getWidth())-(snake.size());
		pickTarget();
		score = 0; 

		// print the introduction before the prompting the user
	}
	
	/*	Main method	*/
	public static void main(String[] args) 
	{
		SnakeGame s = new SnakeGame();
		s.printIntroduction();
		s.playGame();
	}
	
	/**	Print the game introduction	*/
	public void printIntroduction() {
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	
	/**	Print help menu	*/
	public void helpMenu() {
		System.out.println("\nCommands:\n" +
							"  w - move north\n" +
							"  s - move south\n" +
							"  d - move east\n" +
							"  a - move west\n" +
							"  h - help\n" +
							"  f - save game to file\n" +
							"  r - restore game from file\n" +
							"  q - quit");
		Prompt.getString("Press enter to continue");
	}
	
	/**
		Gets the input from the user and returns the string based on the input
		@return ans 	valid string that the user typed
	*/
	public String getInput()
	{
		board.printBoard(snake,target);
		String ans = Prompt.getString("Score: " + score + " w - North, s - South, d - East, a - West, h - help");
		String combined = "wsdahfrq";
		
		while(ans.length() == 0 || combined.indexOf(ans) == -1)
		{
			ans = Prompt.getString("Score: " + score + " w - North, s - South, d - East, a - West, h - help");
		}
			
		return ans;
	}
	
	/**
		This method controls the whole play of the game and is responsible for printing
		the board and asking for input based on how the game is going. It exits the 
		program if "q" is inputted by the user.
	*/
	public void playGame()
	{
		String input = getInput();
		String moves = "wsda";
		boolean done = false;
		
		if(input.equals("q"))
		{
			System.out.println();
			String temp = Prompt.getString("Do you really want to quit? (y or n) ");
			if(temp.equals("y"))
			{
				done = true;
			}
		}
			
		while(done == false)
		{
			if(input.equals("h"))
			{
				helpMenu();
			}
			else if(moves.indexOf(input) != -1)
			{
				done = !makeMove(input);
			}
			
			else if(input.equals("f"))
			{
				System.out.println();
				input = Prompt.getString("Save game? (y or n) ");
				if(input.equals("y"))
				{
					saveGame();
					done = true;
				}
			}
			else if(input.equals("r"))
			{
				restoreGame();
			}
			
			if(openSpaces == 5)
			{
				done = true;
				board.printBoard(snake,target);
			}

			if(isSurrounded() == true)
			{
				done = true;
				board.printBoard(snake,target);
			}
			
			if(done == false)
			{
				input = getInput();
				if(input.equals("q"))
				{
					System.out.println();
					String temp = Prompt.getString("Do you really want to quit? (y or n) ");
					if(temp.equals("y"))
					{
						done = true;
					}
				}
			}
		}
		System.out.println();
		System.out.println("Game is over");
		System.out.println("Score = " + score);
		System.out.println();
		System.out.println("Thanks for playing SnakeGame!!");
	}
	
	/**
		This method moves the snake around the board based on the input provided by the user
		@param input	the input that the user provided
	*/
	public boolean makeMove(String input)
	{
		Coordinate c  = null;
		
		// moves the snake to the north.
		if(input.equals("w"))
		{
			c  = new Coordinate(snake.get(0).getValue().getRow()-1, snake.get(0).getValue().getCol());
			// check bounds and verify why the snake stops two rows/cols before the boundary.
			if(c.getRow() < 0)
			{
				return false;
			}
		}
		
		// moves the snake to the south.
		else if(input.equals("s"))
		{
			c = new Coordinate(snake.get(0).getValue().getRow()+1, snake.get(0).getValue().getCol());
			
			if(c.getRow() >= board.getHeight())
			{
			 	return false;
			}
			
		}
		
		// moves the snake to the east.
		else if(input.equals("d"))
		{
			c = new Coordinate(snake.get(0).getValue().getRow(), snake.get(0).getValue().getCol()+1);
			if(c.getCol() >= board.getWidth())
			{
				return false;
			}
		}
		
		// moves the snake to the west.
		else
		{
			c = new Coordinate(snake.get(0).getValue().getRow(), snake.get(0).getValue().getCol()-1);
			if(c.getCol() < 0)
			{
				return false;
			}
		}
		
		// checks whether the coordinate falls into the body of the snake/head of the snake.
		if(isSnake(c) == true)
		{
			return false;
		}
		
		// checks whether the snake ate the target and decrements the number 
		// of open spaces and increases the score.
		if(target.equals(c) == true)
		{
			snake.add(0,c);
			score++;
			openSpaces--;
			pickTarget();
		}
		
		// if the snake has not eaten the target then a coordinate in that
		// direction is added (for the head) and the original tail is removed.
		else
		{
			snake.add(0,c);
			snake.remove(snake.size()-1);
		}
		
		//System.out.println(openSpaces);
		return true;
	}
		
	/**
		This method is responsible for picking the target. The coordinate for the target
		is chosen randomly on the board. This method also checks whether there are any 
		obstacles in the way such as a snake head/body and makes sure that the target is
		not placed over it.
	*/
	public void pickTarget()
	{
		int targetRow = (int)(Math.random()*board.getHeight());
		int targetCol = (int)(Math.random()*board.getWidth());
		
		while(isSnake(new Coordinate(targetRow,targetCol)) == true)
		{
			targetRow = (int)(Math.random()*board.getHeight());
			targetCol = (int)(Math.random()*board.getWidth());
		}
		target = new Coordinate(targetRow,targetCol);
		
	}
	
	public boolean isSurrounded()
	{
		Coordinate top = null;
		Coordinate bottom = null;
		Coordinate left = null;
		Coordinate right = null;
		
		// top
		if(snake.get(0).getValue().getRow()-1 >= 0)
		{
			top = new Coordinate(snake.get(0).getValue().getRow()-1,snake.get(0).getValue().getCol());
		}
		
		if(top != null && isSnake(top) == false)
		{
			return false;
		}
		
		// bottom
		if(snake.get(0).getValue().getRow()+1 < board.getHeight())
		{
			bottom = new Coordinate(snake.get(0).getValue().getRow()+1,snake.get(0).getValue().getCol());
		}
		
		if(bottom != null && isSnake(bottom) == false)
		{
			return false;
		}
		
		// left
		if(snake.get(0).getValue().getCol()-1 >= 0)
		{
			left = new Coordinate(snake.get(0).getValue().getRow(),snake.get(0).getValue().getCol()-1);
		}
		
		if(left != null && isSnake(left) == false)
		{
			return false;
		}
		
		// right
		if(snake.get(0).getValue().getCol()+1 < board.getWidth())
		{
			right = new Coordinate(snake.get(0).getValue().getRow(),snake.get(0).getValue().getCol()+1);
		}
		
		if(right != null && isSnake(right) == false)
		{
			return false;
		}
		
		return true;
	}

	/**
		Helper method that goes through the snake list and checks whether the coordinate 
		matches the body or the head.
	*/
	private boolean isSnake(Coordinate c)
	{
		for(int i = 0; i < snake.size(); i++)
		{
			if(snake.get(i).getValue().equals(c))
			{
				return true;
			}
		}
		return false;
	}
	
	private void saveGame()
	{
		PrintWriter writer = FileUtils.openToWrite("snakeGameSave.txt");
		writer.println("Score " + score);
		writer.println("Target " + target.getRow() + " " + target.getCol());
		writer.println("Snake " + snake.size());
		for(int i = 0; i < snake.size(); i++)
		{
			writer.println(snake.get(i).getValue().getRow() + " " + snake.get(i).getValue().getCol());
		}
		System.out.println();
		System.out.println("Saving game to file snakeGameSave.txt");
		writer.close();
	}
	
	private void restoreGame()
	{
		Scanner reader = FileUtils.openToRead("snakeGameSave.txt");
		
		// getting the score
		String line = reader.nextLine();
		line = line.substring(6);
		score = Integer.parseInt(line);
		
		// getting the target
		line = reader.nextLine();
		line = line.substring(7);
		int row = Integer.parseInt(line.substring(0,line.indexOf(" ")));
		int col = Integer.parseInt(line.substring(line.indexOf(" ")+1));
		target = new Coordinate(row,col);
		
		// getting the snake
		int size = Integer.parseInt(reader.nextLine().substring(6));
		snake.clear();
		for(int i = 0; i < size; i++)
		{
			line = reader.nextLine();
			row = Integer.parseInt(line.substring(0,line.indexOf(" ")));
			col = Integer.parseInt(line.substring(line.indexOf(" ")+1));
			snake.add(new Coordinate(row,col));
		}
		openSpaces = (board.getHeight()*board.getWidth())-(size);
	}
	
}
