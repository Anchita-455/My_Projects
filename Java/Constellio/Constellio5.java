// Anchita Dash
// Constellio5.java
// 04/19/2021


// all the imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.Dimension;

import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyListener; 
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener; 
import java.awt.event.FocusEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.MouseEvent;

import java.io.File; 
import java.util.Scanner;
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.PrintWriter; 
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

// This class makes the frame and calls another class Constellio4Panel which handles the 
// job done in the game. The instance of Constellio4Panel is also created and added to the frame.  
public class Constellio5
{
	// creates an instance of the class itself and calls the method makingFrame. 
	public static void main(String args[])
	{
		Constellio5 c5 = new Constellio5();  // creates an instance of the Constellio4 class
		c5.makingFrame(); // calls a method making frame which makes the frame.
	}
	
	// This method is responsible to create the frame labeled "Constellio4"
	// It sets the size and location of the frame. 
	// This method also creates an instance of the Constellio4Panel class whose 
	// contents are added to the frame.
	public void makingFrame()
	{
		JFrame frame = new JFrame("Constellio 5"); // makes the frame named Constellio4
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocation(10,100);
		
		ConstellioPanel cp = new ConstellioPanel(); // creates an instance of Constellio4Panel class.
		frame.getContentPane().add(cp); // adds the content of that class to the frame. 
		frame.setVisible(true);  // sets the visibility of the frame to true, so that it can be seen. 
	}
}

	
// This class creates the cardLayout and all the cards(JPanels) are added to this CardLayout
// which is later shown. This class also initializes the arrays by reading the textfiles. 
// Scanner is used to read the text files and alternative lines are stored in the array. 
// For example 1stLine is the question and the 2nd Line is the answer. It also reads the 
// name of the constellations which is stored in another textFile. The Images are also 
// stored in the image array which stores the different images of the constellations.
class ConstellioPanel extends JPanel
{
	// all field variables declared
	private CardLayout cards; // variable for setting the cardLayout.
	private HomePanel homeCard; // instance of the HomePanel class which is the first page in the CardLayout. 
	private HowToPlayPanel howToPlayCard; // instance of the HowToPlay class which is the second page in the CardLayout. 
	private ConstellationGuidePanel constellationGuideCard; // instance of the ConstellationGuidePanel class which is the third page in the CardLayout.
	private LevelsPanel levelsCard; // instance of the LevelsPanel class which is the fourth page in the CardLayout.
	private HighScoresPanel highScoresCard; // instance of the HighScoresPanel class which is the fifth page in the CardLayout.
	private GamePanel gameCard;  // instance of the GamePanel class which is the sixth page in the CardLayout.
	private String[] constellations; // array to store the names of constellations.
	private String[] questions; // array to store questions.
	private String[] answers; // array to store answers.
	private Image[] images; // array to store images displayed in both game and constellationGuidePanel.
	private String[] information; // array to store information.
	private String[] explanations; // array storing explanations.
	private String[] highScores; // array for storing the high score.
	private boolean[] levelsPlayed; // array for easy,medium, hard to switch to the other levels. 
	private boolean[] questionsAsked; // array to handle the questions asked already.
	private Timer timer; // instance of timer created. 
	private String mission; // stores the string that contains instructions about how the game is played. 
	private Scanner reader; // reads in the textFiles 
	private PrintWriter writer; // writes the high scores
	private String levelName; // stores the names of the levels such as easy,medium or hard. 
	private Image gameGuide; // stores an image that is a guide in the how to play panel.
	private int okClicked; // variable that handles how many times "ok" was clicked.
	private int seconds; // a seconds variable is created when the start button is clicked before selecting the type of level. 
	private int levelTime; // variable that takes cares of seconds that are present in each level. 
	private int totalScore; // stores the high Score
		
	// all the instances of the JPanels are created and added to the cardLayout. 
	// the arrays are also initialized in this constructor. 
	public ConstellioPanel()
	{
		cards = new CardLayout();	
		setLayout(cards);
		reader = null;
		constellations = new String[12];
		questions = new String[48];
		answers = new String[48];
		images = new Image[12];
		information = new String[12];
		explanations = new String[48];
		highScores = new String[1000];
		levelsPlayed = new boolean[3];
		questionsAsked = new boolean[48];
		mission = new String("");
		seconds = 60;
		levelTime = 60;
		levelName = new String("Easy");
		okClicked = 0;
		totalScore = 0;
		writer = null;
		
		loadConstellations(); // method to read in the names of the constellations. 
		loadQuestions();  // method to read in questions.
		loadImages(); // method to read in images.
		loadInstructions(); // method to read in instructions.
		loadInformation();  // method to read in information.
		loadGameGuide(); // method to store the game which acts as a guide image.
		loadExplanations(); // method that loads in all the explanations for that particular question.
		loadHighScores(); // loads the highScores file.
		getOkClicked();
		
		homeCard = new HomePanel(cards,this); // the first page
		howToPlayCard = new HowToPlayPanel(cards,this); // the fourth page.
		constellationGuideCard = new ConstellationGuidePanel(cards,this); // the third page.
		levelsCard = new LevelsPanel(cards,this); // the fourth page
		highScoresCard = new HighScoresPanel(cards,this); // the fifth page
		gameCard = new GamePanel(cards,this); // the sixth page
		
		// all panels added to the cardLayout.
		add(homeCard,"Home Panel"); // add first page to firstCard.
		add(howToPlayCard, "How To Play?"); // add second page to secondCard.
		add(constellationGuideCard,"Constellations Guide"); // add third page to thirdCard.
		add(levelsCard, "Levels");  // add fourth page to fourthCard.
		add(highScoresCard,"High Scores");  // add fifth page to fifthCard.
		add(gameCard,"Game");  // add sixth page to sixthCard.		
	}
	
	// getter method to get the array of constellations. 
	public String[] getConstellations()
	{
		return constellations;
	}
	
	// getter method to get the array of questions. 
	public String[] getQuestions()
	{
		return questions;
	}
	
	// getter method to get the array of answers. 
	public String[] getAnswers()
	{
		return answers;	
	}
	
	//getter method for Timer
	public Timer getTimer()
	{
		return timer;
	}
	
	// set the Timer to timer1 when paintComponent is called.
	public void setTimer(Timer timer1)
	{
		timer = timer1;
	}
	
	// returns seconds so that the other classes would be able to access it. 
	public int getSeconds()
	{
		return seconds;
	}
	
	// resets the seconds according to the level.
	public void resetSeconds()
	{
		seconds = levelTime;
	}
	
	// sets the seconds according to the time that is being count down. 
	public void setSeconds(int sec)
	{
		seconds = sec;	
	}
	
	// sets level time to level seconds
	public void setLevelTime(int levelSeconds)
	{
		levelTime = levelSeconds;
	}
	
	// returns the images so that other classes are able to access it. 
	public Image[] getImages()
	{
		return images;
	}
	
	// returns the instructions so that other classes are able to access it. 
	public String getMission()
	{
		return mission;
	}
	
	// returns the information that is shown in the constellationGuidePanel so that 
	// other classes are able to access it. 
	public String[] getInformation()
	{
		return information;
	}
	
	// returns the name of Level depending on the levelTime that is set, so that
	// other classes are able to access it. 
	public String getLevelName() 
	{
		return levelName;
	}
	
	// sets the levelName to name.
	public void setLevelName(String name)
	{
		levelName = name;
	}
	
	// returns the image of the game guide so that other classes would be able to access it. 
	public Image getGameGuide()
	{
		return gameGuide;	
	}
	
	// returns the explanations file, which would later be used to show the explanations for wrong answers.
	public String[] getExplanations()
	{
		return explanations;
	}
	
	// returns the highScores file, so that it could be used in other classes in order to update the high score of the player. 
	public String[] getHighScores()
	{
		return highScores;
	}
	
