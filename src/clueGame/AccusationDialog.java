package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class AccusationDialog extends SuggestionDialog
{
	private JComboBox<String> rooms;
	
	public AccusationDialog()
	{
		super();
		
		rooms = new JComboBox<String>();
		
		rightPanel.removeAll();
		rightPanel.setLayout(new GridLayout(4, 1));
		rightPanel.add(rooms);
		rightPanel.add(people);
		rightPanel.add(weapons);
		rightPanel.add(cancel);	
		
		leftPanel.remove(submit);
		submit = new JButton("Submit");
		submit.addActionListener(new SubmitAccuseListener());
		leftPanel.add(submit);
		
		setRoomsComboBox();
		
		setVisible(true);
		
	} //end Constructor
	
	public void setRoomsComboBox()
	{	
		ArrayList<Card> deck = board.getCleanDeck();
		
		for(int i = 0; i < deck.size(); i++)
		{
			if(deck.get(i).getType().equals(CardType.ROOM))
			{
				rooms.addItem(deck.get(i).getName());
				
			} //end nested if
			
		} //end for
		
	} //end setComboBoxes
	
	class SubmitAccuseListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent press) 
		{			
			System.out.println(rooms.getSelectedItem());
			System.out.println(people.getSelectedItem());
			System.out.println(weapons.getSelectedItem());
			//Submit the suggestion to the game
			
			setVisible(false);
			
		} //end actionPerformed
				
	} //end suggestListener
	
} //end AccusationDialog
