package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception
{
	private String errorLogFile;
	private String problemFile;
	
	BadConfigFormatException(String setProblemFile)
	{
		problemFile = setProblemFile;
		
		errorLogFile = "data/errorlog.txt";
		
	} //end constructor
	
	public void logError()
	{
		try 
		{
			PrintWriter errorOutput = new PrintWriter(errorLogFile);
			
			errorOutput.println("ERROR:");
			errorOutput.println("Configuration or Layout Error in " + "'" + problemFile + "'");
			
			errorOutput.close();
			
		} //end try
		
		catch (FileNotFoundException e) 
		{
			//Add handling for missing errorlog.txt

		} //end catch
		
	} //end logError
	
	public void logError(String message)
	{
		try 
		{
			PrintWriter errorOutput = new PrintWriter(errorLogFile);
			
			errorOutput.println("ERROR:");
			errorOutput.println("Configuration or Layout Error in " + "'" + problemFile + "'" + ", " + message);
			
			errorOutput.close();
			
		} //end try
		
		catch (FileNotFoundException e) 
		{
			//Add handling for missing errorlog.txt

		} //end catch
		
	} //end logError
	
} //end BadConfigFormatException
