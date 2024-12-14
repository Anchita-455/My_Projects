/**
 *	The Snake is a singly linked list with snake behaviors.
 *	@author	Anchita Dash
 *	@since	May 3, 2022
 */
public class Snake extends SinglyLinkedList<Coordinate> {
	
	// Fields
	
	public Snake(Coordinate location)
	{
		add(new Coordinate(location.getRow(),location.getCol()));
		add(new Coordinate(location.getRow()+1,location.getCol()));
		add(new Coordinate(location.getRow()+2,location.getCol()));
		add(new Coordinate(location.getRow()+3,location.getCol()));
		add(new Coordinate(location.getRow()+4,location.getCol()));
	}
	
}
