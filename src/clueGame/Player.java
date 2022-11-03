package clueGame;

import java.util.Set;

public abstract class Player 
{
	private String name;
	private String color;
	
	private Set<Card> cards;
	
	private char curRoomKey;	
	private int curRow;
	private int curColumn;
	
	public Player(String initName, String initColor, char initRoomKey)
	{
		name = initName;
		color = initColor;
		curRoomKey = initRoomKey;
		
	} //end constructor
	
	public void updateHand(Card card)
	{
		
		
	} //end updateHand
	
	public String getName()
	{
		return "Null";
		
	} //end getName;
	
} //end Player
