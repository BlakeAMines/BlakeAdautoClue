package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
		JPanel rollPanel = new JPanel();
		JPanel inner0x2 = new JPanel();
		
		setLayout(new GridLayout(2, 0));
		
		inner0x2.setLayout(new GridLayout(0, 2));
		
		inner1x4.setLayout(new GridLayout(1, 4));
		
		turnPanel.setLayout(new GridLayout(2, 0));
		
		JLabel turnLabel = new JLabel("Whose turn?");
		JTextField turnName = new JTextField("Name");
		turnPanel.add(turnLabel);
		turnPanel.add(turnName);
		
		JLabel roll = new JLabel("Roll: ");
		JTextField rollNumText = new JTextField(Integer.toString(5));
		rollPanel.add(roll);
		rollPanel.add(rollNumText);
		
		JButton accuse = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		
		inner1x4.add(turnPanel);
		inner1x4.add(rollPanel);
		inner1x4.add(accuse);
		inner1x4.add(next);
		add(inner1x4);
		
		JPanel temp1x0 = new JPanel();
		temp1x0.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		temp1x0.setLayout(new GridLayout(0, 1));
		JTextField tempText = new JTextField("I have no guess!");
		temp1x0.add(tempText);
		
		inner0x2.add(temp1x0);
		
		temp1x0 = new JPanel();
		temp1x0.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		temp1x0.setLayout(new GridLayout(0, 1));
		tempText = new JTextField("So you have nothing?");
		temp1x0.add(tempText);
		
		inner0x2.add(temp1x0);
		add(inner0x2);
		
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
