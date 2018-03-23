package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class ConcreteCardVisitor implements CardVisitor {

	

	
	/**
	 * Apply the bonus of holding a VelibCard by using the card's time credit to reduce the charged trip duration.
	 * @param timeCredit
	 * @param tripDuration
	 */
	static void applyVelibBonus(Duration timeCredit, Duration tripDuration) {
		if (timeCredit.compareTo(tripDuration)>=0) {
			timeCredit = timeCredit.minus(tripDuration);
			tripDuration = Duration.ZERO;
		}
		else {
			// We substract the number of hours available in timeCredit
			timeCredit=timeCredit.minusHours(timeCredit.toHours());
			tripDuration=tripDuration.minusHours(timeCredit.toHours());
			// if the remaining time in timeCredit is enough to lower the number of hours in tripDuration we transfer the needed time credit to reduce the tripduration.
			if (tripDuration.minus(timeCredit).toHours()<tripDuration.toHours()) {
				Duration excess = tripDuration.minusHours(tripDuration.toHours());
				timeCredit = timeCredit.minus(excess);
				tripDuration = tripDuration.minus(excess);
			}
		}
	}
	
	/** 
	 * 
	 * @param creditCard
	 * @param tripDuration
	 * @param type Bicycle Time
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip when the user has no VelibCard.
	 * @throws Exception
	 */
	@Override
	public double visit(CreditCard creditCard, Duration tripDuration, Bicycle.BicycleType type) throws Exception {
		
		if (tripDuration.isZero() || tripDuration.isNegative()) {
			throw new Exception("Duration negative or equals Zero.");
		}
		
		if (type==Bicycle.BicycleType.Electrical){
			return (tripDuration.toHours()+1)*CreditCard.getCost1HElectrical();
		}
		if (type==Bicycle.BicycleType.Mechanical){
			return (tripDuration.toHours()+1)*CreditCard.getCost1HMechanical();
		}
		else{
			throw new Exception("bicycle type not found!");
		}
	}
	
	/** 
	 * 
	 * @param creditCard
	 * @param tripDuration
	 * @param type Bicycle Time
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip when the user has a VlibreCard.
	 * @throws Exception
	 */
	@Override
	public double visit(VlibreCard vlibreCard, Duration tripDuration, Bicycle.BicycleType bType) throws Exception {	
		
		if (tripDuration.isZero() || tripDuration.isNegative()) {
			throw new Exception("Duration negative or equals Zero.");
		}
		
		Duration timeCredit = vlibreCard.getTimeCredit();
		
		applyVelibBonus(timeCredit, tripDuration);
		
		long tripNbHours = tripDuration.toHours();
		
		if (bType == Bicycle.BicycleType.Electrical) {
			return VlibreCard.getCost1HElectrical() + tripNbHours*VlibreCard.getCostAfter1HElectrical();
		}
		else if (bType == Bicycle.BicycleType.Mechanical) {
			return VlibreCard.getCostAfter1HMechanical() + tripNbHours*VlibreCard.getCostAfter1HMechanical();
		}
		else {
			throw new Exception("bicycle type error!");
		}
	}
	
	/** 
	 * 
	 * @param creditCard
	 * @param tripDuration
	 * @param type Bicycle Time
	 * @return the cost of the trip concidering the bicycle type, the duration of the trip when the user has a VmaxCard.
	 * @throws Exception
	 */
	@Override
	public double visit(VmaxCard vmaxCard,  Duration tripDuration, BicycleType type) throws Exception {
		
		if (tripDuration.isZero() || tripDuration.isNegative()) {
			throw new Exception("Duration negative or equals Zero.");
		}
		
		long tripNbHours = tripDuration.toHours();
		return VmaxCard.getCostH1() + tripNbHours * VmaxCard.getCostAfterH1();
	}
	
}
