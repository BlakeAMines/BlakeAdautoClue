package clueGame;

public class Card 
{
	private String cardName;
	
	private CardType cardType;
	
	public boolean equals(Card target)
	{
		return false;
		
	} //end equals
	
	public Card(String initCardName, String initCardType)
	{
		cardName = initCardName;
		
		if(initCardType.equals("Room"))
		{
			cardType = CardType.ROOM;
			
		} //end if
		
		else if(initCardType.equals("Person"))
		{
			cardType = CardType.PERSON;
			
		} //end else if
		
		else if(initCardType.equals("Weapon"))
		{
			cardType = CardType.WEAPON;
			
		} //end else if
				
	} //end Constructor
	
	public String getName()
	{
		return cardName;
		
	} //end getName
	
} //end card
