package Tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Experiment.TestBoard;
import Experiment.TestBoardCell;

class BoardTestsExp 
{
	private TestBoard board;

	//Sets new board for tests
	@BeforeEach
	public void setUp()
	{
		board = new TestBoard();	
		
	} //end constructor
	
	//Adjacency Tests
	@Test
	public void topCornerCellAdjacencyTest()
	{
		//Top Left Cell
		TestBoardCell testCell = board.getCell(0, 0);
		
		Set<TestBoardCell> testAdjList = testCell.getAdjList();
		
		Assert.assertEquals(2, testAdjList.size());
		Assert.assertTrue(testAdjList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testAdjList.contains(board.getCell(0, 1)));
		
	} //end topCornerCellAdjacencyTest
	
	@Test
	public void bottomCornerCellAdjacencyTest()
	{
		//Bottom Right Cell
		TestBoardCell testCell = board.getCell(3, 3);
		
		board.calcAdjList(testCell);
		
		Set<TestBoardCell> testAdjList = testCell.getAdjList();
		
		Assert.assertEquals(2, testAdjList.size());
		Assert.assertTrue(testAdjList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testAdjList.contains(board.getCell(3, 2)));
		
	} //end bottomCornerCellAdjacencyTest
	
	@Test
	public void leftEdgeCellAdjacencyTest()
	{
		//Far Left Edge
		TestBoardCell testCell = board.getCell(1, 0);
		
		Set<TestBoardCell> testAdjList = testCell.getAdjList();
				
		Assert.assertEquals(3, testAdjList.size());
		Assert.assertTrue(testAdjList.contains(board.getCell(0, 0)));
		Assert.assertTrue(testAdjList.contains(board.getCell(1, 1)));
		Assert.assertTrue(testAdjList.contains(board.getCell(2, 0)));
		
	} //end leftEdgeCellAdjacencyTest
	
	@Test
	public void rightEdgeCellAdjacencyTests()
	{
		//Far Right Edge
		TestBoardCell testCell = board.getCell(1, 3);
		
		Set<TestBoardCell> testAdjList = testCell.getAdjList();
		
		Assert.assertEquals(3, testAdjList.size());
		Assert.assertTrue(testAdjList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testAdjList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testAdjList.contains(board.getCell(2, 3)));
	
	} //end rightEdgeCellAdjacencyTest
	
	@Test
	public void topEdgeCellAdjacencyTests()
	{
		//Top Edge
		TestBoardCell testCell = board.getCell(0, 1);
		
		board.calcAdjList(testCell);
		
		Set<TestBoardCell> testAdjList = testCell.getAdjList();
		
		Assert.assertEquals(3, testAdjList.size());
		Assert.assertTrue(testAdjList.contains(board.getCell(0, 0)));
		Assert.assertTrue(testAdjList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testAdjList.contains(board.getCell(1, 1)));
				
	} //end topEdgeCellAdjacencyTest
	
	@Test
	public void middleGridAdjacencyTests()
	{
		//Middle of Board
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcAdjList(testCell);
		
		Set<TestBoardCell> testAdjList = testCell.getAdjList();
		
		Assert.assertEquals(4, testAdjList.size());
		Assert.assertTrue(testAdjList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testAdjList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testAdjList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testAdjList.contains(board.getCell(2, 1)));
				
	} //end middleGridAdjacencyTests
	
