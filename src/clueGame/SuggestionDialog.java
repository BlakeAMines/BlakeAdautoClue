package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog
{
	private JTextField tempText;
	private JTextField tempDisplay;
	
	public SuggestionDialog()
	{
		setTitle("Make a suggestion");
		
		setSize(400, 400);
		
		setLayout(new GridLayout(2, 2));
		
		tempText = new JTextField(20);
		tempDisplay = new JTextField(20);
		
		JButton okButton = new JButton("Submit");
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent press) 
			{
				tempDisplay.setText(tempText.getText());;
				
			} //end actionPerformed
			
		}); //end addActionListener
		
		add(okButton);
		add(tempText);
		add(tempDisplay);
		
		setVisible(true);
		
	} //end constructor

} //end SuggestionDialoge
