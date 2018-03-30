package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class VmaxCard extends VelibCard implements Card {
	private int ID;
	private static int IDcounter=0;
	private static double costH1 = 0.0;
	private static double costAfterH1 = 1.0;



	public VmaxCard(User user, Duration timeCredit) {
		super(timeCredit, user);
		IDcounter++;
		this.user = user;
		this.timeCredit = timeCredit;
		this.ID = IDcounter;
	}
	
	
	public Double getCost(Duration duration, Bicycle.BicycleType bType) {
		if (duration.toHours()<1) {
				return VmaxCard.costH1;
			}
		else {
			return VmaxCard.costAfterH1;
		}
	}

	@Override
	public Double accept(CardVisitor visitor, Duration tripTime, BicycleType type) throws Exception {
		return visitor.visit(this, tripTime, type);		
	}

	

	public static double getCostH1() {
		return costH1;
	}


	public static void setCostH1(double costH1) {
		VmaxCard.costH1 = costH1;
	}


	public static double getCostAfterH1() {
		return costAfterH1;
	}


	public static void setCostAfterH1(double costAfterH1) {
		VmaxCard.costAfterH1 = costAfterH1;
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
	
	
	

}
