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
		
		//I learned how to do this from an online resource
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
		
		//This adds the board's clean deck as a copy to the player's unseen cards
		//I chose to do this instead of copying it directly because the player doesn't need to know what the actual deck is
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
	
	//This function takes a suggestion in and returns a card which disproves it if they can, or returns null otherwise
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
	
	public void drawPerson(Graphics graphic, int cellSize, int xOffset, int yOffset)
	{
		graphic.setColor(color);
		
		graphic.fillOval((curColumn * cellSize) + xOffset, (curRow * cellSize) + yOffset, cellSize, cellSize);
		
		graphic.setColor(Color.black);
		graphic.drawOval((curColumn * cellSize) + xOffset, (curRow * cellSize) + yOffset, cellSize, cellSize);
		
	} //end drawPerson
	
	public int getColumn()
	{
		return curColumn;
		
	} //end getColumn
	
	public int getRow()
	{
		return curRow;
		
	} //end getColumn
	
} //end Player
