package Tests;

import clueGame.GameControlPanel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardsPanel;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class RunUI 
{
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_CARDS = 21;
	
	private static Board board;
	
	@BeforeAll
	public static void gameSetup()
	{		

		
	} //end gameSetup
	
	//@Test
	public void testGameControlPanel() 
	{
		GameControlPanel panel = new GameControlPanel();
		JFrame frame = new JFrame(); 
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		panel.setGuess("New Guess");
		panel.setPlayer(new ComputerPlayer("New Name", "Color", "Room", 6));
		panel.setResult("New Result");
		
		System.out.println("Make a breakpoint here");
	
	} //end testGameControlPanel
	
	@Test
	public void testCardPanel()
	{
		CardsPanel panel = new CardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(180, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		System.out.println("Make a breakpoint here");
		
	} //end testCardPanel

} //end GameSetupTests