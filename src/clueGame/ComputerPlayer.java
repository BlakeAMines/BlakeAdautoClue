package clueGame;

public class ComputerPlayer extends Player
{
	public ComputerPlayer(String initName, String initColor, String initRoomName) 
	{
		super(initName, initColor, initRoomName);

	} //end Constructor	
	
	@Override
	public String getType()
	{
		return "Computer";
		
	} //end getType
	
} //end ComputerPlayer