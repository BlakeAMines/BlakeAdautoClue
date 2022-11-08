package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player
{
	public ComputerPlayer(String initName, String initColor, String initRoomName) 
	{
		super(initName, initColor, initRoomName);

	} //end Constructor	
	
	@Override
	public boolean isHuman()
	{
		return false;
		
	} //end getType
	
	public Solution makeSuggestion()
	{		
		Card suggestPerson = null;
		
		boolean foundPerson = false;
		boolean foundWeapon = false;
		
		int random;
		
		while(!foundPerson)
		{
			random = rand.nextInt(unseenCards.size());
			
			if(unseenCards.get(random).getType().equals(CardType.PERSON))
			{
				suggestPerson = unseenCards.get(random);
				foundPerson = true;
				
			} //end if
			
		} //end while
		
		return new Solution(curRoom, suggestPerson, new Card("Empty", "Empty"));
		
	} //end makeAccusation
	
	public BoardCell selectTarget()
	{
		return new BoardCell(-1, -1, '!');
		
	} //eng selectTarget
	
} //end ComputerPlayer