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
import clueGame.Solution;

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
				Assert.assertEquals(curPlayer.getColor(), "Red");
				
			} //end nested if
			
			else if(curPlayer.getName().equals("Regular Person"))
			{
				foundHuman = true;
				
				Assert.assertEquals(curPlayer.getType(), "Human");
				Assert.assertEquals(curPlayer.getColor(), "Green");
				
			} //end nested else if
			
		} //end for
		
		Assert.assertTrue(foundComputer);
		Assert.assertTrue(foundHuman);
		
	} //end playerInfo
	
	@Test
	public void gameDeckTest()
	{
		boolean foundPlayer = false;
		boolean foundComputer = false;
		boolean foundWeapon = false;
		
		ArrayList<Card> testDeck = board.getGameDeck();
				
		for(int i = 0; i < testDeck.size(); i++)
		{
			if(testDeck.get(i).getName().equals("Regular Person"))
			{
				foundPlayer = true;
				
			} //end nested if			
			
			else if(testDeck.get(i).getName().equals("Glowing Person"))
			{
				foundComputer = true;
				
			} //end else if
			
			else if(testDeck.get(i).getName().equals("Priceless Dinosaur Tooth"))
			{
				foundWeapon = true;
				
			} //end nested else if
					
		} //end for 
		
		Assert.assertEquals(NUM_CARDS, testDeck.size());
		
		Assert.assertTrue(foundPlayer);
		Assert.assertTrue(foundComputer);
		Assert.assertTrue(foundWeapon);
				
	} //end gameDeckTest
	
	@Test
	public void testShuffle()
	{
		int equalsScore = 0;
		
		ArrayList<Card> testDeck = board.getGameDeck();
		
		board.shuffleDeck();
		
		for(int i = 0; i < testDeck.size(); i++)
		{
			if(testDeck.get(i).equals(board.getGameDeck().get(i)))
			{
				equalsScore++;
				
			} //end nested if
			
		} //end for
		
		//This ensures that no more than half of the deck slots are the same between two shuffled decks
		Assert.assertTrue(equalsScore < (testDeck.size() / 2));
		
	} //end testShuffle
	
	@Test
	public void testAnswer()
	{
		int solutionCount = 0;
		
		Solution testAnswer;
		
		testAnswer = board.getAnswer();
		
		for(int i = 0; i < 10000; i ++)
		{
			board.shuffleDeck();
			board.getAnswer();
			
			if(testAnswer.equals(board.getAnswer()))
			{
				solutionCount++;
				
			} //end nested if
			
		} //end for
		
		//There is a 1/342 chance that a predetermined solution will be picked
		//Given 10,000 trials, there should be about 31 of the same solution, so I chose to test for about half of that
		Assert.assertTrue(solutionCount >= 15);
				
	} //end testAnswer
		
} //end GameSetupTests
