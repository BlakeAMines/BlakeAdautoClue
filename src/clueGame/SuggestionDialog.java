package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog
{
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JTextField curRoom;
	private JComboBox<String> weapons;
	private JComboBox<String> people;
	
	public SuggestionDialog()
	{
		setTitle("Make a suggestion");
		setSize(300, 200);
		setLayout(new GridLayout(0, 2));
				
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent press) 
			{
				System.out.println(curRoom.getText());
				System.out.println(people.getSelectedItem());
				System.out.println(weapons.getSelectedItem());
				//Submit the suggestion to the game
				
				setVisible(false);
				
			} //end actionPerformed
			
		}); //end addActionListener
		
		JButton cancel = new JButton("Cancel");
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
		
		weapons = new JComboBox<String>();
		weapons.addItem("Weapon 1");
		weapons.addItem("Weapon 2");
		
		people = new JComboBox<String>();
		people.addItem("Person 1");
		people.addItem("Person 2");
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		rightPanel.add(curRoom);
		rightPanel.add(people);
		rightPanel.add(weapons);
		rightPanel.add(cancel);
		
		add(leftPanel);
		add(rightPanel);
		
		setVisible(true);
		
	} //end constructor

} //end SuggestionDialoge