	//Target Tests
	@Test
	public void oneMoveCornerTargetTest()
	{
		//Tests targets from corner of empty board
		TestBoardCell testCell = board.getCell(0, 0);
		
		board.calcTargets(testCell, 1);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 2);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 1)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 0)));
				
	} //end oneMoveCornerTargetTest
	
	@Test
	public void oneMoveMiddleTargetTest()
	{
		//Tests targets from middle of empty board
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 1);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 4);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 1)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 1)));

	} //end oneMoveMiddleTargetTest
	
	@Test
	public void twoMovesCornerTargetTest()
	{
		//Tests targets from corner with two moves and empty board
		TestBoardCell testCell = board.getCell(0, 0);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 3);
		
		//Ensures the piece cannot move back to where it started
		Assert.assertFalse(testTargets.contains(board.getCell(0, 0)));
		
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 1)));
				
	} //end twoMovesCornerTargetTest
	
	@Test
	public void twoMovesMiddleTargetTest()
	{
		//Tests targets from center with two moves and empty board
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 6);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(3, 1)));
		
	} //end twoMovesMiddleTargetTest

	@Test
	public void threeMovesMiddleTargetTest()
	{
		//Tests targets from center of an empty board with three moves
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 6);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(3, 1)));
				
	} //end threeMovesMiddleTargetTest
	
	@Test
	public void sixMovesCornerTargetTest()
	{
		//Tests targets from a corner on an empty board with six moves
		TestBoardCell testCell = board.getCell(0, 0);
		
		board.calcTargets(testCell, 6);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 7);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 1)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(3, 1)));
		Assert.assertTrue(testTargets.contains(board.getCell(3, 3)));
		
	} //end sixMovesCornerTargetTest
	
	@Test
	public void sixMovesMiddleTargetTest()
	{
		//Tests targets from the middle of an empty board with six moves
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 6);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 7);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(3, 1)));
		Assert.assertTrue(testTargets.contains(board.getCell(3, 3)));
			
	} //end sixMovesMiddleTargetTest

	//Occupied and Room Tests
	@Test
	public void twoMovesOpponentTargetTest()
	{
		//Tests for targets with one target occupied by opponent
		board.getCell(0, 0).setOccupied(true);
		
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 5);
		
		Assert.assertFalse(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		
	} //end twoMovesOpponentTargetTest
	
	@Test
	public void twoMovesOpponentBlockedTargetTest()
	{
		//Tests for targets when opponent blocks the path to a target
		board.getCell(1, 2).setOccupied(true);
		
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 5);
		
		Assert.assertFalse(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 2)));
						
	} //end twoMovesBlockedTargetTest
	
	@Test
	public void twoMovesRoomTargetTest()
	{
		//Tests targets when a target is a room
		board.getCell(0, 0).setRoom(true);
		
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 6);
		
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		
	} //end twoMovesOpponentTargetTest
	
	@Test
	public void twoMovesBlockedRoomTargetTest()
	{
		//Tests targets when a room blocks the path to a target and is not itself on a target
		board.getCell(1, 2).setRoom(true);
		
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 6);
		Assert.assertFalse(testTargets.contains(board.getCell(1, 3)));
		Assert.assertTrue(testTargets.contains(board.getCell(1, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
				
	} //end twoMovesOpponentTargetTest
	
	@Test
	public void twoMovesMixed1()
	{
		//Tests targets when opponents block every path to a room
		board.getCell(0, 0).setRoom(true);
		board.getCell(1, 0).setOccupied(true);
		board.getCell(0, 1).setOccupied(true);
		
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 5);
		Assert.assertFalse(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
		
	} //end twoMovesMixed1
	
	@Test
	public void twoMovesMixed2()
	{
		//Tests targets when opponent blocks one path to a room
		board.getCell(0, 0).setRoom(true);
		board.getCell(0, 1).setOccupied(true);
		
		TestBoardCell testCell = board.getCell(1, 1);
		
		board.calcTargets(testCell, 2);
		
		Set<TestBoardCell> testTargets = board.getTargets();
		
		Assert.assertEquals(testTargets.size(), 6);
		Assert.assertTrue(testTargets.contains(board.getCell(0, 0)));
		Assert.assertTrue(testTargets.contains(board.getCell(0, 2)));
		Assert.assertTrue(testTargets.contains(board.getCell(2, 0)));
				
	} //end twoMovesMixed2
	
} //end BoardTestsExp
