package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

class FileInitTests
{
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 22;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() 
	{
		// Board is singleton
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		board.initialize();
		
	} //end setUp
	
	@Test
	public void roomNamesTest()
	{
		BoardCell testCell = board.getCell(0, 20);
		
		//Tests a room using two different methods
		Assert.assertEquals("Tower", board.getRoom('T').getName());
		Assert.assertEquals("Tower", board.getRoom(testCell).getName());
		
		//Tests two rooms with starting letters to ensure they are distinct
		Assert.assertEquals("Library", board.getRoom('B').getName());
		Assert.assertEquals("Lounge", board.getRoom('L').getName());
		
		//Tests for unused space and hallways
		Assert.assertEquals("Walkway", board.getRoom('W').getName());
		Assert.assertEquals("Unused", board.getRoom('X').getName());
		
		//Tests for room names with spaces in them
		Assert.assertEquals("Dining Hall", board.getRoom('D').getName());
		Assert.assertEquals("Garden Room", board.getRoom('G').getName());
		
	} //end roomNamesTest
		
	@Test
	public void boardDimensionsTest()
	{
		System.out.println("Rows " + board.getNumRows());
		Assert.assertEquals(board.getNumRows(), NUM_ROWS);
		Assert.assertEquals(board.getNumColumns(), NUM_COLUMNS);
		
		//I will add another test when I have a variable for the input of the ClueSetup.txt
				
	} //end boardDimensionsTest
	
	@Test
	public void doorDirectionsTest()
	{		
		//Tests doorCells directly next to each other which point different ways
		//This ensures that the board is aligned as expected
		BoardCell doorCell = board.getCell(5, 0);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.UP, doorCell.getDoorDirection());
		
		doorCell = board.getCell(5, 1);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.DOWN, doorCell.getDoorDirection());
		
		//Tests doorCells directly above and below each other which point different ways
		//This ensures that the board is aligned as expected
		doorCell = board.getCell(2, 4);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.RIGHT, doorCell.getDoorDirection());
		
		doorCell = board.getCell(3, 4);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.LEFT, doorCell.getDoorDirection());
		
		doorCell = board.getCell(9, 14);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.LEFT, doorCell.getDoorDirection());
		
	} //end doorDirectionsTest
	
	@Test
	public void numDoorsTest()
	{
		BoardCell curCell;
		
		int numDoors = 0;
		
		//Iterates through the board and counts a door each time a cell is a door
		for(int i = 0; i < NUM_ROWS; i++)
		{
			for(int j = 0; j < NUM_COLUMNS; j++)
			{
				curCell = board.getCell(i, j);
				
				if (curCell.isDoorway())
				{
					numDoors++;
					
				} //end nested if
				
			} //end nested for
			
		} //end for
		
		Assert.assertEquals(19, numDoors);
		
	} //end numDoorsTest
	
	@Test
	public void numRooms()
	{
		BoardCell curCell;
		
		int numRooms = 0;
		
		//Iterates through the board and counts a door each time a cell is a door
		for(int i = 0; i < NUM_ROWS; i++)
		{
			for(int j = 0; j < NUM_COLUMNS; j++)
			{
				curCell = board.getCell(i, j);
				
				if (curCell.isLabel())
				{
					numRooms++;
					
				} //end nested if
				
			} //end nested for
			
		} //end for
		
		Assert.assertEquals(9, numRooms);
		
	} //end numRooms
	
	@Test
	public void testRooms()
	{
		BoardCell cell = board.getCell(1, 1);
		Room room = board.getRoom(cell);
		
		//Ensures that this space is a room
		assertTrue(room != null);
		
		assertEquals("Observatory", room.getName());
		assertFalse(cell.isLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		
		//Center of room test
		cell = board.getCell(2, 1);
		room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals(room.getName(), "Observatory") ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		//Secret Passage test
		cell = board.getCell(0, 0);
		room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals( room.getName(), "Observatory") ;
		assertTrue(cell.getSecretPassage() == 'T');
		
		//Walkway test
		cell = board.getCell(25, 21);
		room = board.getRoom(cell);
		assertTrue( room != null);
		assertEquals(room.getName(), "Unused" );
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());
		
		//Unused test
		cell = board.getCell(0, 11);
		room = board.getRoom(cell) ;
		assertTrue( room != null);
		assertEquals( room.getName(), "Walkway");
		assertFalse( cell.isRoomCenter());
		assertFalse( cell.isLabel());
		
	} //end testRooms
	
} //end FileInitTest
