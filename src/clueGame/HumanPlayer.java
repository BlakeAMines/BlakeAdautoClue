package clueGame;

import java.util.HashMap;
import java.util.Map;

public class HumanPlayer extends Player
{
	Map<Card, Player> humanSeen;
	
	public HumanPlayer(String initName, String initColor, String initRoomName) 
	{
		super(initName, initColor, initRoomName);
		
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

} //end HumanPlayer
