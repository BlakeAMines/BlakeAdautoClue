package clueGame;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel
{
	Player curPlayer;
	int rollNum;
	String curGuess;
	String guessResult;
	
	public GameControlPanel() 
	{
		JPanel inner1x4 = new JPanel();
		JPanel turnPanel = new JPanel();
		JPanel rollLabel = new JPanel();
		JPanel innter0x2 = new JPanel();
		
		setLayout(new GridLayout(2, 0));
		
		inner1x4.setLayout(new GridLayout(1, 4));
		JLabel turnLabel = new JLabel("Whose turn?");
		JTextField turnName = new JTextField(curPlayer.getName());
		turnPanel.add(turnLabel);
		turnPanel.add(turnName);
		
		
		
		
		inner1x4.add(turnPanel);
		add(inner1x4);
		
	} //end constructor
	
	public void setGuess(String guess)
	{
		curGuess = guess;
		
	} //end guess
	
	public void setPlayer(Player player)
	{
		curPlayer = player;
		
	} //end setPlayer
	
} //end GameControlPanel
