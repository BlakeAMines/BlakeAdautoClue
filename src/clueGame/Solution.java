package clueGame;

public class Solution 
{
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution(Card initRoom, Card initPerson, Card initWeapon)
	{
		room = initRoom;
		person = initPerson;
		weapon = initWeapon;
		
	} //end constructor
	
	public boolean equals(Solution target)
	{
		return(room.equals(target.getRoom()) && person.equals(target.getPerson()) && weapon.equals(target.getWeapon()));

	} //end equals
	
	public Card getRoom()
	{
		return room;
		
	} //end getRoom
	
	public Card getPerson()
	{
		return person;
		
	} //end getRoom
	
	public Card getWeapon()
	{
		return weapon;
		
	} //end getRoom

	public boolean contains(Card seenRoom) 
	{
		return false;
		
	}
	
} //end Solution