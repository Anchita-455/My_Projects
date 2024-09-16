import java.util.Scanner;

/**
 * Binary Tree for State Objects
 *
 * @author Anchita Dash
 * @version 1 (working version)
 */
 
import java.util.Scanner; 
public class BinaryTree {

	private final String DEFAULT_FILE_NAME = "states2.txt"; // Default input file
	private Scanner keyboard;
	
	private TreeNode<State> root;
	
	public BinaryTree() 
	{
	}
	
	/**
	 *	Load data from a text file
	 */
	public void loadData()
	{
		System.out.println("Loading file states2.txt");
		System.out.println();
		keyboard = FileUtils.openToRead(DEFAULT_FILE_NAME);
		root = null;
		while(keyboard.hasNextLine())
		{
			String line = keyboard.nextLine();
			String name = line.substring(0,line.indexOf(" "));
			line = line.substring(line.indexOf(" ")+1).trim();
			String abbreviation = line.substring(0,line.indexOf(" "));
			line = line.substring(line.indexOf(" ")+1).trim();
			int pop = Integer.parseInt(line.substring(0,line.indexOf(" ")));
			line = line.substring(line.indexOf(" ")+1).trim();
			int area = Integer.parseInt(line.substring(0,line.indexOf(" ")));
			line = line.substring(line.indexOf(" ")+1).trim();
			int reps = Integer.parseInt(line.substring(0,line.indexOf(" ")));
			line = line.substring(line.indexOf(" ")+1).trim();
			String capital = line.substring(0,line.indexOf(" "));
			line = line.substring(line.indexOf(" ")+1).trim();
			int month = Integer.parseInt(line.substring(0,line.indexOf(" ")));
			line = line.substring(line.indexOf(" ")+1).trim();
			int day = Integer.parseInt(line.substring(0,line.indexOf(" ")));
			line = line.substring(line.indexOf(" ")+1).trim();
			int year = Integer.parseInt(line.substring(0));
			State s = new State(name,abbreviation,pop,area,reps,capital,month,day,year);
			insert(s);
		}
		keyboard.close(); 
	}
	
	/**
	 * Insert State into tree
	 * @param next  State to insert
	 */
	public void insert(State next) 
	{
		if(root == null)
		{
			root = new TreeNode<State>(next);
			return;
		}
		
		insert(next, root);
	}
	
	/**
	 * Insert state into a tree. Uses recursion to add each state to the
	 * subroot. 
	 * @param next 	state to insert
	 * @param subroot 	the root of the tree
	 */
	private void insert(State next, TreeNode<State> subroot)
	{
		if(next.compareTo(subroot.getValue()) < 0)
		{
			if(subroot.getLeft() == null)
			{
				subroot.setLeft(new TreeNode<State>(next));
			}
			
			else
			{
				subroot = subroot.getLeft();
				insert(next,subroot);
			}
			
		}
		
		else
		{
			if(subroot.getRight() == null)
			{
				subroot.setRight(new TreeNode<State>(next));
			}
			
			else
			{
				subroot = subroot.getRight();
				insert(next,subroot);
			}	
		}
		
	}

	/**
	 * Prints the tree as a list in ascending order by state name
	 */
	public void printList() 
	{
		if(root == null)
		{
			System.out.println();
			return;
		}
		
		printList(root);
	}
	
	/**
	 * Recursive method that prints the tree by inorder. 
	 * @param subroot 	the root of the tree
	 */
	private void printList(TreeNode<State> subroot)
	{
		if(subroot == null)
		{
			return;
		}
		
		printList(subroot.getLeft());
		System.out.println(subroot.getValue());
		printList(subroot.getRight());
			
	}
	
	
	/**
	 * Prompts user for State name to find, then starts search
	 */
	public void testFind() 
	{
		String input = Prompt.getString("Enter state name to search for (Q to quit) ");
		input = input.toLowerCase();
		while(!(input.equals("q")))
		{
			State state = testFind(input,root);
			
			if(state == null)
			{
				System.out.println("Name = " + input + "  No such state name");
				System.out.println();
			}
			else
			{
				System.out.println();
				System.out.println(state);
				System.out.println();
			}
			input = Prompt.getString("Enter state name to search for (Q to quit) ");
			input = input.toLowerCase();
		}
	}
	
	/**
	 * recursive method that is called with the state name and the subroot
	 * It returns the state when it matches with any of the nodes. Until then
	 * it keeps searching the left and right subtree to find the state.
	 * @return State	the state that was found.
	 */
	private State testFind(String name, TreeNode<State> subroot)
	{
		if(subroot == null)
		{
			return null;	
		}
		String stateName = (subroot.getValue()).getName().toLowerCase();
		if(name.compareTo(stateName) == 0)
		{
			return subroot.getValue();
		}
		
		if(name.compareTo(stateName) < 0)
		{
			return testFind(name,subroot.getLeft());
		}
		
		else
		{
			return testFind(name,subroot.getRight());
		}
		
	}
	

	/**
	 * Prompts user for State name to delete
	 * OPTIONAL: Not included in your grade!
	 */
	public void testDelete() 
	{
		if(root == null)
		{
			return;
		}
		String state = Prompt.getString("Enter state name to delete for (Q to quit) ");
		state = state.toLowerCase();
		System.out.println("Deleted " + state);
		while(!(state.equals("q")))
		{
			testDelete(null,root,state,false);
			state = Prompt.getString("Enter state name to delete for (Q to quit) ");
			state = state.toLowerCase();
		}
	}
	
