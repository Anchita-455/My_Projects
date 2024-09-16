// Anchita Dash
// Constellio2.java
// 04/19/2021


	// all the imports
	import java.awt.Graphics;
	import java.awt.Color;
	import java.awt.Font;
	import java.awt.GridLayout;
	import java.awt.Image;
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
	
	
	
// This class makes the frame and calls another class Constellio2Panel which handles the 
// job done in the game. The instance of Constellio2Panel is also created and added to the frame. 
public class Constellio2
{
	// creates an instance of the class itself and calls the method makingFrame. 
	public static void main(String args[])
	{
		Constellio2 c2 = new Constellio2();
		c2.makingFrame();
		
	}
	
	// This method is responsible to create the frame labeled "Constellio2"
	// It sets the size and location of the frame. 
	// This method also creates an instance of the Constellio2Panel class whose 
	// contents are added to the frame.
	public void makingFrame()
	{
		JFrame frame = new JFrame("Constellio 2"); // makes the frame named Constellio2
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocation(10,100);
		
		Constellio2Panel c2p = new Constellio2Panel();
		frame.getContentPane().add(c2p);
		frame.setVisible(true);
		
	}
}
	
// This class creates the cardLayout and all the cards(JPanels) are added to this CardLayout
// which is later shown. This class also initializes the arrays by reading the textfiles. 
// Scanner is used to read the text files and alternative lines are stored in the array. 
// For example 1stLine is the question and the 2nd Line is the answer. It also reads the 
// name of the constellations which is stored in another textFile. The Images are also 
// stored in the image array which stores the different images of the constellations.
class Constellio2Panel extends JPanel
{
	// all field variables declared
	private CardLayout cards;
	private JPanel homeCard;
	
