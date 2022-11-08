package Tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ComputerAITest 
{
	private static Board board;
	
	public static final int NUM_CARDS = 21;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_WEAPONS = 6;
	
	private static ArrayList<Card> tempDeck;
	
	private static Card seenRoom;
	private static Card seenPerson;
	private static Card seenWeapon;
		
	private static Card unseenRoom;
	private static Card unseenPerson;
	private static Card unseenPerson2;
	private static Card unseenWeapon;
	
	private static ComputerPlayer testPlayer;
	
	@BeforeAll
	public static void gameSetup()
	{		
		//Board is singleton
		board = Board.getInstance();
	
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	
		board.initialize();

		//This sets seen cards to known values
		seenRoom = board.getCleanDeck().get(0);
		seenPerson = board.getCleanDeck().get(NUM_ROOMS);
		seenWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS);
		
		//This sets unseen cards to known values
		unseenRoom = board.getCleanDeck().get(1);
		unseenPerson = board.getCleanDeck().get(NUM_ROOMS + 1);
		unseenPerson2 = board.getCleanDeck().get(NUM_ROOMS + 2);
		unseenWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS + 1);
			
		tempDeck = new ArrayList<>();
		
		//This creates a controlled gameDeck to keep predictable values and comparisons
		tempDeck.add(seenRoom);
		tempDeck.add(seenPerson);
		tempDeck.add(seenWeapon);
		tempDeck.add(unseenRoom);
		tempDeck.add(unseenPerson);
		tempDeck.add(unseenPerson2);
		tempDeck.add(unseenWeapon);
		
		board.setGameDeck(tempDeck);
		board.loadCleanDeck();
		board.shuffleDeck();
		
		//This creates a new player with a known hand
		testPlayer = new ComputerPlayer("TestName", "TestColor", "TestRoom");
		
		testPlayer.updateSeen(seenRoom);
		testPlayer.updateSeen(seenPerson);
		testPlayer.updateSeen(seenWeapon);
		
	} //end gameSetup
	
	//This ensures that the player always includes their current room when making a suggestion
	@Test
	public void testRoom()
	{
		testPlayer.setRoom(seenRoom);
		
		Assert.assertTrue(testPlayer.makeSuggestion().getRoom().equals(seenRoom));
		
	} //end testCreate
	
	//This ensures that the player chooses a person who they have not seen yet and chooses randomly when they have multiple options
	@Test
	public void testPerson()
	{
		//These counters are used to check that both of the available selections are taken randomly
		int foundCount1 = 0;
		int foundCount2 = 0;
		
		testPlayer.setRoom(seenRoom);
		
		Card tempPerson = null;
		
		for(int i = 0; i < 10; i++)
		{
			if(testPlayer.makeSuggestion().getPerson().equals(unseenPerson))
			{
				foundCount1++;
				
			} //end nested if
			
			if(testPlayer.makeSuggestion().getPerson().equals(unseenPerson2))
			{
				foundCount2++;
				
			} //end nested if
			
			tempPerson = testPlayer.makeSuggestion().getPerson();
			
		} //end for
			
		Assert.assertTrue(foundCount1 >= 2 && foundCount1 <= 8);
		Assert.assertTrue(foundCount2 >= 2 && foundCount2 <= 8);	
		
		Assert.assertTrue(tempPerson.equals(unseenPerson) || tempPerson.equals(unseenPerson2));
		
	} //end testCreate
	
	//This test is the same as testing for people but ensures that if only one weapon is available, it is selected
	@Test
	public void testWeapon()
	{	
		testPlayer.setRoom(seenRoom);
				
		Assert.assertTrue(testPlayer.makeSuggestion().getWeapon().equals(unseenWeapon));
		
	} //end testCreate
	
	//This ensures that if an unseen room is available, it is always selected
	@Test
	public void unseenRoomTargetsTest()
	{
		testPlayer.setCoords(3, 18);
		
		board.calcTargets(board.getCell(3, 18), 1);
		
		Assert.assertTrue(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(1, 20)));
		
	} //end unseenTargetsTest
	
	//This ensures that rooms are selected randomly if they've been seen before
	@Test
	public void seenRoomTargetsTest()
	{
		int hadRoom = 0;
		
		testPlayer.setCoords(3, 4);
		
		board.calcTargets(board.getCell(3, 4), 1);
		
		for(int i = 0; i < 30; i++)
		{
			if(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(2, 1)))
			{
				hadRoom++;
				
			} //end nested if
			
		} //end for
		
		Assert.assertTrue(hadRoom >= 1 && hadRoom <= 20);
		
	} //end seenRoomTargetsTest
	
	//This tests for the random selections of cells when no rooms are available
	@Test
	public void randomTargetsTest()
	{
		int cellCounter1 = 0;
		int cellCounter2 = 0;
		int cellCounter3 = 0;
		
		testPlayer.setCoords(5, 7);
		
		board.calcTargets(board.getCell(5, 7), 4);
		
		for(int i = 0; i < 45; i++)
		{
			if(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(4, 4)))
			{
				cellCounter1++;
				
			} //end nested if
			
			if(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(4, 10)))
			{
				cellCounter2++;
				
			} //end nested if
			
			if(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(6, 10)))
			{
				cellCounter3++;
				
			} //end nested if
			
		} //end for
		
		Assert.assertTrue(cellCounter1 >= 1 && cellCounter1 <= 30);
		Assert.assertTrue(cellCounter2 >= 1 && cellCounter1 <= 30);
		Assert.assertTrue(cellCounter3 >= 1 && cellCounter1 <= 30);
		
	} //end randomTargetsTest
	
} //end ComputerAITest
