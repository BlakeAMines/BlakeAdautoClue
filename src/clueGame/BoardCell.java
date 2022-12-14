package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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
	
	private boolean targetFlag;
	
	private boolean isWalkway;
	private boolean isRoom;
	
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
		
		isWalkway = false;
		isRoom = false;
		
		targetFlag = false;
		
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
	
	public boolean isOccupied()
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

		} //end if
					
	} //end handleAllow
	
	public void handleType()
	{
		if(initial == 'W')
		{
			isWalkway = true;
			
		} //end 
		
		else if(initial != 'X')
		{
			isRoom = true;
			
		} //end else if
		
	} //end handleType
	
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
	
	//This draws a cell on the board and contains logic for what color and shape to draw
	public void drawCell(Graphics graphic, int cellSize, int xPos, int yPos)
	{		
		if(isWalkway)
		{
			graphic.setColor(Color.orange);

		} //end if
		
		else if(isRoom)
		{
			graphic.setColor(Color.lightGray);
			
		} //end else if
		
		else
		{
			graphic.setColor(Color.black);
			
		} //end else
				
		graphic.fillRect(xPos, yPos, cellSize, cellSize);
				
		if(isPassage)
		{
			graphic.drawString(Character.toString(secretPassage), xPos + cellSize / 2, yPos + cellSize / 2);
			
		} //end isPassage

	} //end drawCell
	
	//This draws a door, but must be called after drawing the cells below
	public void drawDoor(Graphics graphic, int size, int xPos, int yPos)
	{
		graphic.setColor(Color.green);
		
		if(doorDirection.equals(DoorDirection.UP))
		{
			graphic.fillRect(xPos + 1, yPos - size / 4, size - 1, size / 4);
			
		} //end if
		
		else if(doorDirection.equals(DoorDirection.DOWN))
		{
			graphic.fillRect(xPos + 1, yPos + size, size - 1, size / 4);
			
		} //end if
		
		else if(doorDirection.equals(DoorDirection.RIGHT))
		{
			graphic.fillRect(xPos + size, yPos + 1, size / 4, size - 1);
			
		} //end if
		
		else if(doorDirection.equals(DoorDirection.LEFT))
		{
			graphic.fillRect(xPos - (size / 4), yPos + 1, size / 4, size - 1);
			
		} //end if
		
	} //end drawDoor
	
	//This draws a grid outline over every cell on the board
	public void drawGrid(Graphics graphic, int size, int xPos, int yPos)
	{
		graphic.setColor(Color.black);
		
		if(!isRoom)
		{
			graphic.drawRect(xPos, yPos, size, size);
			
		} //end if
		
	} //end drawGrid
	
	//This is a special draw which is only for targets
	//I put it in cell because the cells don't know if they are targets
	public void drawTarget(Graphics graphic, int size, int xPos, int yPos)
	{
		graphic.setColor(Color.MAGENTA);
		graphic.fillRect(xPos, yPos, size, size);
				
	} //end drawTarget
	
	//This is test code
	/*
	public void drawOccupied(Graphics graphic, int size, int xPos, int yPos)
	{
		graphic.setColor(Color.BLUE);
		graphic.fillRect(xPos, yPos, size, size);
		
	} //end drawOccupied
	*/
		
} //end BoardCell
