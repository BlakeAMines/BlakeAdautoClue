package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Experiment.TestBoardCell;

public class Board 
{
	private BoardCell[][] grid;
	
	private int numRows;
	private int numColumns;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	static Board theInstance = new Board();
	
	private Board()
	{
        super();
                
	} //end constructor
	
	public void initialize()
	{
		loadConfigFiles();
		
	} //end initialize
	
	public static Board getInstance()
	{
		return theInstance;
		
	} //end getInstance
	
	public void setConfigFiles(String csvName, String txtName)
	{
		layoutConfigFile = "data/" + csvName;
		setupConfigFile = "data/" + txtName;
		
	} //end setConfigFiles
	
	public void loadConfigFiles()
	{
		try
		{
			loadSetupConfig();
			loadLayoutConfig();
			calcAllAdj();
			
		} //end try
		
		catch(FileNotFoundException fileError)
		{
			//Do something
			
		} //end catch
		
		//Default Error Message which runs in addition to any other specific errors
		catch(BadConfigFormatException formatError)
		{
			formatError.logError();
			
		} //end catch
		
	} //end loadConfigFiles
	
	//Establishes rooms by reading a txt setup file and handles bad formatting with exceptions
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException
	{
		roomMap = new HashMap<>();
		targets = new HashSet<>();
		visited = new HashSet<>();
		
		String curLine = "";
		String[] roomInfo;
		char[] roomLabels;

		//FileReader and Scanner for file input
		FileReader readFile = new FileReader(setupConfigFile);
		try (Scanner fileIn = new Scanner(readFile)) 
		{				
			//Takes input from the file as rows of comma separated values
			do 
			{				
				curLine = fileIn.nextLine();
				
				//Splits the string of room info into individual pieces
				roomInfo = curLine.split(", ", 0);
				
				if(!roomInfo[0].contains("//"))
				{
					if(!roomInfo[0].equals("Room") && !roomInfo[0].equals("Space"))
					{
						BadConfigFormatException badSetup = new BadConfigFormatException(setupConfigFile);
						badSetup.logError("Incorrect Setup Formatting");
						throw badSetup;
						
					} //end nested if
										
					roomLabels = roomInfo[2].toCharArray();
					
					Room tempRoom = new Room(roomInfo[1]);
					
					roomMap.put(roomLabels[0], tempRoom);
					
				} //end nested if
									
			} while(fileIn.hasNextLine()); //end do while
			
		} //end try
		
	} //end loadSetupConfig
	
