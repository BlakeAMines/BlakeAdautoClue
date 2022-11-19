package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

//This is test code for now
public class SplashText extends JFrame
{
	private JButton okButton;

	//This constructor includes a String to set the message displayed to prevent repeat code
	public SplashText(String display)
	{
		setTitle("Clue Game");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//This adds an OK button which closes the popup when pressed
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent press) 
			{
				setVisible(false);
				
			} //end actionPerformed
			
		}); //end addActionListener
		
		//This creates the JFrame with the proper text and button
		JPanel tempPanel1 = new JPanel();
		tempPanel1.setLayout(new GridLayout(1, 1));
		tempPanel1.add(new JLabel(display));
		
		add(tempPanel1, BorderLayout.NORTH);
		
		JPanel tempPanel2 = new JPanel();
		tempPanel2.setLayout(new GridLayout(1, 1));
		tempPanel2.add(okButton);
		
		add(tempPanel2, BorderLayout.SOUTH);
		
		setVisible(true);
				
	} //end constructor
	
} //end SplashText


