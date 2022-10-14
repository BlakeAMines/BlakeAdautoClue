package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
	
	private String setupSearch = "";
	
	private Map<Character, Room> roomMap;
	
	static Board theInstance = new Board();
	
	private Board()
	{
        super();
        
	} //end constructor
	
	public void initialize()
	{
			
	} //end initialize
	
	//Establishes rooms
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException
	{
		String curLine = "";
		
		try
		{
			//FileReader and Scanner for file input
			FileReader readFile = new FileReader(setupConfigFile);
			Scanner fileIn = new Scanner(readFile);
			
			String[] rooms;
			
			curLine = fileIn.nextLine();
			setupSearch += curLine;
					
			//Takes input from the file as rows of comma separated values
			while(fileIn.hasNextLine())
			{				
				rooms = curLine.split(",", 0);
				
				if(!rooms[0].contains("//"))
				{
					if(!rooms[0].equals("Room") && !rooms[0].equals("Space"))
					{
						throw new BadConfigFormatException();
						
					} //end if
							
				} //end if
					
				curLine = fileIn.nextLine();
				setupSearch += curLine;
				
			} //end while
		
		} //end try
		
		catch(FileNotFoundException fileError)
		{
			//throw fileError;
			
		} //end catch
		
		System.out.println(setupSearch);
		
	} //end loadSetupConfig
	
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException 
	{
		int countCols = 0;
		int countRows = 0;

		String curLine = "";
		
		char[] cellInfo;
		
		String[] rowList;
		String[] tempList;
		
		try
		{
			//FileReader and Scanner for file input
			FileReader readFile = new FileReader(layoutConfigFile);
			Scanner fileIn = new Scanner(readFile);
			
			curLine = fileIn.nextLine();
			rowList = curLine.split(",", 0);
			
			char[] curCell;
					
			//Counts the columns from the first row
			//This doesn't need to count rows because any missing rows or columns will be picked up as a missing column
			countCols = rowList.length;
			
			System.out.println(curLine);
			
			//Takes input from the file as rows of comma separated values
			//Defines the size of the board
			while(fileIn.hasNextLine())
			{							
				curLine = fileIn.nextLine();
				
				System.out.println(curLine);
				
				rowList = curLine.split(",", 0);
				
				if(rowList.length != countCols)
				{
					throw new BadConfigFormatException();
					
				} //end if 
								
				countRows++;
				
			} //end while
			
			grid = new BoardCell[countRows][countCols];
			
			for(int i = 0; i < countRows; i++)
			{
				
				
			} //end for
			
				/*
				grid[curRow][i] = new BoardCell(curRow, i, curCell[0]);
				
				if(curCell.length > 1)
				{
					if(curCell[1] == '*')
					{
						grid[curRow][i].setCenter(true);
						
					} //end nested if
					
					else if(curCell[1] == '#') 
					{
						grid[curRow][i].setLabel(true);
						
					} //end nest else if
					
				} //end nested if
				*/
		
		} //end try
		
		catch(FileNotFoundException fileError)
		{
			throw fileError;
			
		} //end catch
		
	} //end loadLayoutConfig
	
	public static Board getInstance()
	{
		return theInstance;
		
	} //end getInstance
	
	public void setConfigFiles(String csvName, String txtName)
	{
		layoutConfigFile = "data/" + csvName;
		setupConfigFile = "data/" + txtName;
		
	} //end setConfigFiles
		
	//Overloading
	public Room getRoom(char roomLabel)
	{
		//test data
		return null;
		//end test data
		
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
