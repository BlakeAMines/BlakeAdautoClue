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
	private static Card unseenWeapon;
	private static Card unseenWeapon2;
	
	private static ComputerPlayer testPlayer;
	
	@BeforeAll
	public static void gameSetup()
	{		
		//Board is singleton
		board = Board.getInstance();
	
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	
		board.initialize();
		
		seenRoom = board.getCleanDeck().get(0);
		seenPerson = board.getCleanDeck().get(NUM_ROOMS);
		seenWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS);
		
		unseenRoom = board.getCleanDeck().get(1);
		unseenPerson = board.getCleanDeck().get(NUM_ROOMS + 1);
		unseenWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS + 1);
		unseenWeapon2 = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS + 2);
			
		tempDeck = new ArrayList<>();
		
		tempDeck.add(seenRoom);
		tempDeck.add(seenPerson);
		tempDeck.add(seenWeapon);
		tempDeck.add(unseenRoom);
		tempDeck.add(unseenPerson);
		tempDeck.add(unseenWeapon);

		board.setGameDeck(tempDeck);
		board.loadCleanDeck();
		board.shuffleDeck();
		
		testPlayer = new ComputerPlayer("TestName", "TestColor", "TestRoom");
		
		testPlayer.updateSeen(seenRoom);
		testPlayer.updateSeen(seenPerson);
		testPlayer.updateSeen(seenWeapon);
		
	} //end gameSetup
	
	@Test
	public void testRoom()
	{
		testPlayer.setRoom(seenRoom);
		
		Assert.assertTrue(testPlayer.makeSuggestion().getRoom().equals(seenRoom));
		
	} //end testCreate
	
	@Test
	public void testPerson()
	{
		testPlayer.setRoom(seenRoom);
		
		Assert.assertTrue(testPlayer.makeSuggestion().getPerson().equals(unseenPerson));
		
	} //end testCreate
	
	@Test
	public void testWeapon()
	{
		int foundCount1 = 0;
		int foundCount2 = 0;
		
		testPlayer.setRoom(seenRoom);
		
		Card tempWeapon = null;
		
		for(int i = 0; i < 10; i++)
		{
			if(testPlayer.makeSuggestion().getWeapon().equals(unseenWeapon))
			{
				foundCount1++;
				
			} //end nested if
			
			if(testPlayer.makeSuggestion().getWeapon().equals(unseenWeapon))
			{
				foundCount2++;
				
			} //end nested if
			
			tempWeapon = testPlayer.makeSuggestion().getWeapon();
			
		} //end for
			
		Assert.assertTrue(foundCount1 >= 2 && foundCount2 >= 2);
		Assert.assertTrue(foundCount1 >= 2 && foundCount2 >= 2);	
		
		Assert.assertTrue(tempWeapon.equals(unseenWeapon) || tempWeapon.equals(unseenWeapon2));
		
	} //end testCreate
	
	@Test
	public void unseenRoomTargetsTest()
	{
		testPlayer.setCoords(3, 18);
		
		board.calcTargets(board.getCell(3, 18), 1);
		
		Assert.assertTrue(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(1, 20)));
		
	} //end unseenTargetsTest
	
	@Test
	public void seenRoomTargetsTest()
	{
		int hadRoom = 0;
		
		testPlayer.setCoords(3, 4);
		
		board.calcTargets(board.getCell(3, 4), 1);
		
		for(int i = 0; i < 20; i++)
		{
			if(testPlayer.selectTarget(board.getTargets()).equals(board.getCell(2, 1)))
			{
				hadRoom++;
				
			} //end nested if
			
		} //end for
		
		Assert.assertTrue(hadRoom >= 4 && hadRoom <= 10);
		
	} //end seenRoomTargetsTest
	
	@Test
	public void randomTargetsTest()
	{
		int cellCounter1 = 0;
		int cellCounter2 = 0;
		int cellCounter3 = 0;
		
		testPlayer.setCoords(5, 7);
		
		board.calcTargets(board.getCell(5, 7), 4);
		
		for(int i = 0; i < 40; i++)
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
		
		Assert.assertTrue(cellCounter1 >= 5 && cellCounter1 <= 20);
		Assert.assertTrue(cellCounter2 >= 5 && cellCounter1 <= 20);
		Assert.assertTrue(cellCounter3 >= 5 && cellCounter1 <= 20);
		
	} //end randomTargetsTest
	
} //end ComputerAITest