	private String[] constellations;
	private String[] questions;
	private String[] answers;
	private Image[] images;
	private String[] information;
	private Timer timer;
	private int seconds;
	private int levelTime;
	private String mission;
		
		
	// all the instances of the JPanels are created and added to the cardLayout. 
	// the arrays are also initialized in this constructor. 
	public Constellio2Panel()
	{
		
		cards = new CardLayout();	
		setLayout(cards);
		constellations = new String[12];
		questions = new String[48];
		answers = new String[48];
		images = new Image[12];
		information = new String[1000];
		seconds = 60;
		levelTime = 60;
		
		loadFiles(); // method load files is called to read files.
		
		HomePanel homeCard = new HomePanel(cards,this); // the first page
		HowToPlayPanel howToPlayCard = new HowToPlayPanel(cards,this); // the fourth page.
		ConstellationGuidePanel constellationGuideCard = new ConstellationGuidePanel(cards,this); // the third page.
		LevelsPanel levelsCard = new LevelsPanel(cards,this); // the fourth page
		HighScoresPanel highScoresCard = new HighScoresPanel(cards,this); // the fifth page
		GamePanel gameCard = new GamePanel(cards,this); // the sixth page
		
		
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
	
	// returns the instructiins so that other classes are able to access it. 
	public String getMission()
	{
		return mission;
	}
	
	public String[] getInformation()
	{
		return information;
	}
	
	// LoadFiles reads in the textFiles for the questions, answers and the constellations. 
	// They are then stored in the respective arrays. 
	// Try catch methods are used to open the file and send errors if the file 
	// is unable to open or if there is any other error.
	public void loadFiles()
	{
		Scanner reader = null;
		File constellationFile = new File("Constellations.txt");
	
		// try-catch block for checking if it's able to read Constellations.txt
		try
		{
			reader = new Scanner(constellationFile);
		
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n","Constellations.txt"); 
			System.exit(1);
		}
		
		int index = 0; 
		while(reader.hasNextLine())
		{
			constellations[index] = reader.nextLine().trim();
			index++;
		
		}
		
		
		reader = null;
		constellationFile = new File("QuestionsAndAnswers.txt");
		
		// try-catch block for checking if it's able to read QuestionsAndAnswers.txt
	
		try
		{
			reader = new Scanner(constellationFile);
		
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\n\nERROR: Cannot file/open file %s\n\n\n","QuestionsAndAnswers.txt"); 
			System.exit(1);
		}
		
		index = 0; 
		while(reader.hasNextLine())
		{
			questions[index] = reader.nextLine().trim();
			answers[index] = reader.nextLine().trim();
			index++;
		
		}
		

		
		// try-catch block for checking if it's able to store images in the array.
		
		for(int k = 0; k < constellations.length; k++) // for loop that goes through all the images in the array.
		{
			String pictName = constellations[k] + ".jpg";
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
			

			while(reader.hasNextLine())
			{
				mission = reader.nextLine();
			}
			

			
		}
		
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
		
		while(reader.hasNextLine())
		{
			information[index] = reader.nextLine().trim();
			index++;
			for (int i = 0; i < information.length; i++)
			{
				System.out.println(information[i]);
			}
			
		}
		
		reader.close();
		
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
	private CardLayout card;
	private Constellio2Panel cp;
		
		// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
		// which are nested inside this class are able to access them. 
		public HomePanel(CardLayout cards,Constellio2Panel c2p)
		{
			cp = c2p;
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
			JButton highscores = new JButton("High Scores");
			highscores.addActionListener(hbh);
			highscores.setBounds(350,700,100,50);
			
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
			add(highscores);
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
					card.show(cp,"Game");
					cp.getTimer().start();	
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
	private Constellio2Panel cp; 
	private CardLayout card;
	
		// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
		// which are nested inside this class are able to access them. 
		public HowToPlayPanel(CardLayout cards,Constellio2Panel c2p)
		{
				cp = c2p;
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
				JTextArea jta = new JTextArea(cp.getMission(),400,400);
				JScrollPane scroller = new JScrollPane(jta);
				scroller.setPreferredSize(new Dimension(450,110));
				centerPanel.add(scroller);
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
			private Constellio2Panel cp; 
			private CardLayout card;
			
			
		// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
		// which are nested inside this class are able to access them. 
			public ConstellationGuidePanel(CardLayout cards,Constellio2Panel c2p)
			{
		
				cp = c2p;
				card = cards;
	
				setLayout(new BorderLayout());
				// Label in the North
				JLabel constellationGuide = new JLabel("Constellations Guide");
				constellationGuide.setFont(new Font("Serif", Font.BOLD, 30));
				add(constellationGuide,BorderLayout.NORTH);	
				
				// Panel created in the South.
				JPanel cButtonsSouth = new JPanel();
				cButtonsSouth.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
				setBackground(Color.CYAN);
				cButtonsSouth.setBackground(Color.CYAN);
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
				
				// JMenu
				JMenuBar sets = new JMenuBar();
				JMenu choosingSet = new JMenu("Choose Set"); // name of the Menu
				JMenuItem set1 = new JMenuItem("Set 1");
				JMenuItem set2 = new JMenuItem("Set 2");
				JMenuItem set3 = new JMenuItem("Set 3");
				choosingSet.add(set1);
				choosingSet.add(set2);
				choosingSet.add(set3);
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
							card.show(cp,"Game");
							cp.getTimer().start();	
								
						}
						
						else if(command.equals("Previous"))
						{
							// TO DO
						}
						
						else 
						{
							// TO DO	
						}
							
					
				}
							
					
			}
			
}

// This class shows the various levels that are present. It presently, has a borderLayout. 
// The buttons are in the east, and the center is completely left to show the various types 
// of levels. 		
class LevelsPanel extends JPanel 
{
	private Constellio2Panel cp; 
	private CardLayout card;
	
	// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public LevelsPanel(CardLayout cards,Constellio2Panel c2p)
	{
			cp = c2p;
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
		add(centerPanel,BorderLayout.CENTER); // Panel is added to the center.
		
		
		 
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
					card.show(cp,"Game");
					cp.getTimer().start();		
				}
				
				else 
				{
					System.exit(1);	
				}
			
		}				
				
	}
	
	
}

// This panel shows the high scores of the players who played the game. 
// This panel has a border layout, where the text is in the north and the 
// buttons are in the south. 		
class HighScoresPanel extends JPanel 
{
	private Constellio2Panel cp; 
	private CardLayout card;
	
	// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public HighScoresPanel(CardLayout cards,Constellio2Panel c2p)
	{
		
		cp = c2p;
		card = cards;

		setLayout(new BorderLayout());
		
		// The Label is added to the North.
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.BLUE);
		JLabel textInNorth = new JLabel("These Players are on our high score list!",JLabel.CENTER);
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
		
