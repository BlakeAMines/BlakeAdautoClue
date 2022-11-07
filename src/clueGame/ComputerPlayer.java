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
	
	public Solution makeSuggestion()
	{
		return new Solution(new Card("Empty", "Empty"), new Card("Empty", "Empty"), new Card("Empty", "Empty"));
		
	} //end makeAccusation
	
	public BoardCell selectTarget()
	{
		return new BoardCell(-1, -1, '!');
		
	} //eng selectTarget
	
} //end ComputerPlayer