	// returns okClicked so that it is accessible in other classes.
	public int getOkClicked()
	{
		return okClicked;
	}
	
	// returns the highScoresCard so that other classes are able to access it. 
	public HighScoresPanel getHighScoresCard()
	{
		return highScoresCard;
	}
	
	// increments the amount of time ok was clicked in the Correct and Incorrect frame. 
	public void incrementClicked()
	{
		okClicked++;
	}
	
	// resets the amount to 0 when a new level starts. 
	public void resetClicked()
	{
		okClicked = 0;
	}
	
	// adds the current score to the total score in order to get a high score. 
	public void addToTotal(int score)
	{
		totalScore+= score;
	}
	
	// returns the total score so that other classes are able to access it. 
	public int getTotalScore()
	{
		return totalScore;	
	}
	
	// calls the repaint of the levels Card so that it is reflective of the changes performed. 
	public void updateLevelsCard()
	{
		levelsCard.repaint();	
	}
	
	// returns the questionsAsked variable, which checks if one of the questions has been asked.
	public boolean[] getQuestionsAsked()
	{
		return questionsAsked;	
	}
	
	// checks if the any of the levels is complete and sets it to true. 
	public void completedLevel(String level)
	{
		if(level.equals("Easy"))
		{
			levelsPlayed[0] = true;
		}
		else if(level.equals("Medium"))
		{
			levelsPlayed[1] = true;
		}
		else
		{
			levelsPlayed[2] = true;
		}
	}
	
	// checks if all the levels are played and returns true if all levels are 
	// complete and otherwise returns false. 
	public boolean allLevelsComplete()
	{
		if(levelsPlayed[0] == true && levelsPlayed[1] == true && levelsPlayed[2] == true)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	// returns the boolean true or false depending on which level was 
	// played.
	public boolean alreadyPlayed(String level)
	{
		if(level.equals("Easy"))
		{
			return levelsPlayed[0];
		}
		else if(level.equals("Medium"))
		{
			return levelsPlayed[1];
		}
		else
		{
			return levelsPlayed[2];
		}
	}
	
	// picks a randomLevel when one of the three levels has already been 
	// played. 
	public void pickRandomLevel()
	{
		int level = 0;
		boolean found = false;
		while(found == false)
		{
			level = (int)(Math.random()*3); 
			if(levelsPlayed[level] == false)
			{
				found = true;
			}	
		}
		
		if(level == 0)
		{
			seconds = 60;  
			levelTime = 60; // sets the level Time to 60.
			levelName = "Easy";
		}
		else if (level == 1)
		{
			seconds = 30; 
			levelTime = 30; // sets the level Time to 30.
			levelName = "Medium";
		}
		else
		{
			seconds = 15; 
			levelTime = 15; // sets the level Time to 15.
			levelName = "Hard";
		}
	}
	
	// LoadConstellations reads in the textFiles for the names of the constellations.
	// They are then stored in the constellations array. 
	// Try catch methods are used to open the file and send errors if the file 
	// is unable to open or if there is any other error.
	public void loadConstellations()
	{
		// try-catch block for checking if it's able to read Constellations.txt
		String constellationsFileName = new String("Constellations.txt");
		File constellationFile = new File(constellationsFileName);
	
		try
		{
			reader = new Scanner(constellationFile);
		
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n",constellationsFileName); 
			System.exit(1);
		}
		
		int index = 0;
		while(reader.hasNextLine())
		{
			constellations[index] = reader.nextLine().trim();
			index++;
		
		}
		reader.close();
	}
	
	// LoadQuestions reads in the textFiles for the different types of questions. 
	// They are then stored in the questions and answers array. 
	// The questions are basically the odd number lines like (1,3,5)...
	// The answers are basically the even number lines like (2,4,8).... 
	// When the reader reads in one line it stores it in the question array and 
	// when it reads the next line, it is stored in the answers array. 
	public void loadQuestions()
	{
		// try-catch block for checking if it's able to read QuestionsAndAnswers.txt
		String questionsFileName = new String("QuestionsAndAnswers.txt");
		File questionsFile = new File(questionsFileName);
	
		try
		{
			reader = new Scanner(questionsFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n",questionsFileName); 
			System.exit(1);
		}
		
		int index = 0;
		while(reader.hasNextLine())
		{
			questions[index] = reader.nextLine().trim();
			answers[index] = reader.nextLine().trim();
			index++;
		}
		
		reader.close();
	}
	
	// This method reads in the Images and stores them in an array. 
	// This array is later used to draw the images on the gamePanel as 
	// well as the ConstellationGuidePanel as both of them show the same 
	// images. 
	public void loadImages()
	{
		// try-catch block for checking if it's able to store images in the array.
		
		int index = 0; 
		for(int k = 0; k < constellations.length; k++) // for loop that goes through all the images in the array.
		{
			String pictName = constellations[k] + ".jpg"; // the name is created as by reading in each element of the constellations array + jpg.
			File pictFile = new File(pictName);
			
			try
			{
				images[k] = ImageIO.read(pictFile);
			}
			catch(IOException e)
			{
				System.err.println("\n\n" + pictName + " can't be found. \n\n");
				e.printStackTrace(); 
			}
		}
		
		reader.close();
	}
	
	// loadInstructions reads in the String of the instructions that guide the game play. 
	// It stores the information in the String named "mission".
	public void loadInstructions()
	{
		// try-catch block for reading in the instructions.
		String fileName = new String("Mission.txt"); 
		File missionFile = new File(fileName);
		try
		{
			reader = new Scanner(missionFile);
		}
		
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n",fileName); 
			System.exit(1);
		}
		
		int index = 0;
		while(reader.hasNextLine())
		{
			mission = reader.nextLine().trim();
		}
		
		reader.close();
	}
	
	// loadInformation basically reads in the information about the different types of constellation. 
	// It stores the information in the array named information.	
	public void loadInformation()
	{
		// try-catch block for reading in the information to be presented in the ConstellationGuidePanel
		String nameOfFile = new String("Information.txt");
		File informationFile = new File(nameOfFile);
		try
		{
			reader = new Scanner(informationFile);
			
		}
		
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n",nameOfFile); 
			System.exit(1);	
		}
		
		int index = 0;
		while(reader.hasNextLine())
		{
			information[index] = reader.nextLine().trim();
			index++;
		}
		
		reader.close();	
	}
	
	// loadGameGuide reads in the gameGuide image which is basically a guide for the game play.
	// try-catch blocks are used to catch an IOException if it is unable to read an image.
	public void loadGameGuide()
	{
		String nameOfGuide = new String("GameGuide.png");
		File guideFile = new File(nameOfGuide);
		try
		{
			gameGuide = ImageIO.read(guideFile);	
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + nameOfGuide + " can't be found. \n\n");
			e.printStackTrace();
			
		}
		