		// The centerPanel in the center. 
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.BLUE);
		add(centerPanel,BorderLayout.CENTER);
		

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
			if(command.equals("Main Menu")) 
				{
					card.show(cp,"Home Panel");
					
				}

			else 
			{
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
	private Constellio2Panel cp;
	private CardLayout card;
	private JSlider imageSize;
	private JLabel countDown;
	private ImagePanel imagePanel;
	private int height;
	private int width;
	private int image;
	private int questionNumber;
	
	// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public GamePanel(CardLayout cards,Constellio2Panel c2p)
	{
		cp = c2p;
		card = cards;
		countDown = new JLabel("Timer : " + cp.getSeconds()); // Creates the label timer which shows the number of seconds.
		height = 300; 
		width = 500;
		questionNumber = (int)(Math.random()*cp.getQuestions().length); // uses Math.Random to randomly choose a question.
		image = questionNumber%12; 

		TimerHandler th = new TimerHandler();
		
		if(cp.getTimer() == null)
		{
			
			cp.setTimer(new Timer(1000,th)); // sets the timer to 1000 milliseconds and passes in the instance of the Timer Handler class.
	
			
		}
		
		// Label in the North.
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		JLabel gameLabel = new JLabel("Game",JLabel.CENTER);
		gameLabel.setFont(new Font("Serif", Font.BOLD, 30));
		northPanel.add(gameLabel,BorderLayout.CENTER);
		northPanel.add(countDown,BorderLayout.EAST);
		add(northPanel,BorderLayout.NORTH);
		
		
		// SouthPanel to store buttons and Label	
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		
		// ButtonsPanel to hold the buttons.
		JPanel buttonPanel = new JPanel();
		JButton quit = new JButton("Quit"); // quit button
		GameButtonHandler gbh = new GameButtonHandler();
		quit.addActionListener(gbh ); // adds the listener to the button.
		JButton main = new JButton("Main Menu");// main menu button
		main.addActionListener(gbh); // adds the listener to the button.

		buttonPanel.add(quit);
		buttonPanel.add(main);
		
		// Level Type Label in center of the panel.
		JLabel levelType = new JLabel("Level: ");
		levelType.setFont(new Font("Serif", Font.BOLD, 20));
		
		southPanel.add(levelType,BorderLayout.CENTER); // levelType Label added to center of the panel.
		southPanel.add(buttonPanel,BorderLayout.EAST); // the buttonPanel added to the east of the panel.
		add(southPanel,BorderLayout.SOUTH); // the whole southPanel added to the South.
		
		// centerPanel
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.PINK);
		centerPanel.setLayout(new BorderLayout());
		
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
		JTextArea question = new JTextArea(cp.getQuestions()[questionNumber],3,45); // gets the question which was randomly created previously and shows the question at that particular index.
		
		question.setLineWrap(true); 
		question.setWrapStyleWord(true); 
		question.setEditable(false);
		JTextField answer = new JTextField("Put your answer here",20);
		rightPanel.add(question);
		rightPanel.add(answer);
		JButton submit = new JButton("SUBMIT");
		submit.addActionListener(gbh);
		rightPanel.add(submit);
		
		
		centerPanel.add(rightPanel,BorderLayout.CENTER);
		centerPanel.add(leftPanel,BorderLayout.WEST);
		add(centerPanel,BorderLayout.CENTER); // centerPanel is added to the center. 
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		countDown.setText("Timer : " + cp.getSeconds()); // sets the text when the countDown starts.
		imagePanel.repaint();
		
	}
	
	class GameButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			// checks if any of the buttons were clicked and redirects to that particular page. 
				if(command.equals("Main Menu"))
				{
					card.show(cp,"Home Panel");
					cp.getTimer().stop();	
					
				}
				
				else if(command.equals("SUBMIT"))
				{
					System.out.println("I submitted");
					cp.getTimer().stop();	
			
				}
				
				else 
				{
					System.exit(1);
						
				}
				
				cp.resetSeconds(); // resets the timer back. 
		}
	}
		
	class TimerHandler implements ActionListener
	{
		 public void actionPerformed(ActionEvent evt)
		 {
			 if(cp.getSeconds() > 0)
			 {
				cp.setSeconds(cp.getSeconds()-1); // subtracts a second in order to begin countdown.
				 repaint(); // calls repaint to change the seconds in the label.
			 }
			 	 
			 
		}
		
		
	}
	
											
	class SliderListener implements ChangeListener
	{
		public void stateChanged (ChangeEvent evt) 
		{
			int val = imageSize.getValue()*5;	// gets the value of the slider and multiplies it by 5 so that the change in the amount of pixels of the image is high.
			imagePanel.updateImage(width + val,height + val,image); // changes the width and height accordingly after the slider changes. 
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
	private Constellio2Panel cp; 
	private int widthOfImages; 
	private int heightOfImages;
	private int imageNumber;
	
	
	public ImagePanel(Constellio2Panel c2p,int width,int height,int image)
	{
		cp = c2p;
		widthOfImages = width; 
		heightOfImages = height;
		imageNumber = image;
		
	}
	
	public void updateImage(int width, int height,int image)
	{
		widthOfImages = width;
		heightOfImages = height;
		imageNumber = image;
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(cp.getImages()[imageNumber],0,0,widthOfImages,heightOfImages,this); // displays the image with the correct width and height after the changes with the slider. 
		
	}

}		

