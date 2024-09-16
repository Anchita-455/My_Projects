import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;



/** 
	* File Utilities for reading and writing 
	*
	* @author Anchita Dash
	* @since August 20,2021
	*/
	
public class FileUtils
{
		
		/**
		 * opens a file to read using the scanner class
		 * @param fileName 		the name of the file to open
		 * @return 				the scanner object to the file
		 */
		public static java.util.Scanner openToRead (String fileName)
		{
			java.util.Scanner input = null;
			
			try
			{
				input = new java.util.Scanner(new java.io.File(fileName));
			}
			
			catch(java.io.FileNotFoundException e)
			{
				System.err.println("ERROR: cannot open " + fileName + "for reading");
				System.exit(52);
				
			}
			
			return input;
		}
	
	/**
	 * opens a file to write using the printwriter class.
	 * @param fileName 		name of the file to open
	 * @return 				the printwriter object to the file
	 */
	
	public static PrintWriter openToWrite(String fileName)
	{
		PrintWriter output  = null;
		
		try
		{
			output = new PrintWriter(new File(fileName));
		}
		
		catch(FileNotFoundException e)
		{
			System.err.println("ERROR: cannot open " + fileName + "for writing");
			System.exit(53);
		}
		
		return output;
	}
	
	
	
	
	
}