		reader.close();
	}
	
	// loads the textFile called high scores which reads in the names and scores of the players.
	// There is a try-catch block which checks whether it is able to open the file or not. 
	public void loadHighScores()
	{
		String nameOfHighScoreFile = new String("HighScores.txt");
		File highScoreFile = new File(nameOfHighScoreFile);
		try
		{
			reader = new Scanner(highScoreFile);
			
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n",nameOfHighScoreFile); 
			System.exit(1);	
		}
		
		int index = 0;
		while(reader.hasNextLine())
		{
			highScores[index] = reader.nextLine().trim();
			index++;
		}
		
		reader.close();
	}
	
	// loads in the textFile which consists of the explanations of the question, justifying why the answer is correct.
	public void loadExplanations()
	{
		String nameOfExplanationsFile = new String("Explanations.txt");
		File explanationsFile = new File(nameOfExplanationsFile);
		try
		{
			reader = new Scanner(explanationsFile);
			
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n",nameOfExplanationsFile); 
			System.exit(1);	
		}
		
		int index = 0;
		while(reader.hasNextLine())
		{
			explanations[index] = reader.nextLine().trim();
			index++;
		}
		
		reader.close();
	}
	
	// This is the method of the printwriter which writes to the file when the player plays all the levels. 
	// It has a try-catch block which checks if it can write to the highScores file or not.
	public void saveHighScores()
	{
		String nameOfHighScoreFile = new String("HighScores.txt");
		File highScoreFile = new File(nameOfHighScoreFile);
	
		try
		{
			writer = new PrintWriter(highScoreFile);
		}
		catch(IOException e)
		{
			System.err.println("Cannot create" + nameOfHighScoreFile + "file to be written to");
			System.exit(1);	
		}
		
		int index = 0;
		while(highScores[index] != null)
		{
			writer.println(highScores[index]);
			index++;
			
		}
		writer.close();
	}	
}
	
// The HomePanel is the first page or the main menu that is shown. 
// It has a null layout. The buttons and Labels are given coordinates so 
// that they would be in that particular place. All of the buttons are directly added 
// to the HomePanel. The Button Listeners are nested inside this class. They have 
// access to the CardLayout as I passed them in from the Constellio1Panel class. 
// The ButtonListeners handle the buttons so that they redirect to the right page. 
// This class also has the paintComponent which has a for loop which continuosly draws circles
// which acts like a design for the home page. 
class HomePanel extends JPanel 
{
	private CardLayout card; // This is the instance of the cardLayout
	private ConstellioPanel cp;  // This is the instance of Constellio4Panel.
		
	// The instance of Constellio4Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them. 
	public HomePanel(CardLayout cards,ConstellioPanel cop)
	{
		cp = cop;
		card = cards;
		setLayout(null); // layout set to null
		setBackground(Color.DARK_GRAY);
			
		// Constellio Label in the Center.
		JLabel name = new JLabel("Constellio", JLabel.CENTER); // label is made.
		name.setFont(new Font("Serif", Font.BOLD, 90));
		name.setBounds(250,100,500,500); // label is added to the location and is given a size.
		add(name);	// Label is added to the Panel.
		
		// Level Button
		JButton level = new JButton("Levels"); // different buttons are created and are set to a particular location.
		HomeButtonHandler hbh = new HomeButtonHandler();
		level.addActionListener(hbh);
		level.setBounds(150,700,100,50);
		
		//How To Play Button
		JButton howtoplay = new JButton("How To Play?");
		howtoplay.addActionListener(hbh);
		howtoplay.setBounds(250,700,100,50);
		
		// High Score Button
		JButton highScoresButton = new JButton("High Scores");
		highScoresButton.addActionListener(hbh);
		highScoresButton.setBounds(350,700,100,50);
		
		// Start Button
		JButton start = new JButton("Start");
		start.addActionListener(hbh);
		start.setBounds(450,700,100,50);
		
		// Quit Button
		JButton quit = new JButton("Quit");
		quit.addActionListener(hbh);
		quit.setBounds(550,700,100,50);
		
		// Constellation Guide button
		JButton constellationsguide = new JButton("<html> <center> Constellations <br>" +
		"Guide </center> </html>"); // used to fit two lines in one button and to center it. 
		constellationsguide.setBounds(650,700,150,50);
		constellationsguide.addActionListener(hbh);
	
		// all the buttons are added to the first Panel so that they could be seen.
		add(level); 
		add(howtoplay);
		add(highScoresButton);
		add(start);
		add(quit);
		add(constellationsguide);							
	}
	
	// PaintComponent draws the design of circles on the screen and takes care 
	// to redraw the changes. 
	public void paintComponent(Graphics g) // paintComponent is called to redraw the changes on the screen after user input.
	{
		super.paintComponent(g); 
		for(int i = 0; i < 1000; i+=20) // nested for loop used to draw circles on the homePanel as a design.
		{
			for(int j = 0; j < 1000; j+=20)
			{
				if((i/20+j/20) % 2 == 0)
				{
					g.setColor(Color.WHITE);
					g.fillOval(i,j,5,5);
				}
			}	
		}		
	}

	// All the buttons present in the HomePanel class are administered in this class. 
	// It checks if a button is clicked and is redirected to that particular page. 
	// If-else blocks are used to check which button is clicked. 
	class HomeButtonHandler implements ActionListener
	{	
		// actionPerformed is used to check for an action event that is occuring. 
		// Inside this method,it checks which button is clicked and then shows 
		// the particular card for the button.			
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand(); 
			if(command.equals("Levels"))
			{
				card.show(cp,"Levels");
			}
			else if(command.equals("Start"))
			{
				if(cp.alreadyPlayed(cp.getLevelName()))
				{
					cp.pickRandomLevel();
				}
				
				card.show(cp,"Game");
				cp.getTimer().start();	// starts the timer as the game started.
			}
			else if(command.equals("How To Play?"))
			{
				card.show(cp,"How To Play?");	
			}
			else if(command.equals("High Scores"))
			{
				card.show(cp,"High Scores");
			}
			else if(command.equals("Quit"))
			{
				cp.saveHighScores();
				System.exit(1);				
			}
			else 
			{
				card.show(cp,"Constellations Guide");
			}
		}	 		
	}						
}
		
// The HowToPlayPanel is the page which is redirected by the How To Play Button in the
// HomePanel. This page has the instructions written down. It has a 
// label in the north in a seperate Panel. Then it has a panel in the south as well as the center.
// This Panel has a BorderLayout, so that each Panel would be situated in that particular direction. 
// The Panel in the north has the text which is later added to the North. The Panel in the south
// contains the buttons so that the user would be able to click them and go to another page if necessary. 
// There is a scrollPane in the center that would have the directions.
class HowToPlayPanel extends JPanel 
{
	private ConstellioPanel cp; // This is the instance of Constellio4Panel.
	private CardLayout card; // This is the instance of the cardLayout

	// The instance of Constellio3Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them. 
	public HowToPlayPanel(CardLayout cards,ConstellioPanel cop)
	{
		cp = cop;
		card = cards;
		// Text in the North.
		setBackground(Color.GREEN);
		setLayout(new BorderLayout());
		JLabel textInNorth = new JLabel("Mission and Instructions");
		textInNorth.setFont(new Font("Serif", Font.BOLD, 20));
		add(textInNorth,BorderLayout.NORTH);
		
		// Panel created for the buttons in the South.
		JPanel buttonsSouth = new JPanel();
		buttonsSouth.setBackground(Color.GREEN);
		buttonsSouth.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		
		JButton main = new JButton("Main Menu");
		HowToPlayButtonHandler htpbh = new HowToPlayButtonHandler();
		main.addActionListener(htpbh);
		JButton start = new JButton("Start");
		start.addActionListener(htpbh);
		JButton constellationsGuide = new JButton("Constellation Guide");
		constellationsGuide.addActionListener(htpbh);
		buttonsSouth.add(main);
		buttonsSouth.add(start); 
		buttonsSouth.add(constellationsGuide);
		add(buttonsSouth, BorderLayout.SOUTH); // the panel is added to the South.
		
		// ScrollPane in the Center.
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GREEN);
		JTextArea jta = new JTextArea(cp.getMission(),20,200);
		jta.setLineWrap(true); 
		jta.setWrapStyleWord(true); 
		jta.setEditable(false);
		JScrollPane scroller = new JScrollPane(jta); 
		scroller.setPreferredSize(new Dimension(500,200));
		centerPanel.add(scroller);
		GuideImage gi = new GuideImage(cp);
		gi.setPreferredSize(new Dimension(500,500));
		centerPanel.add(gi);
		add(centerPanel,BorderLayout.CENTER);			
	}
		
	// All the buttons present in the HowToPlayPanel class are administered in this class. 
	// It checks if a button is clicked and is redirected to that particular page. 
	// If-else blocks are used to check which button is clicked.
	class HowToPlayButtonHandler implements ActionListener
	{
		// actionPerformed is used to check for an action event that is occuring. 
		// Inside this method,it checks which button is clicked and then shows 
		// the particular card for the button.	
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Main Menu")) 
			{
				card.show(cp,"Home Panel");
			}
			else if(command.equals("Start"))
			{
				if(cp.alreadyPlayed(cp.getLevelName()))
				{
					cp.pickRandomLevel();
				}
				
				card.show(cp,"Game");
				cp.getTimer().start();		
			}
			else 
			{
				card.show(cp,"Constellations Guide");		
			}
		}
	}		
}	

