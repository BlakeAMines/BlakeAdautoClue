package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel
{
	private JPanel handPeople;
	private JPanel seenPeople;
	private JPanel handRooms;
	private JPanel seenRooms;
	private JPanel handWeapons;
	private JPanel seenWeapons;
	private Player player;
	
	public CardsPanel(Player initPlayer)
	{		
		player = initPlayer;
		
		setLayout(new GridLayout(0, 1));
		
		JPanel knownCards = new JPanel();
		knownCards.setLayout(new GridLayout(0, 1));
		knownCards.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		people.add(new JLabel("In Hand: "));
		handPeople = new JPanel();
		handPeople.setLayout(new GridLayout(0, 1));
		people.add(handPeople);

		people.add(new JLabel("Seen: "));
		seenPeople = new JPanel();
		seenPeople.setLayout(new GridLayout(0, 1));
		people.add(seenPeople);
		
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		rooms.add(new JLabel("In Hand: "));
		handRooms = new JPanel();
		handRooms.setLayout(new GridLayout(0, 1));
		rooms.add(handRooms);
		
		rooms.add(new JLabel("Seen: "));
		seenRooms = new JPanel();
		seenRooms.setLayout(new GridLayout(0, 1));
		rooms.add(seenRooms);
		
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		
		weapons.add(new JLabel("In Hand: "));
		handWeapons = new JPanel();
		handWeapons.setLayout(new GridLayout(0, 1));
		weapons.add(handWeapons);
		
		weapons.add(new JLabel("Seen: "));
		seenWeapons = new JPanel();
		seenWeapons.setLayout(new GridLayout(0, 1));
		weapons.add(seenWeapons);
		
		knownCards.add(people);
		knownCards.add(rooms);
		knownCards.add(weapons);
		
		add(knownCards);

	} //end CardsPanel	

	public void updateKnown()
	{
		handPeople.removeAll();
		seenPeople.removeAll();
		
		Set<Card> seenCards = player.getSeen();
		List<Card> hand = player.getHand();
		
		for(Card curCard : seenCards)
		{
			if(curCard.getType().equals(CardType.PERSON) && !hand.contains(curCard))
			{
				seenPeople.add(new JTextField(curCard.getName()));
				
			} //end nested if
			
			else if(curCard.getType().equals(CardType.ROOM) && !hand.contains(curCard))
			{
				seenRooms.add(new JTextField(curCard.getName()));
				
			} //end nested else if
			
			else if(curCard.getType().equals(CardType.WEAPON) && !hand.contains(curCard))
			{
				seenWeapons.add(new JTextField(curCard.getName()));
				
			} //end nested else if
			
		} //end for
		
	} //end updateKnown
	
	public void inHand()
	{
		
		
	} //end inHand

	
} //end CardsPanel