	//Sets up the board by reading a csv and handles improper formatting with exceptions
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException 
	{		
		String curLine = "";
	
		String[] rowList;
		char[] cellInfo;
		ArrayList<String[]> saveRows = new ArrayList<String[]>();

		//FileReader and Scanner for file input
		FileReader readFile = new FileReader(layoutConfigFile);
		
		try (Scanner fileIn = new Scanner(readFile)) 
		{
			curLine = fileIn.nextLine();
			rowList = curLine.split(",", 0);			
			saveRows.add(rowList);

			//Counts the columns from the first row
			//This doesn't need to count rows because any missing rows or columns will be picked up as a missing column
			numColumns = rowList.length;

			//Takes input from the file as rows of comma separated values
			//Defines the size of the board
			while(fileIn.hasNextLine())
			{							
				curLine = fileIn.nextLine();
				rowList = curLine.split(",", 0);
				saveRows.add(rowList);
				
				if(rowList.length != numColumns)
				{
					BadConfigFormatException badLayout = new BadConfigFormatException(layoutConfigFile);
					badLayout.logError("Incorrect Columns");
					throw badLayout;
					
				} //end if 
				
			} //end while

		} //end try

		numRows = saveRows.size();		
		grid = new BoardCell[numRows][numColumns];
		
		//Iterates through each index on the board grid
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numColumns; j++)
			{			
				cellInfo = saveRows.get(i)[j].toCharArray();
				grid[i][j] = new BoardCell(i, j, cellInfo[0]);

				if(!roomMap.containsKey(cellInfo[0]))
				{
					BadConfigFormatException badLayout = new BadConfigFormatException(layoutConfigFile);
					badLayout.logError("Board Contains Unspecified Room Character");
					throw badLayout;
					
				} //end nested if
				
				if(cellInfo.length > 1)
				{
					handleCellInfo(grid[i][j], cellInfo[0], cellInfo[1]);
											
				} //end nested if
				
			} //end nested for
			
		} //end for
		
	} //end loadLayoutConfig
	
	//I considered handling this inside of a cell, but because it involved rooms, I decided to include it in 
	public void handleCellInfo(BoardCell cell, char cellLabel, char cellInfo)
	{
		if(cellInfo == '*')
		{
			cell.setCenter(true);
			roomMap.get(cellLabel).setCenterCell(cell);
			
		} //end nested if
		
		else if(cellInfo == '#') 
		{
			cell.setLabel(true);
			roomMap.get(cellLabel).setLabelCell(cell);
			
		} //end nested else if
		
		//Secret passage case
		else if(roomMap.containsKey(cellInfo))
		{
			cell.setSecretPassage(cellInfo);
			
		} //end nested else
		
		else if(cellLabel == 'W')
		{
			cell.makeDoor(cellInfo);
			
		} //end nested else if
				
	} //end handleCellInfo
	
	//Driver function for calculating targets given starting cell and dice roll
	public void calcTargets(BoardCell startCell, int startingSteps)
	{
		targets.clear();
		visited.clear();
		visited.add(startCell);
		
		findAllTargetsRecursive(startCell, startingSteps);
		
	} //end calcTargets
	
	//Recursive function explores possible movements
	public void findAllTargetsRecursive(BoardCell curStartCell, int curSteps)
	{
		//Runs through each of the adjacent cells
		for(BoardCell curCell : curStartCell.getCellAdjList())
		{			
			if(!visited.contains(curCell) && (!curCell.getOccupied() || curCell.isRoomCenter()))
			{
				//The cell being investigated cannot be revisited
				visited.add(curCell);
				
				if(curCell.isRoomCenter() && curSteps >= 1)
				{
					targets.add(curCell);
					 
				} //end nested 
				
				//If there are no more moves to step away after, this is a target
				else if(curSteps == 1)
				{
					targets.add(curCell);
					
				} //end nested if
				
				//If there are more steps to take, they must be taken
				else
				{
					//The same process is continued for the current cell with one less step available to take
					findAllTargetsRecursive(curCell, curSteps - 1);
					
				} //end nested else
				
				//Once the possible paths off of a cell have been investigated, the move is undone and it is no longer considered to be visited
				visited.remove(curCell);
				
			} //end nested else
			
		} //end for
		
	} //end findAllTargetsRecursive
	
	//Returns the room center of the room that a given door cell points to
	public BoardCell findDoorPoint(BoardCell startCell)
	{
		BoardCell roomCenter;
		
		switch(startCell.getDoorDirection())
		{
			case UP:
				roomCenter = roomMap.get(grid[startCell.getRow() - 1][startCell.getCol()].getInitial()).getCenterCell();
								
			break;
			
			case DOWN:
				roomCenter = roomMap.get(grid[startCell.getRow() + 1][startCell.getCol()].getInitial()).getCenterCell();
			
			break;
			
			case RIGHT:
				roomCenter = roomMap.get(grid[startCell.getRow()][startCell.getCol() + 1].getInitial()).getCenterCell();
				
			break;
			
			case LEFT:
				roomCenter = roomMap.get(grid[startCell.getRow()][startCell.getCol() - 1].getInitial()).getCenterCell();

			break;
			
			case NONE:
				return startCell;
			
			default:
				System.out.println("ERRROR, no door pointer");
				
			return null;
				
		} //end switch 
		
		return roomCenter;
		
	} //end findDoorPoint
		
	//Driver function for calculating an adjacency list
	public void calcAllAdj()
	{
		//Creates the adjacency list for each cell
		//This runs through separate to creating the board because the entire grid must be created first
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numColumns; j++)
			{			
				//This cannot be run in the previous for loops because the adjacent cells of a given cell may not yet be created
				calcAdjList(grid[i][j]);
				
			} //end nested for 
			
		} //end for
				
	} //end calcAllAdj
	
	//Handles logic for which cells will be adjacent to each other
	public void calcAdjList(BoardCell cell)
	{		
		BoardCell tempCell;
		
		//This will handle the special cases for adjacencies (doors and passages) and return if the cell is available
		boolean validCell = handleValid(cell);
				
		//incr will switch between plus or minus one to keep the following code more condense
		int incr = 1;
		
		if(validCell)
		{
			for(int i = 0; i < 2; i++)
			{
				incr *= -1;
				
				//If the cell directly above or below or to the left or right is inside the board, it might be adjacent
				if((cell.getRow() + incr) >= 0 && cell.getRow() + incr < numRows)
				{	
					tempCell = grid[cell.getRow() + incr][cell.getCol()];
					
					if(tempCell.getInitial() == 'W')
					{
						cell.addAdj(tempCell);
						
					} //end nested if
					
				} //end nested if
				
				if(cell.getCol() + incr >= 0 && cell.getCol() + incr < numColumns)
				{
					tempCell = grid[cell.getRow()][cell.getCol() + incr];
	
					if(tempCell.getInitial() == 'W')
					{
						cell.addAdj(tempCell);
						
					} //end nested if
					
				} //end nested if
				
			} //end nested for 
			
		} //end if
			
	} //end calcAdjList
	
	public boolean handleValid(BoardCell cell)
	{
		BoardCell tempCell;
	
		boolean validCell = false;
		
		if(cell.isDoorway())
		{
			validCell = true;
			
			tempCell = findDoorPoint(cell);
			cell.addAdj(tempCell);
			tempCell.addAdj(cell);
			
		} //end nested if
		
		else if(cell.getInitial() == 'W')
		{
			validCell = true;
			
		} //end else if
		
		//Secret Passage case
		else if(cell.isSecretPassage())
		{
			tempCell = roomMap.get(cell.getSecretPassage()).getCenterCell();
			roomMap.get(cell.getInitial()).getCenterCell().addAdj(tempCell);
			
		} //end else if
		
		return validCell;
		
	} //end calcValid
	
	public Set<BoardCell> getTargets()
	{
		return targets;
		
	} //end getTargets
	
	public Set<BoardCell> getAdjList(int cellRow, int cellCol)
	{
		return grid[cellRow][cellCol].getCellAdjList();
		
	} //end getAdjList
	
	//Overloading getRoom using room symbol
	public Room getRoom(char roomLabel)
	{
		return roomMap.get(roomLabel);
		
	} //end getRoom
	
	//Overloading getRoom using cell
	public Room getRoom(BoardCell cell)
	{
		return roomMap.get(cell.getInitial());
		
	} //end getRoom
	
	public BoardCell getCell(int row, int col)
	{
		return grid[row][col];

	} //end getCell
	
	public int getNumColumns()
	{
		return numColumns;
		
	} //end getNumCols
	
	public int getNumRows()
	{
		return numRows;
		
	} //end getNumRows
		
} //end Board