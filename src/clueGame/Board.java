package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board 
{
	private BoardCell[][] grid;
	
	private int numRows;
	private int numColumns;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	
	private Map<Character, Room> roomMap;
	
	static Board theInstance = new Board();
	
	private Board()
	{
        super();
                
	} //end constructor
	
	public void initialize()
	{
		this.loadConfigFiles();
		
	} //end initialize
	
	public static Board getInstance()
	{
		return theInstance;
		
	} //end getInstance
	
	public void loadConfigFiles()
	{
		roomMap = new HashMap<Character, Room>();
		
		try
		{
			loadSetupConfig();
			loadLayoutConfig();
			
		} //end try
		
		catch(FileNotFoundException fileError)
		{
			
			
		} //end catch
		
		catch(BadConfigFormatException formatError)
		{
			
			
		} //end catch
		
	} //end loadConfigFiles
	
	//Establishes rooms
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException
	{
		String curLine = "";
		
		try
		{
			//FileReader and Scanner for file input
			FileReader readFile = new FileReader(setupConfigFile);
			Scanner fileIn = new Scanner(readFile);
			
			String[] roomInfo;
			char[] roomLabels;
				
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
						throw new BadConfigFormatException();
						
					} //end nested if				
					roomLabels = roomInfo[2].toCharArray();

					roomMap.put(roomLabels[0], new Room(roomInfo[1]));
													
				} //end nested if
									
			} while(fileIn.hasNextLine()); //end do while
		
		} //end try
		
		catch(FileNotFoundException fileError)
		{
			//throw fileError;
			
		} //end catch
		
	} //end loadSetupConfig
	
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException 
	{		
		int countCols = 0;
		int countRows = 0;

		String curLine = "";
	
		String[] rowList;
		ArrayList<String[]> saveRows = new ArrayList<String[]>();
		char[] cellInfo;
		
		try
		{
			//FileReader and Scanner for file input
			FileReader readFile = new FileReader(layoutConfigFile);
			Scanner fileIn = new Scanner(readFile);
			
			curLine = fileIn.nextLine();
			rowList = curLine.split(",", 0);			
			saveRows.add(rowList);
			
			countRows++;
					
			//Counts the columns from the first row
			//This doesn't need to count rows because any missing rows or columns will be picked up as a missing column
			countCols = rowList.length;
			
			//Takes input from the file as rows of comma separated values
			//Defines the size of the board
			while(fileIn.hasNextLine())
			{							
				curLine = fileIn.nextLine();
				rowList = curLine.split(",", 0);
				saveRows.add(rowList);
				
				if(rowList.length != countCols)
				{
					throw new BadConfigFormatException();
					
				} //end if 
								
				countRows++;
				
			} //end while
			
			fileIn.close();
			
			//Saves info about board dimensions
			numRows = countRows;
			numColumns = countCols;
			grid = new BoardCell[numRows][numColumns];
			
			//Iterates through each index on the board grid
			for(int i = 0; i < numRows; i++)
			{
				for(int j = 0; j < numColumns; j++)
				{			
					cellInfo = saveRows.get(i)[j].toCharArray();
					System.out.print(cellInfo[0]);
					grid[i][j] = new BoardCell(i, j, cellInfo[0]);

					if(!roomMap.containsKey(cellInfo[0]))
					{
						throw new BadConfigFormatException();
						
					} //end nested if
					
					if(cellInfo.length > 1)
					{
						if(cellInfo[1] == '*')
						{
							grid[i][j].setCenter(true);
							
						} //end nested if
						
						else if(cellInfo[1] == '#') 
						{
							grid[i][j].setLabel(true);
							
						} //end nest else if
						
					} //end nested if
					
				} //end nested for
				
				System.out.println("");
				
			} //end for
							
		} //end try
				
		catch(FileNotFoundException fileError)
		{
			throw fileError;
			
		} //end catch
		
	} //end loadLayoutConfig
	
	public void setConfigFiles(String csvName, String txtName)
	{
		layoutConfigFile = "data/" + csvName;
		setupConfigFile = "data/" + txtName;
		
	} //end setConfigFiles
		
	//Overloading
	public Room getRoom(char roomLabel)
	{
		return roomMap.get(roomLabel);
		
	} //end getRoom
	
	//Overloading
	public Room getRoom(BoardCell cell)
	{
		//test data
		return null;
		//end test data
		
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