	/**
	 * This is the recursive delete function that handles the three cases of
	 * the deleted node being a parent, child or a leaf. The leaf is deleted 
	 * directly. For the only left/right subtree, the subroot of that left/right child is now the
	 * the new subroot of that parent. For the parent being deleted, the lowest value
	 * in the right subtree is found, and the parent is set to that value.
	 * 
	 * @param parent	the parent of the subroot
	 * @param subroot	the root we are currently looking at
	 * @param state		the state that needs to be deleted. 
	 * @param left		a boolean that checks whether the value is in the left 
	 * or right subtree of the root we are looking at. 
	 * 
	 */
	private void testDelete(TreeNode<State> parent, TreeNode<State> subroot, String state, boolean left)
	{
		if(subroot == null)
		{
			return;
		}
		
		if((subroot.getValue()).getName().toLowerCase().compareTo(state) < 0)
		{
			testDelete(subroot,subroot.getRight(),state,false);
		}
		else if((subroot.getValue()).getName().toLowerCase().compareTo(state) > 0)
		{
			testDelete(subroot,subroot.getLeft(),state,true);
		}
		else
		{
			
			// leaf node deletion
			if(subroot.getLeft() == null && subroot.getRight() == null)
			{
				if(parent == null)
				{
					root = null;
					return;
				}
				if(left == true)
				{
					parent.setLeft(null);
					return;
				}
				
				else
				{
					parent.setRight(null);
					return;
				}
			}
			
			// only left child
			if(subroot.getRight() == null)
			{
				if(parent == null)
				{
					root = subroot.getLeft();
					return;
				}
				
				if(left == true)
				{
					parent.setLeft(subroot.getLeft());
					return;
				}
				
				else
				{
					parent.setRight(subroot.getLeft());
					return;
				}
			}
			
			// only right child
			if(subroot.getLeft() == null)
			{
				if(parent == null)
				{
					root = subroot.getRight();
					return;
				}
				
				if(left == true)
				{
					parent.setLeft(subroot.getRight());
					return;
				}
				
				else
				{
					parent.setRight(subroot.getRight());
					return;
				}
			}
			
			// two children deletion
			TreeNode<State> temp = subroot.getRight();
			while(temp.getLeft() != null)
			{
				temp = temp.getLeft();
			}
			subroot.setValue(temp.getValue());
			testDelete(subroot,subroot.getRight(),(temp.getValue()).getName().toLowerCase(),false);
		}
		
	}
	
	/**
	 * Finds the number of nodes starting at the root of the tree
	 * @return  the number of nodes in the tree
	 */
	public int size() 
	{
		if(root == null)
		{
			return 0;
		}
		return size(root);
	}
	
	/**
	 * Counts the number of nodes in the left subtree and the number of nodes
	 * in the right subtree.
	 * @param subroot 	the root of the tree.
	 * @return size		the total number of nodes in the tree.
	 */
	private int size(TreeNode<State> subroot)
	{
		if(subroot == null)
		{
			return 0;
		}
		return 1+size(subroot.getLeft()) + size(subroot.getRight());
		
	}
	
	/**
	 * Clears the tree of all nodes
	 */
	public void clear() 
	{
		root = null;
	}
	
	/**
	 * Prompts user for level of tree to print.
	 * The top level (root node) is level 0.
	 */
	public void printLevel() 
	{
		int inputLevel = Prompt.getInt("Enter level value to print (-1 to quit) ");
		while(inputLevel != -1)
		{
			if(root == null)
			{
				System.out.println();
			}
			
			System.out.println();
			System.out.println("Level \t" + inputLevel);
			printLevel(root,0, inputLevel);
			System.out.println();
			System.out.println();
			inputLevel = Prompt.getInt("Enter level value to print (-1 to quit) ");
		}
		System.out.println();
	}
	
	/**
	 * Checks the level in which the particular state is located and prints
	 * the level and the state.
	 * @param subroot 	the subroot of the tree
	 * @param currentLevel	the level that we are currently checking in
	 * @param level		the level that the user inputs in.
	 */
	private void printLevel(TreeNode<State> subroot, int currentLevel, int level)
	{
		if(subroot == null)
		{
			return;
		}
		if(level == currentLevel)
		{
			System.out.print((subroot.getValue()).getName() + "\t");
			return;
		}
		
		currentLevel++;
		printLevel(subroot.getLeft(),currentLevel,level);
		printLevel(subroot.getRight(),currentLevel,level);
	}
	
	
	/**
	 * Prints the highest level of the tree (root is level 0),
	 * prints "Tree empty" if empty tree
	 */
	public void testDepth() 
	{
		if(root == null)
		{
			System.out.println("Tree empty");
			System.out.println();
			return;
		}
		int depth = testDepth(root)-1;
		System.out.println();
		System.out.println("Depth of the tree = " + depth);
		System.out.println();
		System.out.println();
	}
	
	/**
	 * compares the number of nodes in the left and right subtree. 
	 * @return int 		the depth of the tree after comparison.
	 * @param subroot	the root of the tree
	 */
	private int testDepth(TreeNode<State> subroot)
	{
	
		if(subroot == null)
		{
			return 0;
		}
		
		int leftCount = 1+testDepth(subroot.getLeft());
		int rightCount = 1+testDepth(subroot.getRight());
		if(rightCount > leftCount)
		{
			return rightCount;
		}
		return leftCount;
	}

}
