package Experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard 
{		
	final static int ROWS = 4;
	final static int COLS = 4;
	
	private TestBoardCell grid[][];
	
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard()
	{
		//Sets space for the test grid, target list, and visited list
		grid = new TestBoardCell[ROWS][COLS];
		
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		
		//Adds a cell in each grid space
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++)
			{
				grid[i][j] = new TestBoardCell(i, j);
				
			} //end nested for 
			
		} //end for
		
		//Creates the adjacency list for each cell
		//This runs through a second time because the entire grid must be created first
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++)
			{
				//This cannot be run in the previous for loops because the adjacent cells of a given cell may not yet be created
				calcAdjList(grid[i][j]);
				
			} //end nested for 
			
		} //end for
		
	} //end constructor
	
	//Driver function which prepares for and calls recursive function to find targets
	public void calcTargets(TestBoardCell startCell, int pathLength)
	{
		targets.clear();
		visited.clear();
		
		visited.add(startCell);
		
		findAllTargetsRecursive(startCell, pathLength);
		
	} //end calcTargets
	
	//Recursive function which visits adjacent cells and decides if they are targets
	public void findAllTargetsRecursive(TestBoardCell curStartCell, int curSteps)
	{	
		//Runs through each of the adjacent cells
		for(TestBoardCell curCell : curStartCell.getAdjList())
		{
			if(curCell.getRoom())
			{
				targets.add(curCell);
				
				continue;
				
			} //end nested if
			
			//Cells which have already been visited cannot be revisited
			if(visited.contains(curCell) || curCell.getOccupied())
			{				
				continue;
				
			} //end nested if
			
			else
			{
				//The cell being investigated cannot be revisited
				visited.add(curCell);
				
				//If there are no more moves to step away after, this is a target
				if(curSteps == 1)
				{
					targets.add(curCell);
					
				} //end if
				
				//If there are more steps to take, they must be taken
				else
				{
					//The same process is continued for the current cell with one less step available to take
					findAllTargetsRecursive(curCell, curSteps - 1);
					
				} //end else
				
				//Once the possible paths off of a cell have been investigated, the move is undone and it is no longer considered to be visited
				visited.remove(curCell);
				
			} //end nested else
			
		} //end for
		
	} //end findAllTargetsRecursive
	
	public Set<TestBoardCell> getTargets()
	{
			return targets;
			
	} //end getTargets
	
	public TestBoardCell getCell(int row, int col)
	{
			return grid[row][col];
		
	} //end getCell
	
	/*
	Side note: 
	The calcAdjList function could consider information about occupation or room status
	I decided, though, that those shouldn't matter for adjacency and should be judged as needed
	That also made considering a single, isolated room cell immediately count as a target in calculating targets
	 
	*/
	
	//Creates a Set of adjacent cells
	public void calcAdjList(TestBoardCell cell)
	{
		//incr will switch between plus or minus one to keep the following code more condense and readable
		int incr = 1;
		
		for(int i = 0; i < 2; i++)
		{
			incr *= -1;
			
			//If the cell directly above or below or to the left or right is inside the board, it is adjacent
			if((cell.getRow() + incr) >= 0 && cell.getRow() + incr < ROWS)
			{
				cell.addAdjacency(this.getCell(cell.getRow() + incr, cell.getCol()));
				
			} //end nested if
			
			if(cell.getCol() + incr >= 0 && cell.getCol() + incr < COLS)
			{
				cell.addAdjacency(this.getCell((cell.getRow()), (cell.getCol() + incr)));
				
			} //end nested if
			
		} //end for 
		
	} //end calcAdjList
	
} //end TestBoard
