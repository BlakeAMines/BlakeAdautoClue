package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog
{
	Board board = Board.getInstance();
	
	private Player curPlayer;
	
	protected JPanel leftPanel;
	protected JPanel rightPanel;
	
	private JTextField curRoom;
	protected JComboBox<String> weapons;
	protected JComboBox<String> people;
	
	protected JButton cancel;
	protected JButton submit;
	
	public SuggestionDialog()
	{
		setTitle("Make a suggestion");
		setSize(300, 200);
		setLayout(new GridLayout(0, 2));
		setModal(true);
				
		submit = new JButton("Submit");
		submit.addActionListener(new SubmitSuggestListener());
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent press) 
			{
				setVisible(false);
				
			} //end actionPerformed
			
		}); //end addActionListener
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4, 1));
		leftPanel.add(new JLabel("Current Room"));
		leftPanel.add(new JLabel("Person"));
		leftPanel.add(new JLabel("Weapon"));
		leftPanel.add(submit);
		
		curRoom = new JTextField("Room...");
		curRoom.setEditable(false);
		
		people = new JComboBox<String>();
		weapons = new JComboBox<String>();
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		rightPanel.add(curRoom);
		rightPanel.add(people);
		rightPanel.add(weapons);
		rightPanel.add(cancel);
		
		add(leftPanel);
		add(rightPanel);
		
		setComboBoxes();
		
		setVisible(false);
		
	} //end constructor
	
	public void setCurRoom(Card room)
	{
		curRoom.removeAll();
		curRoom.setText(room.getName());
		
	} //end setComboBoxes
	
	public void setComboBoxes()
	{
		ArrayList<Card> deck = board.getCleanDeck();
		
		people.removeAll();
		weapons.removeAll();
		
		for(int i = 0; i < deck.size(); i++)
		{
			if(deck.get(i).getType().equals(CardType.PERSON))
			{
				people.addItem(deck.get(i).getName());
				
			} //end nested if
			
			else if(deck.get(i).getType().equals(CardType.WEAPON))
			{
				weapons.addItem(deck.get(i).getName());
				
			} //end nested if
			
		} //end for 
		
	} //end setComboBoxes
	
	public void setPlayer(Player player)
	{
		curPlayer = player;
		
	} //end setPlayer
	
	class SubmitSuggestListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent press) 
		{
			if(!curPlayer.equals(null))
			{
				curPlayer.setFinished(true);
				board.handleTurn();
				
			} //end if
			
			System.out.println(curRoom.getText());
			System.out.println(people.getSelectedItem());
			System.out.println(weapons.getSelectedItem());
			//Submit the suggestion to the game
			
			setVisible(false);
			
		} //end actionPerformed
				
	} //end suggestListener

} //end SuggestionDialoge
