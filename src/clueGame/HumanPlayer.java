package clueGame;

public class HumanPlayer extends Player
{
	public HumanPlayer(String initName, String initColor, String initRoomName) 
	{
		super(initName, initColor, initRoomName);

	} //end Constructor
	
	@Override
	public String getType()
	{
		return "Human";
		
	} //end getType

} //end HumanPlayer
