package Experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell 
{
	Set<TestBoardCell> adjacencyList;
	
	int row;
	int col;
	
	boolean isOccupied;
	boolean isRoom;
	
	public TestBoardCell(int inRow, int inCol)
	{
			row = inRow;
			
			col = inCol;
			
			adjacencyList = new HashSet<TestBoardCell>();
							
	} //end constructor
	
	public void addAdjacency(TestBoardCell cell)
	{
		adjacencyList.add(cell);
		
	} //end addAdjacency
	
	public Set<TestBoardCell> getAdjList()
	{
		return adjacencyList;
		
	} //end getAdjList
	
	public void setRoom(boolean room)
	{
		isRoom = room;
		
	} //end setRoom
	
	public boolean getRoom()
	{
		return isRoom;
		
	} //end isRoom
	
	public void setOccupied(boolean occupy)
	{
		isOccupied = occupy;
		
	} //end setOccupied
	
	public boolean getOccupied()
	{
		return isOccupied;
		
	} //end getOccupied
	
	public int getRow()
	{
		return row;
		
	} //end getRow
	
	public int getCol()
	{
		return col;
		
	} //end getCol
	
} //end TestBoardCell
