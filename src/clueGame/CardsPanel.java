package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel
{
	public CardsPanel()
	{
		setLayout(new GridLayout(0, 1));
		
		JPanel knownCards = new JPanel();
		knownCards.setLayout(new GridLayout(0, 1));
		knownCards.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		people.add(new JLabel("In Hand: "));
		people.add(new JTextField("Hey"));
		people.add(new JLabel("Seen: "));
		people.add(new JTextField("See ya"));
		
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		rooms.add(new JLabel("In Hand: "));
		rooms.add(new JTextField("Hi"));
		rooms.add(new JLabel("Seen: "));
		rooms.add(new JTextField("Bye"));
		
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weapons.add(new JLabel("In Hand: "));
		weapons.add(new JTextField("Hello"));
		weapons.add(new JLabel("Seen: "));
		weapons.add(new JTextField("Goodbye"));
		
		knownCards.add(people);
		knownCards.add(rooms);
		knownCards.add(weapons);
		
		add(knownCards);
		
	} //end CardsPanel	

	public void updateKnown()
	{
		
		
	} //end updateKnown
	
} //end CardsPanel