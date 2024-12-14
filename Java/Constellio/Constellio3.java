// Anchita Dash
// Constellio3.java
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
	
	
	
// This class makes the frame and calls another class Constellio3Panel which handles the 
// job done in the game. The instance of Constellio3Panel is also created and added to the frame. 
public class Constellio3
{
	// creates an instance of the class itself and calls the method makingFrame. 
	public static void main(String args[])
	{
		Constellio3 c3 = new Constellio3(); // creates an instance of the Constellio3 class
		c3.makingFrame(); // calls a method making frame which makes the frame. 
		
	}
	
	// This method is responsible to create the frame labeled "Constellio3"
	// It sets the size and location of the frame. 
	// This method also creates an instance of the Constellio3Panel class whose 
	// contents are added to the frame.
	public void makingFrame()
	{
		JFrame frame = new JFrame("Constellio 3"); // makes the frame named Constellio3
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocation(10,100);
		
		Constellio3Panel c3p = new Constellio3Panel(); // creates an instance of Constellio3Panel class.
		frame.getContentPane().add(c3p); // adds the content of that class to the frame. 
		frame.setVisible(true); // sets the visibility of the frame to true, so that it can be seen. 
		
	}
}
	
// This class creates the cardLayout and all the cards(JPanels) are added to this CardLayout
// which is later shown. This class also initializes the arrays by reading the textfiles. 
// Scanner is used to read the text files and alternative lines are stored in the array. 
// For example 1stLine is the question and the 2nd Line is the answer. It also reads the 
// name of the constellations which is stored in another textFile. The Images are also 
// stored in the image array which stores the different images of the constellations.
class Constellio3Panel extends JPanel
{
	// all field variables declared
	private CardLayout cards; // variable for setting the cardLayout.
	private String[] constellations; // array to store the names of constellations.
	private String[] questions; // array to store questions.
	private String[] answers; // array to store answers. 
	private Image[] images; // array to store images. 
	private String[] information; // array to store information.
	private Timer timer; // instance of timer is created. 
	private int seconds;  // a seconds variable is created when the start button is clicked before selecting the type of level.
	private int levelTime; // variable that takes cares of seconds that are present in each level. 
	private String mission;  // stores the string that contains instructions about how the game is played. 
	private Scanner reader; // reads in the textFiles 
		
		
	// all the instances of the JPanels are created and added to the cardLayout. 
	// the arrays are also initialized in this constructor. 
	public Constellio3Panel()
	{
		
		cards = new CardLayout();	
		setLayout(cards);
		reader = null;
		constellations = new String[12];
		questions = new String[48];
		answers = new String[48];
		images = new Image[12];
		information = new String[12];
		mission = new String("");
		seconds = 60;
		levelTime = 60;
		
		loadConstellations(); // method to read in the names of the constellations. 
		loadQuestions(); // method to read in questions.
		loadImages();  // method to read in images.
		loadInstructions();  // method to read in instructions.
		loadInformation();  // method to read in information.
		
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
	
	// returns the instructions so that other classes are able to access it. 
	public String getMission()
	{
		return mission;
		
	}
	
	// returns the information so that the other classes are able to access it. 
	public String[] getInformation()
	{
		return information;
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
		
		int index =0;
		while(reader.hasNextLine())
		{
			questions[index] = reader.nextLine().trim();
			answers[index] = reader.nextLine().trim();
			index++;
			
		
		}
		
		reader.close();
		
	}
	
	// loadImages reads in the Images for the different constellations. 
	// They are then stored in the images array. 
	public void loadImages()
	{
		// try-catch block for checking if it's able to store images in the array.
		
		int index =0;
		for(int k = 0; k < constellations.length; k++) // for loop that goes through all the images in the array.
		{
			String pictName = constellations[k] + ".jpg"; // the image name is basically a combination of the constellation name 
			// + ".jpg". Ex: Taurus + ".jpg" = Taurus.jpg (name of the constellation image).
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
			
			int index =0;
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
			
			int index =0;
			while(reader.hasNextLine())
			{
				information[index] = reader.nextLine().trim();
				index++;
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
	private CardLayout card; // This is the instance of the cardLayout
	private Constellio3Panel cp; // This is the instance of Constellio3Panel.
		
		// The instance of Constellio3Panel and cardLayout is passed so that the listeners 
		// which are nested inside this class are able to access them. 
		public HomePanel(CardLayout cards,Constellio3Panel c3p)
		{
			cp = c3p;
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
	private Constellio3Panel cp; // This is the instance of Constellio3Panel.
	private CardLayout card; // This is the instance of the cardLayout 

	
		// The instance of Constellio3Panel and cardLayout is passed so that the listeners 
		// which are nested inside this class are able to access them. 
		public HowToPlayPanel(CardLayout cards,Constellio3Panel c3p)
		{
				cp = c3p;
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
				JTextArea jta = new JTextArea(cp.getMission(),700,100);
				jta.setLineWrap(true); 
				jta.setWrapStyleWord(true); 
				jta.setEditable(false);
				JScrollPane scroller = new JScrollPane(jta); 
				scroller.setPreferredSize(new Dimension(700,100));
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
	private Constellio3Panel cp;  // This is the instance of Constellio3Panel.
	private CardLayout card; // This is the instance of the cardLayout 
	private CardLayout[] infoLayouts; // This is the instance of the CardLayout that is present in the all the JPanels
	// that are created in the array, these JPanels display the constellation and the images. 
	private JPanel[] infoPanels; // This is an array of JPanels that stores the image and the information for the 12 different constellations.
	private JPanel[] panels; // This is an array of JPanels that has the CardLayout and is present inside the GridLayout of the centerPanel.
		
		// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
		// which are nested inside this class are able to access them. 
			public ConstellationGuidePanel(CardLayout cards,Constellio3Panel c3p)
			{
		
				cp = c3p;
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
				
				panels = new JPanel[4]; // panels is initialized to 4. 
				infoLayouts = new CardLayout[4]; // cardLayouts is initialized to 4. 
				
				for(int i =0; i < panels.length; i++)
				{
					panels[i] = new JPanel(); // creates 4 new JPanels
					infoLayouts[i] = new CardLayout(); // creates 4 different cardLayouts. 
					panels[i].setLayout(infoLayouts[i]); // sets the layout to CardLayout for the four panels. 
					centerPanel.add(panels[i]);	// all four panels are added to the centerPanel.
				}
				
				add(centerPanel,BorderLayout.CENTER); // centerPanel is added to the center. 
				
				
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
				ConstellationGuideMenu cgm = new ConstellationGuideMenu(); // menu handler of the different sets of the constellationGuidePanel. 
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
							infoLayouts[i].first(panels[i]); // shows the first set of the constellations. 
						}
					}
					
					else if(command.equals("Set 2"))
					{
						for(int i =0; i < infoLayouts.length; i++)
						{
							infoLayouts[i].first(panels[i]); // both of these work together, 
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
	private Constellio3Panel cp; // This is the instance of Constellio3Panel.
	private CardLayout card;  // This is the instance of the cardLayout 
	private ButtonGroup bg; // buttongroup thast holds the three buttons.
	private JRadioButton level1, level2, level3; // three different radioButtons for the levels. 
	
	// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public LevelsPanel(CardLayout cards,Constellio3Panel c3p)
	{
			cp = c3p;
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
		JRadioButton level1 = new JRadioButton("Easy");
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
		
		
		// Column 2
		JLabel number2 = new JLabel("2");
		number2.setFont(new Font("Times New Roman", Font.BOLD, 50));
		column2.add(number2);
		JRadioButton level2 = new JRadioButton("Medium");
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
		
		
		 
		// Column 3
		JLabel number3 = new JLabel("3");
		number3.setFont(new Font("Times New Roman", Font.BOLD, 50));
		column3.add(number3);
		JRadioButton level3 = new JRadioButton("Hard");
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
		
		centerPanel.add(column2,BorderLayout.CENTER);
		centerPanel.add(column1,BorderLayout.WEST);
		centerPanel.add(column3,BorderLayout.EAST);
		
		
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
	
	class RadioButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			// TO DO
			
		}
	
	}
	
}

// This panel shows the high scores of the players who played the game. 
// This panel has a border layout, where the text is in the north and the 
// buttons are in the south. 		
class HighScoresPanel extends JPanel 
{
	private Constellio3Panel cp; // This is the instance of the Constellio3Panel.
	private CardLayout card;  // This is the instance of the cardLayout 
	
	// The instance of Constellio2Panel and cardLayout is passed so that the listeners 
	// which are nested inside this class are able to access them.
	public HighScoresPanel(CardLayout cards,Constellio3Panel c3p)
	{
		
		cp = c3p;
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
	private Constellio3Panel cp;
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
	public GamePanel(CardLayout cards,Constellio3Panel c3p)
	{
		cp = c3p;
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
		northPanel.setBackground(Color.PINK);
		northPanel.setLayout(new BorderLayout());
		JLabel gameLabel = new JLabel("Game",JLabel.CENTER);
		gameLabel.setFont(new Font("Serif", Font.BOLD, 30));
		northPanel.add(gameLabel,BorderLayout.CENTER);
		northPanel.add(countDown,BorderLayout.EAST);
		add(northPanel,BorderLayout.NORTH);
		
		
		// SouthPanel to store buttons and Label	
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.PINK);
		southPanel.setLayout(new BorderLayout());
		
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
	
	// Handles the buttons that were clicked and also stops the timer when one 
	// of the buttons is clicked. 
	class GameButtonHandler implements ActionListener
	{
	
		// checks if any of the buttons were clicked and redirects to that particular page
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
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
		
	// TimerHandler checks if the seconds is greater than 0, and keeps
	// reducing a second in order to showcase an ongoing countdown. 
	// it also calls repaint so that the label is repainted to the actual
	// seconds that is being counted down.
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
	
	// SliderlListener updates the image to the value that the slider has been 
	// changed to. This is done when the original width and height is added to the 
	// val of the slider that has been changed with. 									
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
	private Constellio3Panel cp; // instance of the Constellio3Panel 
	private int widthOfImages; // width of the image 
	private int heightOfImages;// height of the image
	private int imageNumber; // the index of the image that is currently being shown.
	
		
	// the constructor basically sets the values of the parameters to its field variables.
	public ImagePanel(Constellio3Panel c3p,int width,int height,int image)
	{
		cp = c3p;
		widthOfImages = width; 
		heightOfImages = height;
		imageNumber = image;
		
	}
	
	// This updates the image by the value of the slider.  
	public void updateImage(int width, int height,int image)
	{
		widthOfImages = width;
		heightOfImages = height;
		imageNumber = image;
		
		
	}
	// Draws the image with the correct width and height. 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(cp.getImages()[imageNumber],0,0,widthOfImages,heightOfImages,this); // displays the image with the correct width and height after the changes with the slider. 
		
	}

}	

// This class draws the images on the ConstellationsGuidePanel
class InfoImage extends JPanel
{
	private Constellio3Panel cp; 
	private int imageNumber;
	
	// the constructor basically sets the values of the parameters to its field variables.
	public InfoImage(Constellio3Panel c3p,int image)
	{
		setBackground(Color.DARK_GRAY);
		cp = c3p;
		imageNumber = image;
		
	}
	
	// draws the correct image of the particular index and the image is drawn with a particular size. 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(cp.getImages()[imageNumber],0,50,235,250,this); 
	}
	

	
}	

