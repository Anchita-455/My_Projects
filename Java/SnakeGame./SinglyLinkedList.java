import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - This class is responsible for implementing some
 * methods of the singly linked list. It primarily uses two variables 
 * head and tail which are pointers to the first and last element respectively. 
 * These pointers help to access other elements that are present in the linked list
 * as they have access to the next pointers. This class also uses the 
 * ListNode class, which is responsible for nodes which are small segments 
 * that a linked list is made up of. Nodes hold pointers and values which 
 * are necessary for the implementation of this assignment. 
 *
 *	@author	Anchita Dash
 *	@since	April 12, 2022
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	
	/* No-args Constructors */
	public SinglyLinkedList() 
	{
		head = null;
		tail = null;
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList) 
	{
		if(oldList.isEmpty())
		{
			head = null;
			tail = null;
			return;
		}
		head = new ListNode<E>(oldList.get(0).getValue());
		ListNode<E> current = head;

		for(int i = 1; i < oldList.size(); i++)
		{
			current.setNext(new ListNode<E>(oldList.get(i).getValue()));
			current = current.getNext();
		}
		tail = current;
		
	}
	
	/**	Clears the list of elements */
	public void clear() 
	{
		head = null;
		tail = null;
	}
	
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) 
	{
		ListNode<E> node = new ListNode<E>(obj);
		if(size() == 0)
		{
			head = node;
			tail = node;
			return true;
		}
	
		tail.setNext(node);
		tail = node;
		return true;
	}
	
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) 
	{
		if(index > size() || index < 0)
		{
			throw new NoSuchElementException();
		}
		ListNode<E> node = new ListNode<E>(obj);
		if(index == size())
		{
			return add(obj);
		}
		
		if(index == 0)
		{
			node.setNext(head);
			head = node;
			return true;
		}
		
		ListNode<E> current = head;
		for(int i = 0; i < index-1; i++)
		{
			current = current.getNext();
		}
		node.setNext(current.getNext());
		current.setNext(node);
		
		return true;
	}
	
	/**	@return the number of elements in this list */
	public int size() 
	{
		int count = 0;
		ListNode<E> current = head;
		while(current != null)
		{
			count++;
			current = current.getNext();
		}
		return count;
	}
	
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) 
	{
		if(index >= size() || index < 0)
		{
			throw new NoSuchElementException();
		}
		
		ListNode<E> current = head;
		for(int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		
		return current;
	}
	
	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj)
	{
		if(index >= size() || index < 0)
		{
			throw new NoSuchElementException();
		}
		
		ListNode<E> current = head;
		for(int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		E val = current.getValue();
		current.setValue(obj);
		
		return val;
	}
	
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) 
	{
		if(index >= size() || index < 0)
		{
			throw new NoSuchElementException();
		}
		
		if(size() == 1)
		{
			E removed = head.getValue();
			head = null;
			tail = null;
			return removed;
		}
		
		if(index == 0)
		{
			E removed = head.getValue();
			head = head.getNext();
			return removed;
		}
	
		if(index == size()-1)
		{
			E removed = tail.getValue();
			ListNode<E> current = head;
			
			for(int i = 0; i < index-1; i++)
			{
				current = current.getNext();
			}
			current.setNext(null);
			tail = current;
			
			return removed;
		}
		
		ListNode<E> current = head;
		for(int i = 0; i < index-1; i++)
		{
			current = current.getNext();
		}
		E val = current.getNext().getValue();
		current.setNext(current.getNext().getNext());
		
		return val;
	}
	
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty() 
	{
		if(head == null)
		{
			return true;
		}
		return false;
	}
	
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object) 
	{
		ListNode<E> current = head;
		while(current != null)
		{
			if(current.getValue().equals(object))
			{
				return true;
			}
			current = current.getNext();
		}
		return false;
	}
	
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element) 
	{
		ListNode<E> current = head;
		int count = 0;
		while(current != null)
		{
			if(current.getValue().equals(element))
			{
				return count;
			}
			current = current.getNext();
			count++;
		}
		
		return -1;
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;
		while (ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
	

}