// ConstellationGuidePanel has a BorderLayout. 
// It has a label in the North. 
// It has a panel in the south holding some buttons and a Menu.
class ConstellationGuidePanel extends JPanel 
{
	private ConstellioPanel cp; // This is the instance of Constellio4Panel.
	private CardLayout card; // This is the instance of the cardLayout 
	private CardLayout[] infoLayouts; // This is the instance of the CardLayout that is present in the all the JPanels
	// that are created in the array, these JPanels display the constellation and the images. 
	private JPanel[] infoPanels; // This is an array of JPanels that stores the image and the information for the 12 different constellations.
	private JPanel[] panels; // This is an array of JPanels that has the CardLayout and is present inside the GridLayout of the centerPanel.
		
	// The instance of Constellio4Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them. 
	public ConstellationGuidePanel(CardLayout cards,ConstellioPanel cop)
	{
		cp = cop;
		card = cards;

		setLayout(new BorderLayout());
		
		// Label in the North
		JLabel constellationGuide = new JLabel("Constellations Guide");
		setBackground(Color.GRAY);
		constellationGuide.setFont(new Font("Serif", Font.BOLD, 30));
		add(constellationGuide,BorderLayout.NORTH);	
		
		// Panel created in the South.
		JPanel cButtonsSouth = new JPanel();
		cButtonsSouth.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		cButtonsSouth.setBackground(Color.GRAY);
		JButton main = new JButton("Main Menu");
		ConstellationsGuideHandler cgh = new ConstellationsGuideHandler();
		main.addActionListener(cgh); // handlers added to the Button.
		JButton previous = new JButton("Previous");
		previous.addActionListener(cgh); // handlers added to the Button.
		JButton next = new JButton("Next");
		next.addActionListener(cgh);
		JButton start = new JButton("Start"); 
		start.addActionListener(cgh);
		cButtonsSouth.add(main); 
		cButtonsSouth.add(previous);
		cButtonsSouth.add(next);
		cButtonsSouth.add(start);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GRAY);
		centerPanel.setLayout(new GridLayout(1,4,20,20));
		panels = new JPanel[4];  // panels is initialized to 4. 
		infoLayouts = new CardLayout[4]; // cardLayouts is initialized to 4.
		for(int i =0; i < panels.length; i++)
		{
			panels[i] = new JPanel(); // creates 4 new JPanels
			infoLayouts[i] = new CardLayout(); // creates 4 different cardLayouts. 
			panels[i].setLayout(infoLayouts[i]);  // sets the layout to CardLayout for the four panels.
			centerPanel.add(panels[i]);	// all four panels are added to the centerPanel.
		}
		add(centerPanel,BorderLayout.CENTER);  // centerPanel is added to the center. 
		
		infoPanels = new JPanel[12]; // infoPanel is initialized to 12. These Panels hold the information and picture of the various types of constellations. 
		for(int i =0; i < infoPanels.length; i++)
		{
			infoPanels[i] = new JPanel(new GridLayout(2,1)); // sets the layout of the 12 panels to a 2,1 GridLayout.
			panels[i%4].add(infoPanels[i],"Card " + i); // adds the panels to the 4 panels that had cardLayouts previously. 
			JPanel top = new InfoImage(cp,i); // calls the infoImage class to draw the image and passes in cp and i which is the panel it should draw on.
			top.setPreferredSize(new Dimension(235,400));
			infoPanels[i].add(top); // top is added to the top of each of the 12 infoPanels.
			infoPanels[i].setBackground(Color.DARK_GRAY);
			JPanel bottom = new JPanel();
			JTextArea jta = new JTextArea(cp.getInformation()[i],30,100);
			jta.setBackground(Color.LIGHT_GRAY);
			jta.setLineWrap(true); 
			jta.setWrapStyleWord(true); 
			jta.setEditable(false);
			JScrollPane scroller = new JScrollPane(jta); 
			scroller.setPreferredSize(new Dimension(225,250));
			scroller.setBackground(Color.LIGHT_GRAY);
			bottom.add(scroller);
			infoPanels[i].add(bottom); // bottom is added to the bottom of the 12 infoPanels.
			bottom.setBackground(Color.DARK_GRAY);	
		}
			
		// JMenu
		JMenuBar sets = new JMenuBar();
		JMenu choosingSet = new JMenu("Choose Set"); // name of the Menu
		JMenuItem set1 = new JMenuItem("Set 1");
		JMenuItem set2 = new JMenuItem("Set 2");
		JMenuItem set3 = new JMenuItem("Set 3");
		ConstellationGuideMenu cgm = new ConstellationGuideMenu();
		choosingSet.add(set1);
		choosingSet.add(set2);
		choosingSet.add(set3);
		set1.addActionListener(cgm);
		set2.addActionListener(cgm);
		set3.addActionListener(cgm);
		sets.add(choosingSet); // Menu added to the MenuBar
		cButtonsSouth.add(sets); // MenuBar is added to the Panel
		add(cButtonsSouth,BorderLayout.SOUTH); // Whole Panel is added to the South
	}
	
	// actionPerformed is used to check for an action event that is occuring. 
	// Inside this method,it checks which button is clicked and then shows 
	// the particular card for the button.	
	class ConstellationsGuideHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Main Menu"))
			{
				card.show(cp,"Home Panel");
			}
			else if(command.equals("Start"))
			{
				if(cp.alreadyPlayed(cp.getLevelName()))
				{
					cp.pickRandomLevel();
				}
				
				card.show(cp,"Game");
				cp.getTimer().start();		
			}
			else if(command.equals("Previous"))
			{
				for(int i =0; i < infoLayouts.length; i++)
				{
					infoLayouts[i].previous(panels[i]);
				}
			}
			else 
			{
				for(int i =0; i < infoLayouts.length; i++)
				{
					infoLayouts[i].next(panels[i]);
				}
			}
		}		
	}
	
	// Checks which set of the Menu was selected and shows that 
	// particular set of constellations out of the 3 sets that 
	// were created. 
	class ConstellationGuideMenu implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Set 1"))
			{
				for(int i =0; i < infoLayouts.length; i++)
				{
					infoLayouts[i].first(panels[i]);  // shows the first set of the constellations. 
				}
			}
			else if(command.equals("Set 2"))
			{
				for(int i =0; i < infoLayouts.length; i++)
				{
					infoLayouts[i].first(panels[i]);// both of these work together, 
					// if set 2 is selected then it immediately switches from first to next and hence the second panel is shown.
					// two functions are performed at one to obtain the result. 
					infoLayouts[i].next(panels[i]);
				}
			}
			else 
			{
				for(int i =0; i < infoLayouts.length; i++)
				{
					infoLayouts[i].last(panels[i]); // shows the last set of the constellations present in the Menu.
				}
			}	
		}	
	}		
}

