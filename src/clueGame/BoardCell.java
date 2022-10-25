package clueGame;

import java.util.HashSet;
import java.util.Set;

import Experiment.TestBoardCell;

public class BoardCell 
{
	private int row;
	private int col;
	
	private char initial;
	private char secretPassage;

	private boolean isPassage;
	private boolean isAllowed;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isDoorway;
	private boolean isOccupied;
	
	private DoorDirection doorDirection;
	
	private Set<BoardCell> adjList;
	
	public BoardCell(int initRow, int initCol, char initLabel)
	{
		roomLabel = false;
		roomCenter = false;
		isOccupied = false;
		isDoorway = false;
		isPassage = false;
		isAllowed = false;
		
		row = initRow;
		
		col = initCol;
		
		initial = initLabel;
		
		adjList = new HashSet<>();
		
	} //end constructor
	
	public void addAdj(BoardCell cell)
	{
		adjList.add(cell);
		
	} //end addAdjacency
	
	public Set<BoardCell> getCellAdjList()
	{
		return adjList;
		
	} //end getCellAdjList
	
	public void setOccupied(boolean occupy)
	{
		isOccupied = occupy;
		
	} //end setOccupied
	
	public boolean getOccupied()
	{
		return isOccupied;
		
	} //end getOccupied
	
	public DoorDirection getDoorDirection()
	{
		return doorDirection;
		
	} //end getDoorDirection
	
	public void makeDoor(char doorInfo)
	{
		setDoorway(true);
		
		switch(doorInfo)
		{
			case '^':
				setDoorDirection(DoorDirection.UP);
				
			break;
			
			case 'v':
				setDoorDirection(DoorDirection.DOWN);
				
			break;
			
			case '>':
				setDoorDirection(DoorDirection.RIGHT);
				
			break;
			
			case '<':
				setDoorDirection(DoorDirection.LEFT);
				
			break;
			
			default:
				setDoorDirection(DoorDirection.NONE);
				setDoorway(false);
				
			break;
		
		} //end switch
		
	} //end newDoor
	
	public void handleAllow()
	{
		if(roomLabel || initial == 'W')
		{
			isAllowed = true;
			
		}
		
	} //end handleAllow
	
	public boolean isAllowed()
	{
		return isAllowed;
		
	} //end isAllowed
	
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
		return secretPassage;
		
	} //end getSecretPassage
	
	public boolean isSecretPassage()
	{
		return isPassage;
		
	} //end isSecretPassage
	
	public void setSecretPassage(char passage)
	{
		secretPassage = passage;
		isPassage = true;
		
	} //end setSecretPassage
	
	public char getInitial()
	{
		return initial;
		
	} //end getInitial
	
	public int getRow()
	{
		return row;
		
	} //end getRow
	
	public int getCol()
	{
		return col;
		
	} //end getRow
	
} //end BoardCell
