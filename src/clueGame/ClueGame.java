package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ClueGame extends JFrame
{
	Board board;
	
	//This constructor creates the splash text and begins the game
	public ClueGame()
	{		
		SplashText intro = new SplashText("This is clue. You are the green player 'Regular Person'", true);
		intro.add(new JLabel("Press the 'NEXT' button to begin"));
		
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		board.initialize();
		
		GameControlPanel controlPanel = new GameControlPanel();
		
		CardsPanel cardPanel = new CardsPanel(board.getHumanPlayer());
		
		SuggestionDialog suggestDialog = new SuggestionDialog();
		
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board.setPanel(controlPanel);
		board.setSuggestDialog(suggestDialog);
			
		add(board, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
						
	} //end ClueGame
	
	/*
	//I commented this main out because my program won't run on my machine if I run main, but it should work
	//If there are issues, please use the JUnit test to run the program, but this main should work as intended
	public static void main(String[] args)
	{
		ClueGame game = new ClueGame();
		
		game.setVisible(true);
		
	}
	*/
		
} //end ClueGame
