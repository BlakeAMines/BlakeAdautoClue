package Tests;

import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;

class GameSetupTests 
{
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	
	private static Board board;
	
	@BeforeAll
	public static void gameSetup()
	{
		//Board is singleton
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		board.initialize();
		
	} //end gameSetup
	
} //end GameSetupTests
