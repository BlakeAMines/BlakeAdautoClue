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
	private JTextField nameVal;
	private JTextField rollVal;
	private JTextField guessVal;
	private JTextField guessResult;
	
	public GameControlPanel() 
	{
		JPanel inner1x4 = new JPanel();
		JPanel turnPanel = new JPanel();
		JPanel rollPanel = new JPanel();
		JPanel inner0x2 = new JPanel();
		
		setLayout(new GridLayout(2, 0));
		
		inner1x4.setLayout(new GridLayout(1, 4));
		inner0x2.setLayout(new GridLayout(0, 2));
		
		turnPanel.setLayout(new GridLayout(2, 0));
		turnPanel.add(new JLabel("Whose turn?"));
		nameVal = new JTextField("Name");
		nameVal.setEditable(false);
		turnPanel.add(nameVal);

		rollPanel.add(new JLabel("Roll: "));
		rollVal = new JTextField(Integer.toString(5));
		rollVal.setEditable(false);
		rollPanel.add(rollVal);
		
		inner1x4.add(turnPanel);
		inner1x4.add(rollPanel);
		inner1x4.add(new JButton("Make Accusation"));
		inner1x4.add(new JButton("NEXT!"));
		add(inner1x4);
		
		JPanel temp1x0 = new JPanel();
		temp1x0.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		temp1x0.setLayout(new GridLayout(0, 1));
		guessVal = new JTextField("I have no guess!");
		guessVal.setEditable(false);
		temp1x0.add(guessVal);
		inner0x2.add(temp1x0);
		
		temp1x0 = new JPanel();
		temp1x0.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		temp1x0.setLayout(new GridLayout(0, 1));
		guessResult = new JTextField("So you have nothing?");
		guessResult.setEditable(false);
		temp1x0.add(guessResult);
		inner0x2.add(temp1x0);
		
		add(inner0x2);
		
	} //end constructor
	
	public void setGuess(String guess)
	{
		guessVal.setText(guess);
		
	} //end guess
	
	public void setPlayer(Player player)
	{
		nameVal.setText(player.getName());
		rollVal.setText(Integer.toString(player.getRoll()));
		
	} //end setPlayer
	
	public void setResult(String result)
	{
		guessResult.setText(result);
		
	} //end setResult
	
} //end GameControlPanel
