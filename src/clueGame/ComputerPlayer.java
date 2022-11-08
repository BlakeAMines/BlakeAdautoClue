package clueGame;

import java.util.ArrayList;
import java.util.Set;

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
		Card suggestWeapon = null;
		
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
				
			} //end nested if
			
		} //end while
		
		while(!foundWeapon)
		{
			random = rand.nextInt(unseenCards.size());
			
			if(unseenCards.get(random).getType().equals(CardType.WEAPON))
			{
				suggestWeapon = unseenCards.get(random);
				foundWeapon = true;
				
			} //end nested if					
			
		} //end while
				
		return new Solution(curRoom, suggestPerson, suggestWeapon);
		
	} //end makeAccusation
	
	public BoardCell selectTarget(Set<BoardCell> targets)
	{
		BoardCell pickCell = null;
		
		for(BoardCell curCell : targets)
		{
			if(curCell.isRoomCenter())
			{
				return curCell;
				
			} //end nested if
			
		} //end for
		
		return new BoardCell(-1, -1, '!');
		
	} //eng selectTarget
	
} //end ComputerPlayer