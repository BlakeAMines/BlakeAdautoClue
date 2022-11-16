package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel
{
	private JPanel people;
	private JPanel rooms;
	private JPanel weapons;
	private HumanPlayer player;
	
	public CardsPanel(HumanPlayer initPlayer)
	{		
		player = initPlayer;
		
		setLayout(new GridLayout(3, 1));
		
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		people = new JPanel();
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		people.add(new JLabel("In Hand: "));
		people.add(new JTextField("None",12));

		people.add(new JLabel("Seen: "));
		people.add(new JTextField("None"));
		
		rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		rooms.add(new JLabel("In Hand: "));
		rooms.add(new JTextField("None"));
		
		rooms.add(new JLabel("Seen: "));
		rooms.add(new JTextField("None"));
		
		weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		
		weapons.add(new JLabel("In Hand: "));
		weapons.add(new JTextField("None"));
		
		weapons.add(new JLabel("Seen: "));
		weapons.add(new JTextField("None"));
		
		add(people);
		add(rooms);
		add(weapons);
		

	} //end CardsPanel	

	public void updateKnown()
	{
		people.removeAll();
		people.add(new JLabel("In Hand: "));
		
		rooms.removeAll();
		rooms.add(new JLabel("In Hand: "));
		
		weapons.removeAll();
		weapons.add(new JLabel ("In Hand: "));
		
		Set<Card> seenCards = player.getSeen();
		List<Card> hand = player.getHand();
		Map<Card, Player> ownerships = player.getHumanSeen();
		
		JTextField temp;
		
		for(int i = 0; i < hand.size(); i++)
		{
			if(hand.get(i).getType().equals(CardType.PERSON))
			{
				people.add(new JTextField(hand.get(i).getName(),12));
				
			} //end nested if
			
			else if(hand.get(i).getType().equals(CardType.ROOM))
			{
				rooms.add(new JTextField(hand.get(i).getName(),12));
				
			} //end nested else if
			
			else if(hand.get(i).getType().equals(CardType.WEAPON))
			{
				weapons.add(new JTextField(hand.get(i).getName(),12));
				
			} //end nested else if
			
		} //end for
		
		people.add(new JLabel("Seen: "));
		rooms.add(new JLabel("Seen: "));
		weapons.add(new JLabel("Seen: "));
		
		for(Card curCard : seenCards)
		{
			if(curCard.getType().equals(CardType.PERSON) && !hand.contains(curCard))
			{
				temp = new JTextField(curCard.getName());
				temp.setBackground(ownerships.get(curCard).getColor());
				
				people.add(temp);
				
			} //end nested if
			
			else if(curCard.getType().equals(CardType.ROOM) && !hand.contains(curCard))
			{
				temp = new JTextField(curCard.getName());
				temp.setBackground(ownerships.get(curCard).getColor());
				
				rooms.add(temp);
				
			} //end nested else if
			
			else if(curCard.getType().equals(CardType.WEAPON) && !hand.contains(curCard))
			{
				temp = new JTextField(curCard.getName());
				temp.setBackground(ownerships.get(curCard).getColor());
				
				weapons.add(temp);
				
			} //end nested else if
			
		} //end for
		revalidate();

	} //end updateKnown
	
} //end CardsPanel