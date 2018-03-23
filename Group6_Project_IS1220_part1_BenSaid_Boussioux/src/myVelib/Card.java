package myVelib;

import java.time.Duration;

public interface Card {
	/**
	 * 
	 * @param visitor
	 * @param tripTime
	 * @param type
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip and the type of the user's Card.
	 * @throws Exception
	 */
	public Double accept(CardVisitor visitor, Duration tripTime, Bicycle.BicycleType type) throws Exception;
	
}
