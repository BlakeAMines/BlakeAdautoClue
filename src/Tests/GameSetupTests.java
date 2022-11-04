package Tests;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests 
{
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_CARDS = 21;
	
	private static Board board;
	
	@BeforeAll
	public static void gameSetup()
	{		
		//Board is singleton
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		board.initialize();
		
	} //end gameSetup
	
	@Test
	public void playerInfo()
	{
		Set<Player> testList = board.getPlayerList();
		Assert.assertEquals(6, testList.size());
		
		boolean foundComputer = false;
		boolean foundHuman = false;
		
		for(Player curPlayer : testList)
		{
			if(curPlayer.getName().equals("Frightening Person"))
			{
				foundComputer = true;
				
				Assert.assertEquals(curPlayer.getType(), "Computer");
				
			} //end nested if
			
			else if(curPlayer.getName().equals("Regular Person"))
			{
				foundHuman = true;
				
				Assert.assertEquals(curPlayer.getType(), "Human");
				
			} //end nested else if
			
		} //end for
		
		Assert.assertTrue(foundComputer);
		Assert.assertTrue(foundHuman);
		
	} //end playerInfo
	
	@Test
	public void gameDeckTest()
	{
		ArrayList<Card> testDeck = board.getGameDeck();
				
		Assert.assertEquals(NUM_CARDS, testDeck.size());
		
	} //end gameDeckTest
	
} //end GameSetupTests
