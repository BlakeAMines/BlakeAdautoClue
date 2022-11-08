package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Player 
{
	private String name;
	private String color;
	
	private ArrayList<Card> cards;
	private Set<Card> seenCards;
	private ArrayList<Card> unseenCards;
	
	protected Card curRoom;	
	private int curRow;
	private int curColumn;
	
	private Random rand;
	
	protected Player(String initName, String initColor, String initRoomName)
	{
		cards = new ArrayList<>();
		unseenCards = new ArrayList<>();
		seenCards = new HashSet<>();
		
		name = initName;
		color = initColor;
		//curRoomName = initRoomName;
		
		rand = new Random();
		
		ArrayList<Card> gameDeck = Board.getInstance().getCleanDeck();
		for(int i = 0; i < gameDeck.size(); i++)
		{
			unseenCards.add(gameDeck.get(i));
			
		} //end for 
		
	} //end constructor
	
	public Card disproveSuggestion(Solution suggestion)
	{
		Card response = null;
		Card checkCard;
		
		ArrayList<Card> matchCards = new ArrayList<>();
		
		for(int i = 0; i < cards.size(); i++)
		{
			checkCard = cards.get(i);
			
			if(checkCard.equals(suggestion.getRoom()))
			{
				matchCards.add(suggestion.getRoom());
				
			} //end nested if
			
			if(checkCard.equals(suggestion.getPerson()))
			{
				matchCards.add(suggestion.getPerson());
				
			} //end nested if
			
			if(checkCard.equals(suggestion.getWeapon()))
			{
				matchCards.add(suggestion.getWeapon());
				
			} //end nested if
			
		} //end for
		
		if(!matchCards.isEmpty())
		{
			if(matchCards.size() == 1)
			{
				response = matchCards.get(0);
				
			} //end nested if
			
			else
			{
				int random;
				random = rand.nextInt(matchCards.size());
				
				response = matchCards.get(random);
				
			} //end nested else
			
		} //end if
		
		return response;
		
	} //end disproveSuggestion
	
	public void updateSeen(Card card)
	{
		seenCards.add(card);
		unseenCards.remove(card);
		
	} //end updateSeen
	
	public void updateHand(Card card)
	{
		cards.add(card);
		
	} //end updateHand
	
	public String getName()
	{
		return name;
		
	} //end getName;
	
	public abstract boolean isHuman();
	
	public String getColor()
	{
		return color;
		
	} //end getColor
	
	public List<Card> getHand()
	{
		return cards;
		
	} //end getHand
	
	public void setRoom(Card setRoom)
	{
		curRoom = setRoom;
		
	} //end setRoom
	
} //end Player
