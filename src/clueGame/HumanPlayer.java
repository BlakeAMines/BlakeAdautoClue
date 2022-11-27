package clueGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player
{
	private Map<Card, Player> humanSeen;
	
	public HumanPlayer(String initName, String initColor, int xPos, int yPos) 
	{
		super(initName, initColor, xPos, yPos);
		
		humanSeen = new HashMap<>();

	} //end Constructor
	
	@Override
	public boolean isHuman()
	{
		return true;
		
	} //end getType
	
	public void updateSeen(Card card, Player player)
	{
		super.updateSeen(card);
		
		humanSeen.put(card, player);
		
	} //end updateSeen	
	
	public void setSeen(Map<Card, Player> seenMap)
	{
		humanSeen = seenMap;
		
	} //end setSeen
	
	public Map<Card, Player> getHumanSeen()
	{
		return humanSeen;
		
	} //end getHumanSeen

	@Override
	protected BoardCell selectTarget(Set<BoardCell> targets) 
	{
		return Board.getInstance().getCell(curRow, curColumn);
		
		//Add selection checks for targets on the board
		
	} //end selectTarget
	
	//This moves a human and sets their cell to occupied
	public void moveHuman(BoardCell cell)
	{
		if(!cell.isRoomCenter())
		{
			setFinished(true);
			
		} //end if
		
		cell.setOccupied(true);
		curRow = cell.getRow();
		curColumn = cell.getCol();
		
	} //end moveHuman
	
} //end HumanPlayer