// This class shows the various levels that are present. It presently, has a borderLayout. 
// The buttons are in the east, and the center is completely left to show the various types 
// of levels. 		
class LevelsPanel extends JPanel 
{
	private ConstellioPanel cp;  // This is the instance of Constellio4Panel.
	private CardLayout card; // This is the instance of the cardLayout 
	private ButtonGroup bg; // buttongroup thast holds the three buttons.
	private JRadioButton level1, level2, level3; // three different radioButtons for the levels.
	
	// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public LevelsPanel(CardLayout cards,ConstellioPanel cop)
	{
		cp = cop;
		card = cards;
				
		setLayout(new BorderLayout());
		setBackground(Color.ORANGE);
		
		// Panel created in the East.
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(200,300));
		eastPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,100));
		JButton main = new JButton("Main Menu");
		LevelsButtonHandler lbh = new LevelsButtonHandler();
		main.addActionListener(lbh); // handlers added to the Button.
		JButton start = new JButton("Start");
		start.addActionListener(lbh);
		JButton quit = new JButton("Quit");
		quit.addActionListener(lbh);
	
		// Label in the North.
		JLabel nameOfLevel = new JLabel("Levels");
		nameOfLevel.setFont(new Font("Serif", Font.BOLD, 20));
		add(nameOfLevel,BorderLayout.NORTH); // label added to the North.
		eastPanel.setBackground(Color.ORANGE);
		
		// All the Buttons added to the Panel
		eastPanel.add(main);
		eastPanel.add(start);
		eastPanel.add(quit);
		add(eastPanel,BorderLayout.EAST); // Panel is added to the East. 
		
		// Center Panel
		JPanel centerPanel = new JPanel(); 
		centerPanel.setBackground(Color.ORANGE);
		JPanel column1 = new JPanel();
		column1.setBackground(Color.ORANGE);
		column1.setLayout(new GridLayout(6,0,10,70));
		JPanel column2 = new JPanel();
		column2.setBackground(Color.ORANGE);
		column2.setLayout(new GridLayout(6,0,10,70));
		JPanel column3 = new JPanel();
		column3.setBackground(Color.ORANGE);
		column3.setLayout(new GridLayout(6,0,10,70));
		centerPanel.setLayout(new BorderLayout(100,0));
		
		// Column 1
		JLabel number1 = new JLabel("1");
		number1.setFont(new Font("Times New Roman", Font.BOLD, 50));
		column1.add(number1);
		bg = new ButtonGroup();
		RadioButtonHandler rbh = new RadioButtonHandler(); 
		level1 = new JRadioButton("Easy");
		level1.setFont(new Font("Serif", Font.BOLD, 20));
		bg.add(level1);					// add button to panel	
		column1.add(level1);	// add JRadioButton to Panel
		level1.addActionListener(rbh);
		JLabel givenQuestions1 = new JLabel("4 questions");
		givenQuestions1.setFont(new Font("Serif", Font.BOLD, 20));
		column1.add(givenQuestions1);
		JLabel givenTime1 = new JLabel("60 seconds/question");
		givenTime1.setFont(new Font("Serif", Font.BOLD, 20));
		column1.add(givenTime1);
		JLabel givenLives1 = new JLabel("3 Lives");
		givenLives1.setFont(new Font("Serif", Font.BOLD, 20));
		column1.add(givenLives1);
		JLabel givenScore1 = new JLabel("5 points");
		givenScore1.setFont(new Font("Serif", Font.BOLD, 20));
		column1.add(givenScore1);
		
		// Column 2
		JLabel number2 = new JLabel("2");
		number2.setFont(new Font("Times New Roman", Font.BOLD, 50));
		column2.add(number2);
		level2 = new JRadioButton("Medium");
		level2.setFont(new Font("Serif", Font.BOLD, 20));
		bg.add(level2);					// add button to panel	
		column2.add(level2);	// add JRadioButton to Panel
		level2.addActionListener(rbh);
		JLabel givenQuestions2 = new JLabel("4 questions");
		givenQuestions2.setFont(new Font("Serif", Font.BOLD, 20));
		column2.add(givenQuestions2);
		JLabel givenTime2 = new JLabel("30 seconds/question");
		givenTime2.setFont(new Font("Serif", Font.BOLD, 20));
		column2.add(givenTime2);
		JLabel givenLives2 = new JLabel("3 Lives");
		givenLives2.setFont(new Font("Serif", Font.BOLD, 20));
		column2.add(givenLives2);
		JLabel givenScore2 = new JLabel("5 points");
		givenScore2.setFont(new Font("Serif", Font.BOLD, 20));
		column2.add(givenScore2);
		
		// Column 3
		JLabel number3 = new JLabel("3");
		number3.setFont(new Font("Times New Roman", Font.BOLD, 50));
		column3.add(number3);
		level3 = new JRadioButton("Hard");
		level3.setFont(new Font("Serif", Font.BOLD, 20));
		bg.add(level3);					// add button to panel	
		column3.add(level3);	// add JRadioButton to Panel
		level3.addActionListener(rbh);
		JLabel givenQuestions3 = new JLabel("4 questions");
		givenQuestions3.setFont(new Font("Serif", Font.BOLD, 20));
		column3.add(givenQuestions3);
		JLabel givenTime3 = new JLabel("15 seconds/question");
		givenTime3.setFont(new Font("Serif", Font.BOLD, 20));
		column3.add(givenTime3);
		JLabel givenLives3 = new JLabel("3 Lives");
		givenLives3.setFont(new Font("Serif", Font.BOLD, 20));
		column3.add(givenLives3);
		JLabel givenScore3 = new JLabel("5 points");
		givenScore3.setFont(new Font("Serif", Font.BOLD, 20));
		column3.add(givenScore3);
		
		// Add columns to center panel
		centerPanel.add(column2,BorderLayout.CENTER);
		centerPanel.add(column1,BorderLayout.WEST);
		centerPanel.add(column3,BorderLayout.EAST);
		add(centerPanel,BorderLayout.CENTER); // Panel is added to the center.
	}
		
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(cp.alreadyPlayed("Easy"))
		{
			level1.setEnabled(false);
		}
		if(cp.alreadyPlayed("Medium"))
		{
			level2.setEnabled(false);
		}
		if(cp.alreadyPlayed("Hard"))
		{
			level3.setEnabled(false);
		}
	}
	
	// actionPerformed is used to check for an action event that is occuring. 
	// Inside this method,it checks which button is clicked and then shows 
	// the particular card for the button.
	class LevelsButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Main Menu"))
			{
				card.show(cp,"Home Panel");
				
			}
			else if(command.equals("Start"))
			{
				if(cp.alreadyPlayed(cp.getLevelName()))
				{
					cp.pickRandomLevel();
				}
				card.show(cp,"Game");
				cp.getTimer().start();		
			}
			else 
			{
				System.exit(1);	
			}
		}						
	}
	
	// checks which of the following radioButtons was clicked 
	// and sets the timer and LevelName accordingly. 
	class RadioButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Easy"))
			{
				
				cp.setSeconds(60);
				cp.setLevelTime(60);
				cp.setLevelName("Easy");
			}
			else if(command.equals("Medium"))
			{
				cp.setSeconds(30);
				cp.setLevelTime(30);
				cp.setLevelName("Medium");
			}
			else 
			{
				cp.setSeconds(15);
				cp.setLevelTime(15);
				cp.setLevelName("Hard");
			}
		}
	}
}

// This panel shows the high scores of the players who played the game. 
// This panel has a border layout, where the text is in the north and the 
// buttons are in the south. 		
class HighScoresPanel extends JPanel 
{
	private ConstellioPanel cp; 
	private CardLayout card;
	private JTextArea highScoresList;
	
	// The instance of Constellio4Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public HighScoresPanel(CardLayout cards,ConstellioPanel cop)
	{
		cp = cop;
		card = cards;

		setLayout(new BorderLayout());
		
		// The Label is added to the North.
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.BLUE);
		JLabel textInNorth = new JLabel("These players are on our high score list!",JLabel.CENTER);
		textInNorth.setFont(new Font("Serif", Font.BOLD, 20));
		northPanel.add(textInNorth);
		add(northPanel,BorderLayout.NORTH);
		
