package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ClueGame extends JFrame
{
	Board board;
	
	public ClueGame()
	{		
		SplashText intro = new SplashText("This is clue");
		intro.add(new JLabel("You are the green player 'Regular Person'"));
		
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		board.initialize();
		
		GameControlPanel controlPanel = new GameControlPanel();
		
		CardsPanel cardPanel = new CardsPanel(board.getHumanPlayer());
		
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		add(board, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
				
	} //end ClueGame
		
} //end ClueGame
