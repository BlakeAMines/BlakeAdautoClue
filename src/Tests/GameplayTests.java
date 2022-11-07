package Tests;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Solution;

public class GameplayTests 
{
	private static Board board;
	
	public static final int NUM_CARDS = 21;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;

	@BeforeAll
	public static void gameSetup()
	{		
		//Board is singleton
		board = Board.getInstance();
	
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	
		board.initialize();
		
	} //end gameSetup
	
	@Test
	public void checkAccusationTest()
	{
		Card goodRoom = board.getCleanDeck().get(0);
		Card goodPerson = board.getCleanDeck().get(NUM_ROOMS);
		Card goodWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS);
		
		Card badRoom = board.getCleanDeck().get(1);
		Card badPerson = board.getCleanDeck().get(NUM_ROOMS + 1);
		Card badWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS + 1);
		
		Solution testAccuse = new Solution(goodRoom, goodPerson, goodWeapon);
		Solution testAnswer = new Solution(goodRoom, goodPerson, goodWeapon);
		
		board.setAnswer(testAnswer);
		
		Assert.assertTrue(board.checkAccusation(testAccuse));
		
		testAccuse = new Solution(badRoom, goodPerson, goodWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
		testAccuse = new Solution(goodRoom, badPerson, goodWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
		testAccuse = new Solution(goodRoom, goodPerson, badWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
	} //end checkAccusationTest
	
} //end GameplayTests