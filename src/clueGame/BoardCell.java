package clueGame;

import java.util.Set;

public class BoardCell 
{
	private int row;
	private int col;
	
	private char initial;
	
	private DoorDirection doorDirection;
	
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isDoorway;
	
	private char secretPassage;
	
	private Set<BoardCell> adjList;
	
	public BoardCell(int initRow, int initCol, char initLabel)
	{
		roomLabel = false;
		roomCenter = false;
		isDoorway = false;
		
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
	
	public void setDoorDirection(DoorDirection door)
	{
		doorDirection = door;
		
	} //end setDoorDirection
	
	public boolean isDoorway()
	{
		return isDoorway;
		
	} //end isDoorway
	
	public void setDoorway(boolean door)
	{
		isDoorway = door;
		
	} //end setDoorway

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
	
	public char getInitial()
	{
		return initial;
		
	} //end getInitial
	
} //end BoardCell
