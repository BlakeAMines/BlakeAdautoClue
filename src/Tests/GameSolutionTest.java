package Tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest 
{
	private static Board board;
	
	public static final int NUM_CARDS = 21;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	
	private static ArrayList<Player> testPlayerList;
	
	//This Player is used to test Player functions
	private static Player testPlayer;
	
	//These players are used to test Board functions
	private static Player startPerson;
	private static Player human;
	private static Player hasCard;
	private static Player noCard;
	
	//These cards are used to create solutions and suggestions
	private static Card goodRoom;
	private static Card goodPerson;
	private static Card goodWeapon;
	
	private static Card badRoom;
	private static Card badPerson;
	private static Card badWeapon;
		
	@BeforeAll
	public static void gameSetup()
	{		
		//Board is singleton
		board = Board.getInstance();
	
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	
		board.initialize();
						
		//This uses an unshuffled deck to set cards to known values
		goodRoom = board.getCleanDeck().get(0);
		goodPerson = board.getCleanDeck().get(NUM_ROOMS);
		goodWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS);
		
		badRoom = board.getCleanDeck().get(1);
		badPerson = board.getCleanDeck().get(NUM_ROOMS + 1);
		badWeapon = board.getCleanDeck().get(NUM_ROOMS + NUM_PLAYERS + 1);
		
		//This creates a testPlayer whose hand has three known cards
		testPlayer = new ComputerPlayer("Name", "PieceColor", "StartRoom");
				
		testPlayer.updateHand(goodRoom);
		testPlayer.updateHand(goodPerson);
		testPlayer.updateHand(goodWeapon);
		
		//This creates four players with predetermined hands and puts them in a list to test correct behavior with a given solution
		startPerson = new ComputerPlayer("Suggester", "Color", "Room");
		human = new HumanPlayer("Human", "Color", "Room");
		hasCard = new ComputerPlayer("Computer", "Color", "Room");
		noCard = new ComputerPlayer("NoCard", "Color", "Room");
		
		startPerson.updateHand(goodPerson);
		human.updateHand(badWeapon);
		hasCard.updateHand(goodRoom);
		noCard.updateHand(badRoom);
		
		testPlayerList = new ArrayList<>();
				
		testPlayerList.add(startPerson);
		testPlayerList.add(human);
		testPlayerList.add(hasCard);
		testPlayerList.add(noCard);
		
		board.setPlayerList(testPlayerList);
		
		//This sets the board's answer to a known combination of cards
		Solution testAnswer = new Solution(goodRoom, goodPerson, goodWeapon);
		board.setAnswer(testAnswer);
		
	} //end gameSetup
	
	//This test is responsible for ensuring the Board can pattern-match a correct accusation to the answer
	@Test
	public void goodAccusationTest()
	{
		Solution testAccuse = new Solution(goodRoom, goodPerson, goodWeapon);

		Assert.assertTrue(board.checkAccusation(testAccuse));
		
	} //end checkAccusationTest
	
	//These three tests ensure that all three of the cards must be correct for the board to accept the accusation
	@Test
	public void badRoomAccuseTest()
	{
		Solution testAccuse = new Solution(badRoom, goodPerson, goodWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
	} //end badRoomAccuseTest
	
	@Test
	public void badPersonAccuseTest()
	{
		Solution testAccuse = new Solution(goodRoom, badPerson, goodWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
		
	} //end badPersonAccuseTest
	
	@Test
	public void badWeaponAccuseTest()
	{		
		Solution testAccuse = new Solution(goodRoom, goodPerson, badWeapon);
		
		Assert.assertFalse(board.checkAccusation(testAccuse));
				
	} //end badWeaponAccuseTest
	
	//This ensures a player will present a card if they can
	@Test
	public void disproveOneTest()
	{
		Solution testSuggest = new Solution(goodRoom, badPerson, badWeapon);
		
		Assert.assertTrue(testPlayer.disproveSuggestion(testSuggest).equals(goodRoom));
		
	} //end testDisprove
	
	//This ensures that null is returned when a player cannot present any card
	@Test
	public void disproveNoneTest()
	{
		Solution testSuggest = new Solution(badRoom, badPerson, badWeapon);
		
		Assert.assertNull(testPlayer.disproveSuggestion(testSuggest));
		
	} //end testDisprove
	
	//This ensures that the player randomly presents a card when they have more than one available
	@Test
	public void disproveThreeTest()
	{
		int roomCount = 0;
		int personCount = 0;
		int weaponCount = 0;
		
		Card curCard;
		
		Solution testSuggest = new Solution(goodRoom, goodPerson, badWeapon);

		for(int i = 0; i < 60; i++)
		{
			curCard = testPlayer.disproveSuggestion(testSuggest);
			
			if(curCard.equals(goodRoom))
			{
				roomCount++;
				
			} //end nested if
			
			else if(curCard.equals(goodPerson))
			{
				personCount++;
				
			} //end nested else if
			
			else if(curCard.equals(goodWeapon))
			{
				weaponCount++;
				
			} //end nested else if
			
		} //end for
		
		//These tests ensure that the player presents possible cards randomly while holding those not in a suggestion
		Assert.assertTrue(roomCount >= 15);
		Assert.assertTrue(personCount >= 15);
		Assert.assertEquals(weaponCount, 0);
		
	} //end testDisprove
	
	//This ensures that the Board returns null when no players have a card in a suggestion
	//Note: no players in the Board's temporary playerList have badPerson in their hand
	@Test
	public void handleCorrectSuggestionTest()
	{		
		Solution testSuggest = new Solution(badPerson, badPerson, badPerson);
		
		Assert.assertNull(board.handleSuggestion(testSuggest));
		
	} //end handleSuggestionTest
	
	//This ensures that if a player has a card from a suggestion, they will present it
	@Test
	public void handlePartialSuggestionTest()
	{
		Solution testSuggest = new Solution(goodRoom, badPerson, badPerson);
		
		Assert.assertEquals(board.handleSuggestion(testSuggest).getName(), goodRoom.getName());
		
	} //end handleSuggestionTest
	
	//This ensures that the player who makes a suggestion will not present a card
	@Test
	public void suggesterTest()
	{
		Solution testSuggest = new Solution(goodRoom, goodPerson, goodWeapon);
						
		Assert.assertEquals(board.handleSuggestion(testSuggest).getName(), goodRoom.getName());
		
	} //end suggesterTest
	
	@Test
	public void orderTest()
	{
		//For this Solution, the hasCard and noCard both have cards in the suggestion
		//The hasCard's card should be returned as it comes first in the order
		Solution testSuggest = new Solution(goodRoom, badRoom, badPerson);
		
		Assert.assertEquals(board.handleSuggestion(testSuggest).getName(), goodRoom.getName());
		
	} //end orderTest
	
} //end GameplayTests