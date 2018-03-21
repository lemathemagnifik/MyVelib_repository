package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class ConcreteCardVisitor implements CardVisitor {

	
	/**
	 * cette classe compute le temps final. C'est � dire que tripTime est le temps moins le cr�dit en temps.
	 * On facture par heures enti�res.
	 * @throws Exception 
	 */
	
	//TODO Coder le duration trip
	@Override
	public double visit(BlueCard blueCard, Duration tripTime, Bicycle.BicycleType type) throws Exception {
		// toHours() retourne le nombre d'heures tronque
		if (type==Bicycle.BicycleType.Electrical){
			return  (tripTime.toHours()+1)*blueCard.getCostH1electrical();
		}
		if (type==Bicycle.BicycleType.Mechanical){
			return (tripTime.toHours()+1)*blueCard.getCostH1mechanical();
		}
		else{
			throw new Exception("bicycle type not found!");
		}
	}
	
	@Override
	public double visit(VlibreCard vlibreCard, Duration tripTime, Bicycle.BicycleType type) throws Exception {	
		// contient le nombre d'heures moins un !
		long numberOfHours=tripTime.toHours();
		
		if(type==Bicycle.BicycleType.Mechanical){
			return numberOfHours;
		}
		if(type==Bicycle.BicycleType.Electrical){
			if(numberOfHours==1){
				return 1;
			}
			else{
				return (numberOfHours-1)*2+1;
			}
		}
		else {
			throw new Exception("bicycle type error!");
		}
	}
	
	@Override
	public double visit(VmaxCard vmaxCard,  Duration tripTime, BicycleType type) {
		long numberOfHours= tripTime.toHours();
		
		return numberOfHours;
	}
	
}
