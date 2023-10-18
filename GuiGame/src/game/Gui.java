package game;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;


public class Gui {
	
	protected static JFrame frame;
	private static JLabel moves;
	private static JButton[][] buttons;
	private static BorderLayout frameLayout;
	private static JPanel topPanel;
	private static JPanel leftPanel;
	private static JPanel rBuffer;
	private static JLabel BufferLabel;
	private static JPanel gamePanel;
	private static JButton credits;
	private static JLabel level;
	private static boolean[][] prevBoard;
	private static JOptionPane nextLevel;
	private static String welcomeMessage;
	
	
	private static int size;
	protected static Board gameBoard;
	private static int currentLevel;
	
	
	public Gui() {
		
		gameBoard = new Board();
		currentLevel = gameBoard.getLevel();
		size = 5;
		buttons = new JButton[size][size];
		prevBoard = gameBoard.getBoard();
		
		//frame
		frame = new JFrame();
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("All-Out");
		
		frameLayout = new BorderLayout();
		frame.setLayout(frameLayout);
		
		
		
		
		//top panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,5));
		
		moves = new JLabel("Moves: "+gameBoard.getMoves());
		topPanel.add(moves);
		
		level = new JLabel("Level: " + currentLevel);
		topPanel.add(level);
		
		credits = new JButton("Credits");
		credits.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						creditsButton(e);
					}
				});
		
		topPanel.add(credits);
		
		frame.add(topPanel,BorderLayout.NORTH);
		
		
		//left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3,1));
		
		BufferLabel = new JLabel("        ");
		leftPanel.add(BufferLabel);
		
		frame.add(leftPanel,BorderLayout.WEST);
		
		
		//right buffer panel
		rBuffer = new JPanel();
		BufferLabel = new JLabel("        ");
		rBuffer.add(BufferLabel);
		frame.add(rBuffer,BorderLayout.EAST);
		
		
		//center game panel
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(size,size));
		gamePanel.setPreferredSize(new Dimension(420,420));
		
 		createButtons();
 		//adds buttons to panel
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				gamePanel.add(buttons[row][col]);
				
			}
			
		}
		frame.add(gamePanel,BorderLayout.CENTER);
		updateColours();
		

		
		
		welcomeMessage = "Welcome to all out.  The point of this game is to hatch all the "
				+ "eggs into chicks.  To do so, press on an egg or chick, and the state"
				+ " of the chicks and eggs around it will change";
		
		JOptionPane.showMessageDialog(frame,welcomeMessage);
		
		initializeBoard();
		frame.setResizable(false);
		frame.setVisible(true);
		backgroundMusic();
	}
	
	private static void createButtons() {
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				buttons[row][col] = new JButton(); 
				buttons[row][col].setPreferredSize(new Dimension(10,10));
				buttons[row][col].setIcon(getOnImage());
				buttons[row][col].setBorderPainted(false); 
				buttons[row][col].setContentAreaFilled(false); 
				buttons[row][col].setFocusPainted(false); 
				buttons[row][col].setOpaque(false);
				buttons[row][col].setPreferredSize(new Dimension(2048,2048));
				buttons[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonClicked(e);
					}
					
				});
				
			}	
		}
	}
	
	protected static void buttonClicked(ActionEvent e) {
	    JButton btClicked = (JButton)e.getSource();
	    for(int row = 0; row < size; row++) {
	        for(int col = 0; col < size; col++) {
	            if(btClicked.equals(buttons[row][col])) {
	                gameBoard.updateBoard(row,col);
	                buttonSound();
	                updateColours();
	                update();
	                
	            }
	        }
	    }
	}
	
	private static void updateColours() {
		boolean[][] currentBoard = gameBoard.getBoard();
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				
					if(gameBoard.getBoard()[row][col]) 
						buttons[row][col].setIcon(getOffImage());
					else
						buttons[row][col].setIcon(getOnImage());
				
			}
		
		}
		prevBoard =  gameBoard.getBoard();
	}
	
	private static void buttonSound() {
		try {
			File soundFile = new File("buttonPressed.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(sound);
			clip.setFramePosition(0);
			clip.start();
		}catch(Exception e) {
			
		}
	}
	
	private static void backgroundMusic() {
		try {
			File soundFile = new File("backgroundMusic.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(sound);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}catch(Exception e) {
			
		}
	}
	
	private static ImageIcon getOnImage() {
		int numOfImages = 3;
		int choice = (int)(Math.random()*numOfImages)+1;
		ImageIcon image = null;
		if(choice == 1) {
			int chance = (int)(Math.random()*100)+1;
			if(chance == 1) 
				choice = 1;
			else
				choice = (int)(Math.random()*(numOfImages-1))+2;
		}
		image = new ImageIcon("egg"+ choice+ ".png");
		
		
		return image;
		
		
	}
	
	private static ImageIcon getOffImage() {
		int numOfImages = 11;
		int choice = (int)(Math.random()*numOfImages)+1;
		ImageIcon image = null;
		if(choice == 1) {
			int chance = (int)(Math.random()*10)+1;
			if(chance == 1) 
				choice = 1;
			else
				choice = (int)(Math.random()*(numOfImages-1))+2;
		}
		
		image = new ImageIcon("chick"+ choice+ ".png");
		
		
		return image;
	}
	
	private static void update() {
		updateColours();
		moves.setText("Move: "+ gameBoard.getMoves());
		
		if(currentLevel != gameBoard.getLevel())
			nextLevelDialog();
	}
	
	private static void nextLevelDialog() {
		level.setText("Level: " + gameBoard.getLevel());

		if(currentLevel < 5) {
			JOptionPane.showMessageDialog(frame, "Congratulations! You have reached level " + gameBoard.getLevel());
			currentLevel = gameBoard.getLevel();
		}else if(currentLevel == 5) {
			JOptionPane.showMessageDialog(frame, "Congragulations! You have completed all the premade levels. "
					+ " From now on levels will be randomly generated");
			currentLevel = gameBoard.getLevel();
		}else {
			JOptionPane.showMessageDialog(frame, "Congratulations! You have reached level " + gameBoard.getLevel());
			currentLevel = gameBoard.getLevel();
		}
	}

	protected static Board getBoard() {
		return gameBoard;
	}
	private static void initializeBoard() {
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				
				if(gameBoard.getBoard()[row][col]) 
					buttons[row][col].setIcon(getOffImage());
				else
					buttons[row][col].setIcon(getOnImage());
			}
		}
	}

	private static void creditsButton(ActionEvent e) {
		GridLayout layout = new GridLayout(1,3);
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		
		JLabel c1 = new JLabel("Graphics: Amaryah Gesch");
		JLabel c2 = new JLabel("Music: Carolyn Rayhons");
		JLabel c3 = new JLabel("Programming: Paul van Ginkel");
		
		panel.add(c1);
		panel.add(c2);
		panel.add(c3);
		
		JOptionPane.showMessageDialog(frame,panel);
		
	}

}