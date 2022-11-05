package Tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
		List<Player> testList = board.getPlayerList();
		Assert.assertEquals(6, testList.size());
		
		boolean foundComputer = false;
		boolean foundHuman = false;
		
		//This runs through the gameBoard's playerList and ensures that predetermined characters are in the txts
		for(int i = 0; i < testList.size(); i++)
		{
			if(testList.get(i).getName().equals("Frightening Person"))
			{
				foundComputer = true;
				
				Assert.assertEquals(testList.get(i).getType(), "Computer");
				Assert.assertEquals(testList.get(i).getColor(), "Red");
				
			} //end nested if
			
			else if(testList.get(i).getName().equals("Regular Person"))
			{
				foundHuman = true;
				
				Assert.assertEquals(testList.get(i).getType(), "Human");
				Assert.assertEquals(testList.get(i).getColor(), "Green");
				
			} //end nested else if
			
		} //end for
		
		//If the people are found, these result as true
		Assert.assertTrue(foundComputer);
		Assert.assertTrue(foundHuman);
		
	} //end playerInfo
	
	@Test
	public void gameDeckTest()
	{
		boolean foundPlayer = false;
		boolean foundComputer = false;
		boolean foundWeapon = false;
		boolean foundRoom = false;
		
		ArrayList<Card> testDeck = board.getGameDeck();
				
		//This runs through the board's deck of cards to ensure that preselected cards are found
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
			
			else if(testDeck.get(i).getName().equals("Observatory"))
			{
				foundRoom = true;
				
			} //end else if
					
		} //end for 
		
		//This ensures that the right amount of cards are added to the deck
		Assert.assertEquals(NUM_CARDS, testDeck.size());
		
		//This tests if each of the predetermined cards are found in the deck
		Assert.assertTrue(foundPlayer);
		Assert.assertTrue(foundComputer);
		Assert.assertTrue(foundWeapon);
		Assert.assertTrue(foundRoom);
				
	} //end gameDeckTest
	
	@Test
	public void testShuffle()
	{
		int equalsScore = 0;
		
		ArrayList<Card> testDeck = new ArrayList<>();
		
		//This sets the current deck to a copy of the board's deck before shuffling
		for(int i = 0; i < board.getGameDeck().size(); i++)
		{
			testDeck.add(board.getGameDeck().get(i));

		} //end for
		
		board.shuffleDeck();
		
		//After shuffling the deck, this compares the previous deck and the shuffled deck
		//This ensures that the cards are diffferent
		for(int i = 0; i < board.getGameDeck().size(); i++)
		{
			if(testDeck.get(i).equals(board.getGameDeck().get(i)))
			{
				equalsScore++;
				
			} //end nested if
			
		} //end for
		
		//This ensures that no more than half of the deck slots are the same between two shuffled decks
		Assert.assertTrue(equalsScore < (board.getGameDeck().size() / 2));
		
	} //end testShuffle
	
	@Test
	public void testAnswer()
	{
		int solutionCount = 0;
		
		Solution testAnswer;
		
		testAnswer = board.getAnswer();		
				
		for(int i = 0; i < 10000; i ++)
		{
			board.initialize();
				
			if(testAnswer.equals(board.getAnswer()))
			{
				solutionCount++;
				
			} //end nested if
			
		} //end for
		
		//There is a 1/342 chance that a predetermined solution will be picked
		//Given 10,000 trials, there should be about 31 of the same solution, so I chose to test for about half of that
		Assert.assertTrue(solutionCount >= 15);
				
	} //end testAnswer
	
	@Test
	public void testDistribute()
	{
		List<Player> testList = board.getPlayerList();
				
		int answerCount = 0;
		
		for(int i = 0; i < testList.size(); i++)
		{
			Assert.assertTrue(testList.get(i).getHand().size() <= (NUM_CARDS/NUM_PLAYERS + 1) && testList.get(i).getHand().size() >= (NUM_CARDS/NUM_PLAYERS) );
			
		} //end for
						
	} //end testDistribute
		
} //end GameSetupTests