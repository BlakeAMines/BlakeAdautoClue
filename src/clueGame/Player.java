package clueGame;

import java.util.Set;

public abstract class Player 
{
	private String name;
	private String color;
	
	private Set<Card> cards;
	
	private String curRoomName;	
	private int curRow;
	private int curColumn;
	
	public Player(String initName, String initColor, String initRoomName)
	{
		name = initName;
		color = initColor;
		curRoomName = initRoomName;
		
	} //end constructor
	
	public void updateHand(Card card)
	{
		
		
	} //end updateHand
	
	public String getName()
	{
		return "Null";
		
	} //end getName;
	
} //end Player
