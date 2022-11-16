package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Player 
{
	private String name;
	private String colorName;
	private Color color;
	
	private ArrayList<Card> cards;
	protected Set<Card> seenCards;
	protected ArrayList<Card> unseenCards;
	
	protected Card curRoom;	
	private int curRow;
	private int curColumn;
	
	int rollNum;
	
	protected Random rand;
	
	protected Player(String initName, String initColor, int xPos, int yPos)
	{
		cards = new ArrayList<>();
		unseenCards = new ArrayList<>();
		seenCards = new HashSet<>();
		
		name = initName;
		colorName = initColor.toLowerCase();
		curRow = xPos;
		curColumn = yPos;
		
		try 
		{
			Field field = Class.forName("java.awt.Color").getField(colorName);
			color = (Color)field.get(null);
			
		} //end try
		
		catch(Exception invalidColor)
		{
			color = null;
			
		} //end catch
		
		
		rand = new Random();
		
		ArrayList<Card> tempGameDeck = Board.getInstance().getCleanDeck();
		for(int i = 0; i < tempGameDeck.size(); i++)
		{
			unseenCards.add(tempGameDeck.get(i));
			
		} //end for 
		
	} //end constructor
	
	protected Player(String initName, String initColor, String initRoomName, int roll)
	{
		name = initName;
		colorName = initColor;
		rollNum = roll;		
		
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
	
	public String getColorName()
	{
		return colorName;
		
	} //end getColor
	
	public List<Card> getHand()
	{
		return cards;
		
	} //end getHand
	
	public void setRoom(Card setRoom)
	{
		curRoom = setRoom;
		
	} //end setRoom
	
	public void setCoords(int setRow, int setCol)
	{
		curRow = setRow;
		curColumn = setCol;
		
	} //end setCoords
	
	public int getRoll()
	{
		return rollNum;
		
	} //end getRoll
	
	public void setSeen(ArrayList<Card> setSeenCards)
	{
		seenCards.clear();
		
		for(int i = 0; i < setSeenCards.size(); i++)
		{
			seenCards.add(setSeenCards.get(i));
			
		} //end for		
		
	} //end setSeen
	
	public void setHand(ArrayList<Card> setHand)
	{
		cards = setHand;
		
	} //end setHand
	
	public Set<Card> getSeen()
	{
		return seenCards;
		
	} //end getSeen
	
	public Color getColor()
	{
		return color;
		
	} //end getColor
	
	public void drawPerson(Graphics graphic, int cellSize)
	{
		graphic.setColor(color);
		
		graphic.fillRect(curColumn * cellSize, curRow * cellSize, cellSize, cellSize);
		
	} //end drawPerson
	
} //end Player
