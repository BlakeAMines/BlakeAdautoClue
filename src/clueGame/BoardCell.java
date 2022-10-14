package clueGame;

import java.util.Set;

public class BoardCell 
{
	private int row;
	private int col;
	
	char initial;
	
	private DoorDirection doorDirection;
	
	private boolean roomLabel;
	private boolean roomCenter;
	
	private char secretPassage;
	
	private Set<BoardCell> adjList;
	
	public BoardCell(int initRow, int initCol, char initLabel)
	{
		row = initRow;
		
		col = initCol;
		
		initial = initLabel;
		
	} //end constructor
	
	public void addAdj(BoardCell adjCell)
	{
		
		
	} //end addAdj
	
	public DoorDirection getDoorDirection()
	{
		return doorDirection;
		
	} //end getDoorDirection
	
	public boolean isDoorway()
	{
		//test data
		return false;
		
		//end test data
		
	} //end isDoorway

	public boolean isLabel() 
	{
		return roomLabel;
		
	} //end isLabel
	
	public void setLabel(boolean label)
	{
		roomLabel = label;
		
	} //end setLabel
	
	public void setCenter(boolean center)
	{
		roomCenter = center;
		
	} //end setCenter

	public boolean isRoomCenter() 
	{
		return roomCenter;
		
	} //end isRoomCenter

	public char getSecretPassage() 
	{
		// TODO Auto-generated method stub
		return 0;
		
	} //end getSecretPassage
	
} //end BoardCell
