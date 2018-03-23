package myVelib;

import java.time.Duration;

public interface CardVisitor {
	
	/** 
	 * 
	 * @param creditCard
	 * @param tripDuration
	 * @param type Bicycle Time
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip when the user has no VelibCard.
	 * @throws Exception
	 */
	public double visit(CreditCard creditCard,Duration tripDuration, Bicycle.BicycleType type) throws Exception;
	
	/** 
	 * 
	 * @param creditCard
	 * @param tripDuration
	 * @param type Bicycle Time
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip when the user has a VlibreCard.
	 * @throws Exception
	 */
	public double visit(VlibreCard vlibreCard, Duration tripDuration, Bicycle.BicycleType type) throws Exception;
	
	/** 
	 * 
	 * @param creditCard
	 * @param tripDuration
	 * @param type Bicycle Time
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip when the user has a VmaxCard.
	 * @throws Exception
	 */
	public double visit(VmaxCard vmaxCard, Duration tripDuration, Bicycle.BicycleType type) throws Exception;
}
