package clueGame;

abstract class Player 
{
	private String name;
	private String color;
	private char curRoomKey;
	private int curRow;
	private int curColumn;
	
	public Player(String initName, String initColor, char initRoomKey)
	{
		name = initName;
		color = initColor;
		curRoomKey = initRoomKey;
		
	} //end constructor
	
	abstract void updateHand(Card card);
	
} //end Player
