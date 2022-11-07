package Tests;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameplayTests 
{
	private static Board board;
	
	public static final int NUM_CARDS = 21;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	
	private static Player testPlayer;
	
	private static Card goodRoom;
	private static Card goodPerson;
	private static Card goodWeapon;
	
	private static Card badRoom;
	private static Card badPerson;
	private static Card badWeapon;
	
	@BeforeAll
	public static void gameSetup()
	{		
		//Board is singleton
		board = Board.getInstance();
	
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	
		board.initialize();
				
		goodRoom = board.getCleanDeck().get(0);
		goodPerson = board.getCleanDeck().get(NUM_ROOMS);
		goodWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS);
		
		badRoom = board.getCleanDeck().get(1);
		badPerson = board.getCleanDeck().get(NUM_ROOMS + 1);
		badWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS + 1);
		
		testPlayer = new ComputerPlayer("Name", "PieceColor", "StartRoom");
				
		testPlayer.updateHand(goodRoom);
		testPlayer.updateHand(goodPerson);
		testPlayer.updateHand(goodWeapon);
		testPlayer.updateHand(badRoom);
		
		Solution testAnswer = new Solution(goodRoom, goodPerson, goodWeapon);
		board.setAnswer(testAnswer);
		
	} //end gameSetup
	
	@Test
	public void goodAccusationTest()
	{
		Solution testAccuse = new Solution(goodRoom, goodPerson, goodWeapon);

		Assert.assertTrue(board.checkAccusation(testAccuse));
		
	} //end checkAccusationTest
	
	@Test
	public void badRoomAccuseTest()
	{
		Solution testAccuse = new Solution(badRoom, goodPerson, goodWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
	} //end badRoomAccuseTest
	
	@Test
	public void badPersonAccuseTest()
	{
		Solution testAccuse = new Solution(goodRoom, badPerson, goodWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
	} //end badPersonAccuseTest
	
	@Test
	public void badWeaponAccuseTest()
	{		
		Solution testAccuse = new Solution(goodRoom, goodPerson, badWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
				
	} //end badWeaponAccuseTest
	
	@Test
	public void testDisproveOne()
	{
		Solution testSuggest = new Solution(goodRoom, badPerson, badWeapon);
		
		Assert.assertTrue(testPlayer.disproveSuggestion(testSuggest).equals(goodRoom));
		
	} //end testDisprove
	
	@Test
	public void testDisproveNone()
	{
		Solution testSuggest = new Solution(badRoom, badPerson, badWeapon);
		
		Assert.assertNull(testPlayer.disproveSuggestion(testSuggest));
		
	} //end testDisprove
	
	@Test
	public void testDisproveThree()
	{
		Solution testSuggest = new Solution(goodRoom, goodPerson, goodWeapon);
		
		Assert.assertTrue(false);
		
	} //end testDisprove
	
} //end GameplayTests