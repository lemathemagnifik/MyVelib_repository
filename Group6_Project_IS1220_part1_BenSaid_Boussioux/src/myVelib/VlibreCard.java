package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class VlibreCard extends VelibCard implements Card {


	
	private static int IDcounter = 0;
	private int ID;
	private static Double cost1HElectrical = 0.0;
	private static Double costAfter1HElectrical = 1.0;
	private static Double cost1HMechanical = 1.0;
	private static Double costAfter1HMechanical = 2.0;
	
	public Double getCost(Duration duration, Bicycle.BicycleType bType) {
		if (duration.toHours()<1) {
			if (bType == Bicycle.BicycleType.Electrical) {
				return VlibreCard.getCost1HElectrical();
			}
			else if (bType == Bicycle.BicycleType.Mechanical) {
				return VlibreCard.getCost1HMechanical();
			}
		}
		else {
			if (bType == Bicycle.BicycleType.Electrical) {
				return VlibreCard.getCostAfter1HElectrical();
			}
			else if (bType == Bicycle.BicycleType.Mechanical) {
				return VlibreCard.getCostAfter1HMechanical();
			}
		}
		return null;
	}

	public VlibreCard(User user, Duration timeCredit) {
		super(timeCredit, user);
		IDcounter++;
		this.user = user;
		this.timeCredit = timeCredit;
		this.ID = IDcounter;
	}


	

	
	
	@Override
	public Double accept(CardVisitor visitor, Duration tripTime, BicycleType type) throws Exception {
		return visitor.visit(this, tripTime, type);
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Duration getTimeCredit() {
		return timeCredit;
	}

	public void setTimeCredit(Duration timeCredit) {
		this.timeCredit = timeCredit;
	}

	public int getID() {
		return ID;
	}

	public static Double getCost1HElectrical() {
		return cost1HElectrical;
	}

	public static void setCost1HElectrical(Double cost1hElectrical) {
		cost1HElectrical = cost1hElectrical;
	}

	public static Double getCostAfter1HElectrical() {
		return costAfter1HElectrical;
	}

	public static void setCostAfter1HElectrical(Double costAfter1HElectrical) {
		VlibreCard.costAfter1HElectrical = costAfter1HElectrical;
	}

	public static Double getCost1HMechanical() {
		return cost1HMechanical;
	}

	public static void setCost1HMechanical(Double cost1hMechanical) {
		cost1HMechanical = cost1hMechanical;
	}

	public static Double getCostAfter1HMechanical() {
		return costAfter1HMechanical;
	}

	public static void setCostAfter1HMechanical(Double costAfter1HMechanical) {
		VlibreCard.costAfter1HMechanical = costAfter1HMechanical;
	}
	
	
	
}
