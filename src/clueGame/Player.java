package clueGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Player 
{
	private String name;
	private String color;
	
	private ArrayList<Card> cards;
	private Set<Card> seenCards;
	
	private String curRoomName;	
	private int curRow;
	private int curColumn;
	
	protected Player(String initName, String initColor, String initRoomName)
	{
		cards = new ArrayList<Card>();
		
		name = initName;
		color = initColor;
		curRoomName = initRoomName;
		
	} //end constructor
	
	public Card disproveSuggestion()
	{
		return new Card("Empty", "Empty");
		
	} //end disproveSuggestion
	
	public void updateSeen(Card card)
	{
		
		
	} //end updateSeen
	
	public void updateHand(Card card)
	{
		cards.add(card);
		
	} //end updateHand
	
	public String getName()
	{
		return name;
		
	} //end getName;
	
	public String getType()
	{
		return null;
		
	} //end getType
	
	public String getColor()
	{
		return color;
		
	} //end getColor
	
	public List<Card> getHand()
	{
		return cards;
		
	} //end getHand
	
} //end Player
