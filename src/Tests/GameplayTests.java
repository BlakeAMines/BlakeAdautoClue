package Tests;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.Solution;

public class GameplayTests 
{
	private static Board board;
	
	private static final int NUM_ROOMS = 9;
	private static final int NUM_PEOPLE = 6;
	private static final int NUM_WEAPONS = 6;
	
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
		Card goodWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PEOPLE);
				
		Card badRoom = board.getCleanDeck().get(1);
		Card badPerson = board.getCleanDeck().get(NUM_ROOMS + 1);
		Card badWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PEOPLE + 1);
		
		System.out.println(goodPerson.getName());
		
		System.out.println(goodWeapon.getName());
		
	} //end checkAccusationTest

} //end GameplayTests