		// The Panel created in the South.
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.BLUE);
		JButton quit = new JButton("Quit"); // Buttons are created.
		HighScoresButtonHandler hsbh = new HighScoresButtonHandler();
		quit.addActionListener(hsbh); // adds the listener to the button.
		JButton main = new JButton("Main Menu");
		main.addActionListener(hsbh); // adds the listener to the button.
		// The buttons are added to the panel.
		southPanel.add(quit);
		southPanel.add(main);
		add(southPanel,BorderLayout.SOUTH); // panel is added to the South.
		
		// leftPanel
		String highScoreText = new String(""); // stores the text while reading through the file.
		int index = 0;
		while(cp.getHighScores()[index] != null)
		{
			String current = cp.getHighScores()[index]; // stores the line that is currently being read. 
			highScoreText += (index + 1) + ". "; // creates a string which is the index and the number to create the list of the players. 
			highScoreText += current.substring(0,current.lastIndexOf(",")) + " "; // gets the name of the player which is before the ","
			highScoreText += current.substring(current.lastIndexOf(",") + 1) + "\n\n"; // gets the score of the player which is an index after the ","
			index++; // increments the index to read further lines.
		}
		highScoresList = new JTextArea(highScoreText); // writes in the text inside the textArea after reading the whole file.
		highScoresList.setBackground(Color.BLUE); 
		highScoresList.setFont(new Font("Serif", Font.BOLD,30)); 
		highScoresList.setEditable(false);
		JScrollPane highScroller = new JScrollPane(highScoresList); 
		highScroller.setPreferredSize(new Dimension(500,600));
		add(highScroller,BorderLayout.WEST);
		
		// right Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,100));
		rightPanel.setBackground(Color.BLUE);
		rightPanel.setPreferredSize(new Dimension(500,800));
		String complimentText = new String("These players charged like a Taurus in this game!");
		JTextArea compliment = new JTextArea(complimentText,3,20);
		compliment.setFont(new Font("Serif", Font.BOLD, 20));
		compliment.setBackground(Color.BLUE);
		compliment.setLineWrap(true); 
		compliment.setWrapStyleWord(true); 
		compliment.setEditable(false); 
		TaurusImagePanel tip = new TaurusImagePanel(cp); // calls Taurus Image Panel to draw the taurus image
		tip.setPreferredSize(new Dimension(400,400)); // sets the size of the panel so that the image could be drawn on it.
		rightPanel.add(tip); // adds the image to the rightPanel
		rightPanel.add(compliment); // also adds the text to the rightPanel
		add(rightPanel,BorderLayout.CENTER); // the right panel is added to the center.
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		String highScoreText = new String(""); // stores the text while reading through the file.
		int index = 0;
		while(cp.getHighScores()[index] != null)
		{
			String current = cp.getHighScores()[index]; // stores the line that is currently being read. 
			highScoreText += (index + 1) + ". "; // creates a string which is the index and the number to create the list of the players. 
			highScoreText += current.substring(0,current.lastIndexOf(",")) + " "; // gets the name of the player which is before the ","
			highScoreText += current.substring(current.lastIndexOf(",") + 1) + "\n\n"; // gets the score of the player which is an index after the ","
			index++; // increments the index to read further lines.
		}
		
		highScoresList.setText(highScoreText);	
	}
	
	// actionPerformed is used to check for an action event that is occuring. 
	// Inside this method,it checks which button is clicked and then shows 
	// the particular card for the button.
	class HighScoresButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			// checks if MainMenu or quit button was clicked. 
			if(command.equals("Main Menu") && cp.allLevelsComplete() == false) 
			{
				card.show(cp,"Home Panel");	
			}
			else 
			{
				cp.saveHighScores();
				System.exit(1);	
			}		
		}	
	}
}

// This panel is the most important panel as it manages the work of the game. 
// This panel also has a timer that manages the time in each level. 
// The game Panel has a borderLayout to add the buttons to the south and 
// the text in the North. Questions are displayed using JTextArea and the 
// answer is written down using a JTextField. An Image of the constellation 
// is also displayed so that the user knows which constellation the game is 
// referring to. It also has a right and left panel which have components
// that help the user while playing the game. There is a slider in the left 
// panel that increases the size of the image by zooming in and also zooming out. 
// The right Panel has the questions, image and the textfield. 
class GamePanel extends JPanel
{
	private ConstellioPanel cp; // the instance of the Constellio4Panel class.
	private CardLayout card; // the instance of the CardLayout.
	private JSlider imageSize; // the instance of the slider which changes the size of the image.
	private JLabel countDown; // the label that displays the time running out in each level.
	private ImagePanel imagePanel; // the instance of the image Panel class which draws the image for a particular question.
	private JLabel levelType; // shows the level that the user is currently playing.
	private JTextField answer; // the area where the user writes down the answer.
	private JTextArea question; // the textArea which displays the question.
	private JLabel livesLabel; // displays the lives of the player.
	private JLabel scoreLabel; // keeps track of the scores in each level.
	private int lives; // the number which takes care of the lives, and is decremented when a question is missed or unanswered.
	private int levelScore; // stores the score that the player gets after playing each level.
	private int height; // the height of the original image.
	private int width; // the width of the original image.
	private int image; // the value of the slider which is added to the original height and width.
	private int questionNumber; // stores the index of the questionNumber is displayed.
	
	// The instance of Constellio4Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public GamePanel(CardLayout cards,ConstellioPanel cop)
	{
		cp = cop;
		card = cards;
		countDown = new JLabel("Timer : " + cp.getSeconds()); // Creates the label timer which shows the number of seconds.
		countDown.setFont(new Font("Serif", Font.BOLD, 20));
		height = 300; 
		width = 500;
		pickQuestion();
		image = questionNumber%12; 
		lives = 3;
		levelScore = 0;

		TimerHandler th = new TimerHandler();
		
		if(cp.getTimer() == null)
		{
			// sets the timer to 1000 milliseconds and passes in the instance of the Timer Handler class
			cp.setTimer(new Timer(1000,th)); 
		}
		
		// Label in the North.
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.PINK);
		northPanel.setLayout(new BorderLayout());
		JLabel gameLabel = new JLabel("Game",JLabel.CENTER);
		gameLabel.setFont(new Font("Serif", Font.BOLD, 30));
		livesLabel = new JLabel("Lives: " + lives);
		livesLabel.setFont(new Font("Serif", Font.BOLD, 20));
		northPanel.add(livesLabel,BorderLayout.WEST);
		northPanel.add(gameLabel,BorderLayout.CENTER);
		northPanel.add(countDown,BorderLayout.EAST);
		add(northPanel,BorderLayout.NORTH);
		
		// ButtonsPanel to hold the buttons.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.PINK);
		JButton quit = new JButton("Quit"); // quit button
		GameButtonHandler gbh = new GameButtonHandler();
		quit.addActionListener(gbh ); // adds the listener to the button.
		JButton main = new JButton("Main Menu");// main menu button
		main.addActionListener(gbh); // adds the listener to the button.
		buttonPanel.add(quit);
		buttonPanel.add(main);
		
