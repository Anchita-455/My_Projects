/**
 * This class is responsible for taking in a row and column. It performs 
 * various actions such as comparing it to another object, and returning the 
 * row and column of the coordinate. 
 * 
 * @author Anchita Dash
 * @since May 3, 2022
 */

public class Coordinate implements Comparable<Coordinate>
{
	private int row; 
	private int col;
	
	public Coordinate(int r, int c)
	{
		row = r;
		col = c;
	}

	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public boolean equals(Object other)
	{
		 if (other != null && other instanceof Coordinate &&
		 row == ((Coordinate)other).row &&
		 col == ((Coordinate)other).col)
			return true;
		 
		 return false;
	}
	
	public int compareTo(Coordinate other) {
		if (! (other instanceof Coordinate))
			throw new IllegalArgumentException("compareTo not Coordinate object");
		if (row > ((Coordinate)other).row || row < ((Coordinate)other).row)
			return row - ((Coordinate)other).row;
		if (col > ((Coordinate)other).col || col < ((Coordinate)other).col)
			return col - ((Coordinate)other).col;
		return 0;
	}

	public String toString()
	{	return "[ " + row + ", " + col + "]";  }
	
}
