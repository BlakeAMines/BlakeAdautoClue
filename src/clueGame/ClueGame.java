package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class ClueGame extends JFrame
{
	Board board;
	
	public ClueGame()
	{		
		//SplashText intro = new SplashText();
		
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
