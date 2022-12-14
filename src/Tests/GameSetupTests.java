package Tests;

import clueGame.GameControlPanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
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
			System.out.println(testList.get(i).getName());
			//These if and else if statements check for certain characters which should be in the playerList
			if(testList.get(i).getName().equals("Frightening Person"))
			{
				foundComputer = true;
				
				//These assertions check that the player's information is read in correctly
				Assert.assertFalse(testList.get(i).isHuman());
				Assert.assertEquals(testList.get(i).getColor(), Color.RED);
				
			} //end nested if
			
			else if(testList.get(i).getName().equals("Regular Person"))
			{
				foundHuman = true;
				
				//These assertions check that the player's information is read in correctly
				Assert.assertTrue(testList.get(i).isHuman());
				Assert.assertEquals(testList.get(i).getColor(), Color.GREEN);
				
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
			//These if and else if statements check for certain cards in the deck
			//They ensure that specific weapons, rooms, and different kinds of players are included
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
		
		ArrayList<Card> testDeck = board.getCleanDeck();
		
		//After shuffling the deck, this compares the previous deck and the shuffled deck
		//This ensures that the cards are diffferent
		for(int i = 0; i < board.getGameDeck().size(); i++)
		{
			if(testDeck.get(i).equals(board.getGameDeck().get(i)))
			{
				equalsScore++;
				
			} //end nested if
			
		} //end for
		
		System.out.println(equalsScore);
		
		//This ensures that no more than half of the deck slots are the same between two shuffled decks
		Assert.assertTrue(equalsScore < (board.getGameDeck().size() / 2));
		
	} //end testShuffle
	
	@Test
	public void testAnswer()
	{
		//solutionCount keeps track of how many times a randomly generated answer matches a predetermined one
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
		
		System.out.println(solutionCount);
		
		//There is a 1/342 chance that a predetermined solution will be picked
		//Given 10,000 trials, there should be about 31 of the same solution, so I chose to test for about half of that
		//The 100 ensures that the random chance is happening randomly and not every time
		Assert.assertTrue(solutionCount >= 15 && solutionCount < 100);
				
	} //end testAnswer
	
	@Test
	public void testDistribute()
	{
		Set<Card> testSet = new HashSet<>();
		
		board.initialize();
		
		boolean foundHuman = false;
		boolean foundComputer = false;
		boolean foundRoom = false;
		boolean foundWeapon = false;
		
		
		List<Player> testPlayers = board.getPlayerList();

		//This runs through the playerList, then each player's hand to ensure cards are dealt properly
		for(int i = 0; i < testPlayers.size(); i++)
		{			
			for(int j = 0; j < testPlayers.get(i).getHand().size(); j++)
			{
				//This adds a player's hand to another set to ensure cards are dealt once
				testSet.add(testPlayers.get(i).getHand().get(j));
				
				//Found the human card handed out
				if(testPlayers.get(i).getHand().get(j).getName().equals("Regular Person"))
				{
					foundHuman = true;
					
				} //end nested if
				
				//Found a computer card handed out
				else if(testPlayers.get(i).getHand().get(j).getName().equals("Friendly Person"))
				{
					foundComputer = true;
					
				} //end nested else if
				
				//Found a room card handed out
				else if(testPlayers.get(i).getHand().get(j).getName().equals("Garden Room"))
				{
					foundRoom = true;
					
				} //end nested else if
				
				//Found a weapon card handed out
				else if(testPlayers.get(i).getHand().get(j).getName().equals("Metal Ball"))
				{
					foundWeapon = true;
					
				} //end nested else if
				
			} //end nested for
						
			Assert.assertTrue(testPlayers.get(i).getHand().size() <= (NUM_CARDS/NUM_PLAYERS + 1) && testPlayers.get(i).getHand().size() >= (NUM_CARDS/NUM_PLAYERS) );
						
		} //end for
			
		//Because Sets cannot have duplicates, this ensures that no card was dealt twice
		Assert.assertEquals(testSet.size(), NUM_CARDS);
		
		Assert.assertTrue(foundHuman);	
		Assert.assertTrue(foundComputer);	
		Assert.assertTrue(foundRoom);	
		Assert.assertTrue(foundWeapon);	
							
	} //end testDistribute
		
} //end GameSetupTests