		// SouthPanel to store buttons and Label	
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.PINK);
		southPanel.setLayout(new BorderLayout());
		levelType = new JLabel("Level: " + cp.getLevelName());
		levelType.setFont(new Font("Serif", Font.BOLD, 20));
		scoreLabel = new JLabel("Score: " + levelScore,JLabel.CENTER);
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 20)); 
		southPanel.add(levelType,BorderLayout.WEST); // levelType Label added to west of the panel.
		southPanel.add(buttonPanel,BorderLayout.EAST); // the buttonPanel added to the east of the panel.
		southPanel.add(scoreLabel,BorderLayout.CENTER);  // score is added to the center of the panels
		add(southPanel,BorderLayout.SOUTH); // the whole southPanel added to the South.
		
		// leftPanel
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.PINK);
		leftPanel.setPreferredSize(new Dimension(250,250));
		imageSize = new JSlider(0, 20, 5);
		imageSize.setValue(0);
		imageSize.setMajorTickSpacing(5);// create tick marks on slider every 5 units
		imageSize.setPaintTicks(true);
		imageSize.setLabelTable(imageSize.createStandardLabels(5) ); // create labels on tick marks
		imageSize.setPaintLabels(true);
		imageSize.setOrientation(JSlider.HORIZONTAL);
		SliderListener slistener = new SliderListener();
		imageSize.addChangeListener(slistener);
		leftPanel.add(imageSize);
		String textAreaInfo = new String("Use the slider to make image bigger or smaller. Write your answers in the textField under the question.");
		JTextArea info = new JTextArea(textAreaInfo,3,20);
		info.setBackground(Color.PINK);
		info.setLineWrap(true); 
		info.setWrapStyleWord(true); 
		info.setEditable(false);
		leftPanel.add(info);
		
		// right Panel
		JPanel rightPanel = new JPanel();
		imagePanel = new ImagePanel(cp,width,height,image);
		imagePanel.setPreferredSize(new Dimension(width,height));
		rightPanel.add(imagePanel);
		rightPanel.setBackground(Color.PINK);
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,50));
		question = new JTextArea(cp.getQuestions()[questionNumber],3,45); // gets the question which was randomly created previously and shows the question at that particular index.
		question.setBackground(Color.PINK);
		question.setLineWrap(true); 
		question.setWrapStyleWord(true); 
		question.setEditable(false);
		answer = new JTextField("Put your answer here",20);
		rightPanel.add(question);
		rightPanel.add(answer);
		JButton submit = new JButton("SUBMIT");
		submit.addActionListener(gbh);
		rightPanel.add(submit);
		
		// centerPanel
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.PINK);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(rightPanel,BorderLayout.CENTER);
		centerPanel.add(leftPanel,BorderLayout.WEST);
		add(centerPanel,BorderLayout.CENTER); // centerPanel is added to the center. 
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		countDown.setText("Timer : " + cp.getSeconds()); // sets the text when the countDown starts.
		levelType.setText("Level: " + cp.getLevelName()); // sets the text to the particular level that is being played.
		scoreLabel.setText("Score: " + levelScore); // changes the text of the score accordingly.
		question.setText(cp.getQuestions()[questionNumber]); 
		imagePanel.switchImage(questionNumber%12); // switches the image according to the next question.
		livesLabel.setText("Lives: " + lives); // changes the lives if the player loses a life/lives.
		imagePanel.repaint();
	}
	
	// creates a random question, and sets the boolean to true, so that 
	// the questions never repeat again.
	public void pickQuestion()
	{
		boolean found = false; 
		while(found == false)
		{
			questionNumber = (int)(Math.random()*cp.getQuestions().length); // uses Math.Random to randomly choose a question.
			if(cp.getQuestionsAsked()[questionNumber] == false)
			{
				found = true;
				cp.getQuestionsAsked()[questionNumber] = true;
			}
		}
	}
	
	// actionPerformed is used to check for an action event that is occuring. 
	// Inside this method,it checks which button is clicked and then shows 
	// the particular card for the button. It also calls the classes to show the pop-up
	// frame whether the answer was correct or wrong.
	class GameButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			// checks if any of the buttons were clicked and redirects to that particular page. 
			if(command.equals("Main Menu"))
			{
				finishLevel();
				card.show(cp,"Home Panel");
				cp.getTimer().stop();	
			}
			else if(command.equals("SUBMIT"))
			{
				if(cp.getAnswers()[questionNumber].equalsIgnoreCase(answer.getText()))
				{
					levelScore+=5; // increments the score to 5 points when an answer is correct.
					CorrectFrame cf = new CorrectFrame(cp);	// calls the correct frame class to display the pop-up frame when the answer is correct.
				}
				else 
				{
					IncorrectFrame inf = new IncorrectFrame(cp,questionNumber); 	// calls the incorrect frame class to display the pop-up frame when the answer is incorrect/unanswered.
					
					// Check if out of lives
					if(lives > 1 && lives <= 3)
					{
						lives--; // decrements lives when the answer is wrong.
					}
					else 
					{
						finishLevel();
					}
							
				}
				
				// Check for questions attempted
				if(cp.getOkClicked() == 3)
				{
					finishLevel();
				}	
			}
			else 
			{
				cp.saveHighScores(); // saves the high score after playing the levels.
				System.exit(1);		
			}
			
			// Check if all levels finished
			if(cp.allLevelsComplete())
			{
				HighScoreNameFrame nameFrame = new HighScoreNameFrame(cp);
				card.show(cp,"High Scores");
			}
			
			resetAnswer();
		}
	}
		
	// This class takes care of the countdown when each level is played. 
	// It decrements the seconds to 0.
	class TimerHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		 {
			 if(cp.getSeconds() > 0)
			 {
				 cp.setSeconds(cp.getSeconds()-1); // subtracts a second in order to begin countdown.
				 repaint(); // calls repaint to change the seconds in the label.
			 }
			 else 
			 {
				IncorrectFrame inf = new IncorrectFrame(cp,questionNumber); // calls the incorrect frame class to display the pop-up frame when the answer is incorrect/unanswered.
					
				// Checking lives
				if(lives > 1 && lives <= 3)
				{
					lives--; // decrements lives when the answer is wrong.
				}
				else 
				{
					finishLevel();
				}
				
				// Checking questions attempted
				if(cp.getOkClicked() == 3)
				{
					finishLevel();
				}	
				
				resetAnswer();
			 } 	 
		}
	}
	
	// resets everything in that particular level. 
	// It also resets the textField to be the original text
	// which does not contain the answer that was given by the player. 
	public void resetAnswer()
	{
		pickQuestion();
		answer.setText("Put your answer here");
		cp.getTimer().stop();
		cp.resetSeconds();
		repaint(); 
	}
	
	// this finishes the level automatically when 
	// main menu was clicked, lives were lost, or the time 
	// ran out. It adds the score to the total high score 
	// which needs to be displayed later in the highScores panel. 
	// It later redirects to the level panel if the level ended and 
	// the player needs to choose another level to start from. 
	public void finishLevel()
	{
		cp.addToTotal(levelScore);
		levelScore = 0;
		cp.completedLevel(cp.getLevelName());
		lives = 3; // sets the lives default as 3.
		cp.updateLevelsCard();
		card.show(cp,"Levels");	
		cp.resetClicked();
	}
			
	// This classes handles the amount that the slider has moved and accordingly changes the 
	// size of the image. 								
	class SliderListener implements ChangeListener
	{
		public void stateChanged (ChangeEvent evt) 
		{
			int val = imageSize.getValue()*5;	// gets the value of the slider and multiplies it by 5 so that the change in the amount of pixels of the image is high.
			imagePanel.resizeImage(width + val,height + val); // changes the width and height accordingly after the slider changes. 
			imagePanel.setPreferredSize(new Dimension(width + val, height + val)); // sets the imagePanel to a new Dimension so that it doesn't overlap with the questions.
			repaint(); // calls repaint to change it to the slider's value.
		}
	}
}
	
// This class draws the image on the GamePanel. 
// It gets the values from other classes such as GamePanel which provides the
// value of slider so that the height and width of the image changes according 
// to the size of the slider. 
class ImagePanel extends JPanel
{
	private ConstellioPanel cp; // instance of the Constellio4Panel.
	private int widthOfImages; // width of the original image
	private int heightOfImages; // height of the original image
	private int imageNumber; // the index of the image that is being displayed.
	
