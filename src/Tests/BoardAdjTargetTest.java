package Tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest 
{
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() 
	{
		board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		

		board.initialize();
	}

	//These tests are TURQUOISE in the planning document
	@Test
	public void testAdjacenciesRooms()
	{
		//Study adjacencies with secret passage
		Set<BoardCell> testList = board.getAdjList(21, 3);
		
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(18, 1)));
		Assert.assertTrue(testList.contains(board.getCell(20, 6)));
		
		//Garden Room adjacencies
		testList = board.getAdjList(18, 14);
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(21, 15)));
		
		//Lounge, should include adjacency back to study
		//Side note: this also ensures that I updated the csv because I added a door
		testList = board.getAdjList(2, 7);
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(21, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 10)));
		
	} //end testAdjacenciesRooms

	//These tests are TURQUOISE in the planning document
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(20, 6);		
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(21, 3)));
		Assert.assertTrue(testList.contains(board.getCell(19, 6)));
		
		//Testing a doorway with all four adjacencies
		testList = board.getAdjList(9, 14);
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(9, 12)));
		Assert.assertTrue(testList.contains(board.getCell(8, 14)));
		Assert.assertTrue(testList.contains(board.getCell(9, 15)));
		
		testList = board.getAdjList(2, 10);
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(2, 7)));
		
	} //end testAdjacencyDoor
	
	//These are YELLOW in the planning document
	@Test
	public void testAdjacencyWalkways()
	{
		Set<BoardCell> testList = board.getAdjList(15, 4);
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(15, 5)));
		Assert.assertTrue(testList.contains(board.getCell(14, 4)));
		
		testList = board.getAdjList(10, 18);
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(9, 18)));
		
		testList = board.getAdjList(23, 16);
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(23, 15)));
		Assert.assertTrue(testList.contains(board.getCell(22, 16)));
		
	} //end testAdjacencyWalkways

	//These are ORANGE in the planning document
	@Test
	public void testTargetsTower() 
	{
		//One step
		board.calcTargets(board.getCell(1, 20), 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 18)));
		
		//Through secret passage
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(1, 20), 3);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 16)));
		Assert.assertTrue(targets.contains(board.getCell(5, 18)));	
		
		//Through secret passage
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(1, 20), 4);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(4, 16)));
		Assert.assertTrue(targets.contains(board.getCell(6, 18)));	
		
		//Through secret passage
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));	
	
	} //end testTargetsTower
	
	//These are ORANGE in the planning document
	@Test
	public void testTargetsLibrary() 
	{
		//One step
		board.calcTargets(board.getCell(9, 1), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(5, 1)));
		Assert.assertTrue(targets.contains(board.getCell(11, 3)));
		
		//Three steps to ensure that the door to the Observatory adjacent to the top doorway is included
		board.calcTargets(board.getCell(9, 1), 3);
		targets= board.getTargets();
		Assert.assertEquals(10, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));	
		Assert.assertTrue(targets.contains(board.getCell(5, 3)));	
		Assert.assertTrue(targets.contains(board.getCell(12, 4)));	
		Assert.assertTrue(targets.contains(board.getCell(16, 0)));	
		
		//Ensures the room is not adjacent to any of its own pieces
		Assert.assertFalse(targets.contains(board.getCell(8, 1)));
		Assert.assertFalse(targets.contains(board.getCell(10, 1)));
		
	} //end testTargetsLibrary

	//This one is ORANGE in the planning document
	@Test
	public void testTargetsAtDoor() 
	{
		//One step and ensures unused spaces are not in target list
		board.calcTargets(board.getCell(23, 12), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(21, 9)));
		Assert.assertTrue(targets.contains(board.getCell(23, 13)));
		
		//Six steps and ensures that room is still in target list
		board.calcTargets(board.getCell(23, 12), 6);
		targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(21, 9)));
		Assert.assertTrue(targets.contains(board.getCell(18, 14)));
		Assert.assertTrue(targets.contains(board.getCell(22, 15)));
		
	} //end testTargetsAtDoor
	
	//This one is ORANGE in the planning document 
	//Tests to include doors and rooms
	@Test
	public void testTargetsInWalkway1() 
	{
		//Three steps
		board.calcTargets(board.getCell(5, 4), 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(5, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 4)));
		Assert.assertTrue(targets.contains(board.getCell(8, 4)));
		
		//Room is included in targets
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		
		//But it takes an extra step to walk through doorway
		Assert.assertFalse(targets.contains(board.getCell(2, 7)));
		
		//Ensures movement over doorway cells is allowed
		Assert.assertTrue(targets.contains(board.getCell(2, 4)));
		
	} //end testTargetsInWalkway1

	//This one is RED in the planning document
	@Test
	public void testTargetsOccupied() 
	{
		board.getCell(16, 13).setOccupied(true);
		
		//Ensures the blocked room is not in targets
		board.calcTargets(board.getCell(16, 12), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		
		Assert.assertTrue(targets.contains(board.getCell(15, 11)));
		Assert.assertTrue(targets.contains(board.getCell(17, 11)));
		
		Assert.assertFalse(targets.contains(board.getCell(16, 13)));
		Assert.assertFalse(targets.contains(board.getCell(18, 14)));
		
		//Another spot
		board.getCell(4, 18).setOccupied(true);
		board.calcTargets(board.getCell(5, 18), 3);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		
		Assert.assertTrue(targets.contains(board.getCell(8, 18)));
		Assert.assertFalse(targets.contains(board.getCell(4, 18)));
		Assert.assertFalse(targets.contains(board.getCell(3, 18)));
		Assert.assertFalse(targets.contains(board.getCell(1, 20)));
		
		//Another cell near the occupied cell
		board.calcTargets(board.getCell(3, 16), 3);
		targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		
		Assert.assertTrue(targets.contains(board.getCell(1, 20)));
		Assert.assertFalse(targets.contains(board.getCell(3, 18)));
		Assert.assertFalse(targets.contains(board.getCell(4, 18)));
		Assert.assertFalse(targets.contains(board.getCell(5, 20)));
		
		//If these are left occupied, it can affect other tests
		board.getCell(16, 13).setOccupied(false);
		board.getCell(4, 18).setOccupied(false);

	} //end testTargetsOccupied
	
} //end BoardAdjTargetTest
