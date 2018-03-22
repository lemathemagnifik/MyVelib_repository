package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class ConcreteCardVisitor implements CardVisitor {

	
	/**
	 * cette classe compute le temps final. C'est � dire que tripTime est le temps moins le cr�dit en temps.
	 * On facture par heures enti�res.
	 * @throws Exception 
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
			// if the remaining time in timeCredit is enough to lower the number of hours in tripDuration
			if (tripDuration.minus(timeCredit).toHours()<tripDuration.toHours()) {
				Duration excess = tripDuration.minusHours(tripDuration.toHours());
				timeCredit = timeCredit.minus(excess);
				tripDuration = tripDuration.minus(excess);
			}
		}
	}
	
	
	//TODO Coder le duration trip
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
	
	@Override
	public double visit(VmaxCard vmaxCard,  Duration tripDuration, BicycleType type) throws Exception {
		
		if (tripDuration.isZero() || tripDuration.isNegative()) {
			throw new Exception("Duration negative or equals Zero.");
		}
		
		long tripNbHours = tripDuration.toHours();
		return VmaxCard.getCostH1() + tripNbHours * VmaxCard.getCostAfterH1();
	}
	
}
