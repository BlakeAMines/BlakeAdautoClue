package clueGame;

import java.awt.Graphics;

public class Room 
{
	private String name;
	
	BoardCell centerCell;	
	BoardCell labelCell;
	
	public Room(String initName)
	{
		name = initName;
		
	} //end constructor
	
	public String getName()
	{
		return name;
		
	} //end getName

	public void setLabelCell(BoardCell label)
	{
		labelCell = label;
		
	} //end setLabelCell
	
	public BoardCell getLabelCell() 
	{
		return labelCell;
	
	} //end getLabelCell

	public void setCenterCell(BoardCell center)
	{
		centerCell = center;
		
	} //end setLabelCell
	
	public BoardCell getCenterCell() 
	{
		return centerCell;
		
	} //end getCenterCell
	
	public void drawRoomName(Graphics graphic, int xPos, int yPos)
	{
		graphic.drawString(name, xPos, yPos);
		
	} //end drawRoomNames
	
} //end Room
