package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class CreditCard implements Card {

	private User user;
	private int ID;
	
	private static double cost1HElectrical = 2;
	private static double cost1HMechanical = 1;
	
	static int IDcounter=0;

	public CreditCard(User user) {
		super();
		IDcounter++;
		this.user = user;
		this.ID = IDcounter;
	}
	
	
	public static double getCost1HElectrical() {
		return cost1HElectrical;
	}


	public static double getCost1HMechanical() {
		return cost1HMechanical;
	}


	public User getUser() {
		return user;
	}


	public int getID() {
		return ID;
	}




	@Override
	public Double accept(CardVisitor visitor, Duration tripTime, BicycleType type) throws Exception {
		return visitor.visit(this, tripTime, type);
	}


	@Override
	public String toString() {
		return "CreditCard : user=" + user.getName();
	}
	
	
	
}