	public ImagePanel(ConstellioPanel cop,int width,int height,int image)
	{
		cp = cop;
		widthOfImages = width; 
		heightOfImages = height;
		imageNumber = image;
	}
	
	// sets the width and height of the image according to the changes performed in the slider.
	public void resizeImage(int width, int height)
	{
		widthOfImages = width;
		heightOfImages = height;	
	}
	
	// switches the image to a different one after the question has been done/or time is up.
	public void switchImage(int imageNum)
	{
		imageNumber = imageNum;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		// displays the image with the correct width and height after the changes with the slider.
		g.drawImage(cp.getImages()[imageNumber],0,0,widthOfImages,heightOfImages,this);  
	}
}	

// Draws the images on the ConstellationGuidePanel class.
class InfoImage extends JPanel
{
	private ConstellioPanel cp;   // instance of Constellio4Panel
	private int imageNumber; //index of the image that is being displayed.
	
	public InfoImage(ConstellioPanel cop,int image)
	{
		setBackground(Color.DARK_GRAY);
		cp = cop;
		imageNumber = image;
	}

	// draws all the images at the top of the infoImage Panel inside the ConstellationGuidePanel class.
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(cp.getImages()[imageNumber],0,50,235,250,this); 
	}
	
}	

// draws the guiding Image in the how to play panel, which shows an image of the 
// game panel before the user plays the game.
class GuideImage extends JPanel
{
	private ConstellioPanel cp;  // instance of Constellio4Panel
	
	public GuideImage(ConstellioPanel cop)
	{
		cp = cop;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(cp.getGameGuide(),0,0,500,500,this);
	}
		
}

// Displays a pop-up window if the answer is correct and has a button ok, 
// which when clicked restarts the timer again for the next question.
class CorrectFrame extends JFrame
{
	private ConstellioPanel cp; // instance of Constellio4Panel
	
	public CorrectFrame(ConstellioPanel cop)
	{
		cp = cop;
		setTitle("Correct");  // sets the title of the frame to correct
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GREEN);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
		JLabel label = new JLabel("CORRECT!",JLabel.CENTER);
		label.setFont(new Font("Serif", Font.BOLD, 20));
		JPanel southPanel = new JPanel(); 
		southPanel.setBackground(Color.GREEN);
		JButton okButton = new JButton("OK");
		ButtonHandler bh = new ButtonHandler(this);
		okButton.addActionListener(bh);
		centerPanel.add(label);
		add(centerPanel,BorderLayout.CENTER);
		southPanel.add(okButton);
		add(southPanel,BorderLayout.SOUTH);
		setSize(500,500);
		setLocation(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	// checks if the user has clicked "ok" to move on.
	class ButtonHandler implements ActionListener
	{
		private CorrectFrame cf;
		
		public ButtonHandler(CorrectFrame frame)
		{
			cf = frame;
		}
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("OK"))
			{
				cp.getTimer().start(); 
				cp.incrementClicked();
				cf.dispose(); // closes the frame.
			}
		}
	}	
}


// Displays a pop-up window if the answer is incorrect and also displays the explanation
// It has a button ok, which when clicked reduces the lives, and continues to the next question.
class IncorrectFrame extends JFrame
{
	private ConstellioPanel cp;  // instance of Constellio4Panel
	
	public IncorrectFrame(ConstellioPanel cop,int qNum)
	{
		setTitle("Incorrect"); // sets the title of the frame to incorrect
		cp = cop;
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.RED);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
		JLabel label = new JLabel("INCORRECT",JLabel.CENTER);
		label.setFont(new Font("Serif", Font.BOLD, 20));
		String text = "The correct answer is " + cp.getAnswers()[qNum] + " because " + cp.getExplanations()[qNum] ;
		JTextArea jta = new JTextArea(text,3,30);
		jta.setBackground(Color.RED);
		jta.setPreferredSize(new Dimension(500,300));
		jta.setLineWrap(true); 
		jta.setWrapStyleWord(true); 
		jta.setEditable(false);
		centerPanel.add(jta);
		JPanel southPanel = new JPanel(); 
		southPanel.setBackground(Color.RED);
		JButton okButton = new JButton("OK");
		ButtonHandler bh = new ButtonHandler(this);
		okButton.addActionListener(bh);
		centerPanel.add(label);
		add(centerPanel,BorderLayout.CENTER);
		southPanel.add(okButton);
		add(southPanel,BorderLayout.SOUTH);
		setSize(500,500);
		setLocation(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	// checks if the user has clicked "ok" to move on.
	class ButtonHandler implements ActionListener
	{
		private IncorrectFrame inf; 
		
		public ButtonHandler(IncorrectFrame frame)
		{
			inf = frame;
		}
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("OK"))
			{
				cp.getTimer().start();
				cp.incrementClicked();
				inf.dispose(); // closes the frame.
			}
		}	
	}
} 

// Draws the image of Taurus in the HighScoresPanel
class TaurusImagePanel extends JPanel
{
	private ConstellioPanel cp; // instance of Constellio4Panel
	
	public TaurusImagePanel(ConstellioPanel cop)
	{
		setBackground(Color.DARK_GRAY);
		cp = cop;	
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(cp.getImages()[1],0,0,400,400,this); 
	}
	
}

// Displays a pop-up window for the user to enter their name 
// at the end of each game.
class HighScoreNameFrame extends JFrame
{
	private ConstellioPanel cp;  // instance of Constellio4Panel
	private JTextField playerName;
	
	public HighScoreNameFrame(ConstellioPanel cop)
	{
		setTitle("Name"); // sets the title of the frame to incorrect
		cp = cop;
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.CYAN);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
		JLabel label = new JLabel("Congrats your score is : " + cp.getTotalScore(), JLabel.CENTER);
		label.setFont(new Font("Serif", Font.BOLD, 20));
		centerPanel.add(label);
		playerName = new JTextField("Enter your Name",20);
		centerPanel.add(playerName);
		JPanel southPanel = new JPanel(); 
		southPanel.setBackground(Color.CYAN);
		JButton okButton = new JButton("OK");
		ButtonHandler bh = new ButtonHandler(this);
		okButton.addActionListener(bh);

		add(centerPanel,BorderLayout.CENTER);
		southPanel.add(okButton);
		add(southPanel,BorderLayout.SOUTH);
		setSize(500,500);
		setLocation(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	// adds the highScore to the new array as a new element. 
	// It also replaces the higher score with the lower score that 
	// was present in the highScores file. 
	public void addHighScore()
	{
		String name = playerName.getText().trim();
		int score = cp.getTotalScore();
		String entry = name + "," + score;
		boolean added = false;
		int index = 0;
		while(added == false)
		{
			String current = cp.getHighScores()[index];
			if(current == null)
			{
				cp.getHighScores()[index] = entry;
				added = true;
			}
			else if(Integer.parseInt(current.substring(current.lastIndexOf(",")+1)) < score)
			{
				for (int i = cp.getHighScores().length-1; i > index; i--)
				{
					cp.getHighScores()[i] = cp.getHighScores()[i-1]; // replaces the current score with the higher score. 
				}
				
				cp.getHighScores()[index] = entry;
				added = true;
			}
			
			index++;	
		}
		
		cp.getHighScoresCard().repaint();
	}
	
	// checks if the user has clicked "ok" to move on.
	class ButtonHandler implements ActionListener
	{
		private HighScoreNameFrame hsnf; 
		
		public ButtonHandler(HighScoreNameFrame frame)
		{
			hsnf = frame;
		}
		
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("OK"))
			{
				addHighScore();
				hsnf.dispose(); // closes the frame.
			}
		}
		
	}
	
} 
