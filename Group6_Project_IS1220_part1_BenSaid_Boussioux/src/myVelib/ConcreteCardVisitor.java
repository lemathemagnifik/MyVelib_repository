package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class ConcreteCardVisitor implements CardVisitor {

	

	
	/**
	 * Apply the bonus of holding a VelibCard by using the card's time credit to reduce the charged trip duration.
	 * @param timeCredit
	 * @param tripDuration
	 * @return 
	 */
	public static Duration[] applyVelibBonus(Duration timeCredit, Duration tripDuration) {
		Duration [ ] results = new Duration[2];
		Duration newTimeCredit;
		Duration newTripDuration;
		if (timeCredit.compareTo(tripDuration)>=0) {
			newTimeCredit = timeCredit.minus(tripDuration);
			newTripDuration = Duration.ZERO;
		}
		else {
			// We substract the number of hours available in timeCredit
			newTripDuration=tripDuration.minusHours(timeCredit.toHours());
			newTimeCredit=timeCredit.minusHours(timeCredit.toHours());
			// if the remaining time in timeCredit is enough to lower the number of hours in tripDuration we transfer the needed time credit to reduce the tripduration.
			if (newTripDuration.minus(newTimeCredit).toHours()<newTripDuration.toHours()) {
				Duration excess = newTripDuration.minusHours(newTripDuration.toHours());
				newTimeCredit = newTimeCredit.minus(excess.plusNanos(1));
				newTripDuration = newTripDuration.minus(excess.plusNanos(1));
			}
		}
		System.out.println("The user transformed "+timeCredit.minus(newTimeCredit).toMinutes()+" minutes of his time credit to reduce the charged trip duration.");
		System.out.println("Remaining time credit : "+newTimeCredit.toMinutes()+" minutes.");
		System.out.println("Charged minutes : "+newTripDuration.toMinutes()+" minutes.");
		results[0]= newTimeCredit;
		results[1]= newTripDuration;
		return results;
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
			throw new Exception("Duration negative or equals Zero.\n");
		}
		
		if (type==Bicycle.BicycleType.Electrical){
			System.out.println("Charged minutes : "+tripDuration.toMinutes()+" minutes.");
			return (tripDuration.toHours()+1)*CreditCard.getCost1HElectrical();
		}
		if (type==Bicycle.BicycleType.Mechanical){
			System.out.println("Charged minutes : "+tripDuration.toMinutes()+" minutes.");
			return (tripDuration.toHours()+1)*CreditCard.getCost1HMechanical();
		}
		else{
			throw new Exception("Bicycle type not found!\n");
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
			throw new Exception("Duration negative or equals Zero.\n");
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
