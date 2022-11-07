package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Board 
{
	private BoardCell[][] grid;
	
	private int numRows;
	private int numColumns;
	
	private int numRooms;
	private int numPeople;
	private int numWeapons;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private ArrayList<Player> playerList;
	
	private ArrayList<Card> cleanDeck;
 	private ArrayList<Card> gameDeck;
	
	private Solution theAnswer;
	
	static Board theInstance = new Board();
	
	private Board()
	{
        super();
                
	} //end constructor
	
	//Works as a pseudo-constructor to load a new Clue game
	public void initialize()
	{		
		roomMap = new HashMap<>();
		playerList = new ArrayList<>();
		gameDeck = new ArrayList<>();
		cleanDeck = new ArrayList<>();
		targets = new HashSet<>();
		visited = new HashSet<>();
				
		numRooms = 0;
		numPeople = 0;
		numWeapons = 0;
		
		loadConfigFiles();
		loadCleanDeck();
		generateAnswer();
		shuffleDeck();
		distributeDeck();
		
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
			System.out.println("Error reading file");
			
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
		String curLine = "";
		
		//readInfo has info from the text file as seperate strings ([0] is title, [1] is name, etc.)
		String[] readInfo;
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
				readInfo = curLine.split(", ", 0);
				
				if(!curLine.contains("//"))
				{					
					if(readInfo[0].equals("Room") || readInfo[0].equals("Space"))
					{
						if(readInfo[0].equals("Room"))
						{
							numRooms++;
							
						} //end nested if
						
						roomLabels = readInfo[2].toCharArray();
						
						Room tempRoom = new Room(readInfo[1]);
						
						roomMap.put(roomLabels[0], tempRoom);
							
					} //end nested if
					
					else if(readInfo[0].equals("Player"))
					{
						numPeople++;
						
						if(readInfo[4].equals("Human"))
						{
							playerList.add(new HumanPlayer(readInfo[1], readInfo[2], readInfo[3]));
							
						} //end nested if
						
						else
						{
							playerList.add(new ComputerPlayer(readInfo[1], readInfo[2], readInfo[3]));
							
						} //end nested else
						
					} //end nested else if
						
					else if(readInfo[0].equals("Weapon"))
					{
						numWeapons++;
						
					} //end else if
					
					else
					{
						BadConfigFormatException badSetup = new BadConfigFormatException(setupConfigFile);
						badSetup.logError("Incorrect Setup Formatting");
						throw badSetup;
						
					} //end nested if
										
					makeCard(readInfo[1], readInfo[0]);		
					
				} //end nested if
							
			} while(fileIn.hasNextLine()); //end do while
			
		} //end try
		
	} //end loadSetupConfig
	
	//Sets up the board by reading a csv and handles improper formatting with exceptions
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException 
	{		
		String curLine = "";
		
		//rowList is used to hold the cell initials in a row as strings
		String[] rowList;
		
		//cellInfo is used to store the info about a cell like its initial and possible secondary info
		char[] cellInfo;
		
		//This saves the board information for use after reading it in
		ArrayList<String[]> saveRows = new ArrayList<>();

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
				//Saves the string information as characters
				cellInfo = saveRows.get(i)[j].toCharArray();
				
				//Creates a new boardCell with the given initial
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
				
				grid[i][j].handleAllow();
				
			} //end nested for
			
		} //end for
		
	} //end loadLayoutConfig
	
	//I considered handling this inside of a cell, but because it involved rooms, I decided to include it in Board
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
		handleValid(cell);
				
		//incr will switch between plus or minus one to keep the following code more condense
		int incr = 1;
		
		if(cell.isAllowed())
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
		
	public void handleValid(BoardCell cell)
	{
		BoardCell tempCell;

		if(cell.isDoorway())
		{
			tempCell = findDoorPoint(cell);
			cell.addAdj(tempCell);
			tempCell.addAdj(cell);
			
		} //end nested if
				
		//Secret Passage case
		else if(cell.isSecretPassage())
		{
			tempCell = roomMap.get(cell.getSecretPassage()).getCenterCell();
			roomMap.get(cell.getInitial()).getCenterCell().addAdj(tempCell);
			
		} //end else if
		
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
	
	public List<Player> getPlayerList()
	{
		return playerList;
		
	} //end getPlayerList
		
	public ArrayList<Card> getGameDeck()
	{
		return gameDeck;
		
	} //end getGameDeck
	
	public ArrayList<Card> getCleanDeck()
	{
		return cleanDeck;
		
	} //end getCleanDeck
	
	public void makeCard(String cardName, String cardType)
	{
		if(!cardType.equals("Space"))
		{
			gameDeck.add(new Card(cardName, cardType));
			
		} //end if
		
	} //end makeCard
	
	public void shuffleDeck()
	{
		Collections.shuffle(gameDeck);
		
	} //end shuffleDeck
	
	public void generateAnswer()
	{		
		int random;
		
		Card getRoom;
		Card getPerson;
		Card getWeapon;
				
		Random rand = new Random();
		
		if(numRooms > 0 && numPeople > 0 && numWeapons > 0)
		{		
			random = rand.nextInt(numRooms);
			getRoom = cleanDeck.get(random);
			
			random = rand.nextInt(numPeople);
			getPerson = cleanDeck.get(random + numRooms);
			
			random = rand.nextInt(numWeapons);
			getWeapon = cleanDeck.get(random + numRooms + numPeople);	
			
			theAnswer = new Solution(getRoom, getPerson, getWeapon);
		
		} //end if
		
	} //end generateAnswer
	
	public Solution getAnswer()
	{
		return theAnswer;
		
	} //end getAnswer
	
	public void distributeDeck()
	{
		if(numRooms > 0 && numPeople > 0 && numWeapons > 0)
		{
			for(int i = 0; i < gameDeck.size(); i++)
			{
				playerList.get(i % playerList.size()).updateHand(gameDeck.get(i));
				
			} //end nested for 
		
		} //end if
		
	} //end distributeDeck
	
	public void setAnswer(Solution newAnswer)
	{
		theAnswer = newAnswer;
		
	} //end setAnswer
	
	public boolean checkAccusation(Solution accusation)
	{
		return theAnswer.equals(accusation);
		
	} //end checkAccusation;
	
	public Card handleSuggestion(Solution testSuggestion)
	{
		int start = 0;
		
		for(int i = start; i < playerList.size(); i++)
		{
			playerList.get(i % playerList.size()).disproveSuggestion(testSuggestion);
			
		} //end for
		
		return new Card("Empty", "Empty");
		
	} //end handleSuggestion
	
	public void loadCleanDeck()
	{
		for(int i = 0; i < gameDeck.size(); i++)
		{
			cleanDeck.add(gameDeck.get(i));
			
		} //end for
		
	} //end loadCleanDeck
	
} //end Board